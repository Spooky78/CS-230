package com.example.cs230;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * responsible for the flying assassin.
 * @author Vic, Rex
 */
public class FlyingAssassin extends NPC {
    private static final String ASSASSIN_DOWN_PATH = "/Assassin/assassinDown.png";
    private static final String ASSASSIN_UP_PATH = "/Assassin/assassinUp.png";
    private static final String ASSASSIN_LEFT_PATH = "/Assassin/assassinLeft.png";
    private static final String ASSASSIN_RIGHT_PATH = "/Assassin/assassinRight.png";
    private static final int MILLS_DELAY = 500;
    private static final int IMAGE_SIZE = 50;
    private static final int MAX_ITERATIONS = 100;
    private final Board gameBoard;
    private final StackPane assassinStackPane;
    private final int[] coords;
    private final int indexID;
    private ImageView assassin;
    private boolean isKilled = false;
    private Timer timer;
    private String startDirection;

    /**
     * Constructor for the flying assassin.
     * @param board the board.
     * @param startCoords the start coords.
     * @param stackPane the stack pane.
     * @param indexID the indexId related to board.
     */
    public FlyingAssassin(Board board, int[] startCoords, StackPane stackPane, int indexID) {
        gameBoard = board;
        coords = startCoords;
        assassinStackPane = stackPane;
        this.indexID = indexID;
        isKilled = false;
        createNPC();
        move();
    }

    /**
     * created the flying assassin.
     */
    @Override
    protected void createNPC() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_DOWN_PATH)));
        assassin = new ImageView(assassinImage);
        assassin.setFitWidth(IMAGE_SIZE);
        assassin.setFitHeight(IMAGE_SIZE);
        int tileSize = gameBoard.getTileSize();
        assassinStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        assassinStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    /**
     * moves the flying assassin.
     */
    protected void move() {
        startDirection = gameBoard.getAssassinStartDirection().get(indexID);
        switch (startDirection) {
            case "RIGHT":
                startRightMovement();
                break;
            case "LEFT":
                startLeftMovement();
                break;
            case "DOWN":
                startDownMovement();
                break;
            case "UP":
                startUpMovement();
                break;
        }
    }

    /**
     * stops the timer.
     */
    public void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    /**
     * checks if collided with player.
     * @param killableCoords the player coords.
     * @param killable the player stackpane.
     * @param pane the game pane.
     * @return if collides with player.
     */
    public boolean collidedPlayer(int[] killableCoords, StackPane killable, BorderPane pane) {
        if (killableCoords[0] + 1 == coords[0] && killableCoords[1] + 1 == coords[1] & !isKilled) {
            pane.getChildren().remove(killable);
            isKilled = true;
            timer.cancel();
            timer.purge();
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if collided with npc.
     * @param killableCoords the player npc.
     * @param killable the npc stackpane.
     * @param pane the game pane.
     * @return if collides with npc.
     */
    public boolean collidedThief(int[] killableCoords, StackPane killable, BorderPane pane) {
        if (killableCoords[0] == coords[0] && killableCoords[1] == coords[1] & !isKilled) {
            pane.getChildren().remove(killable);
            System.out.println("DIE");
            return true;
        } else {
            return false;
        }
    }

    /**
     * starts movement with right direction.
     */
    private void startRightMovement() {
        SequentialTransition movement = moveStartRightTransition();
        setImage("RIGHT");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        int coordsCounter = coords[0];
        while (scheduleCount < MAX_ITERATIONS) {
            scheduleCount = moveRightTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeX();
            setImage("LEFT");
            scheduleCount = moveLeftTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("RIGHT");
        }
    }

    /**
     * starts movement with the left direction.
     */
    private void startLeftMovement() {
        SequentialTransition movement = moveStartLeftTransition();
        setImage("LEFT");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        coords[0] -= 1;
        int coordsCounter = coords[0];
        while (scheduleCount < MAX_ITERATIONS) {
            scheduleCount = moveLeftTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("RIGHT");
            scheduleCount = moveRightTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeX();
            setImage("LEFT");
        }
    }

    /**
     * starts movement with the down direction.
     */
    private void startDownMovement() {
        SequentialTransition movement = moveStartDownTransition();
        setImage("DOWN");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        int coordsCounter = coords[1];
        while (scheduleCount < MAX_ITERATIONS) {
            scheduleCount = moveDownTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeY();
            setImage("UP");
            scheduleCount = moveUpTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("DOWN");
        }
    }

    /**
     * starts movement with the up direction.
     */
    private void startUpMovement() {
        SequentialTransition movement = moveStartUpTransition();
        setImage("UP");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        coords[1] -= 1;
        int coordsCounter = coords[1];
        while (scheduleCount < MAX_ITERATIONS) {
            scheduleCount = moveUpTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("DOWN");
            scheduleCount = moveDownTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeY();
            setImage("UP");
        }
    }

    /**
     * moves assassin across tile to right.
     * @param timer the timer.
     * @param scheduleCount the schedule count.
     * @param coordsCounter the coords counter.
     * @param animation the sequential transition of animations.
     * @return the schedule count.
     */
    private int moveRightTile(Timer timer, int scheduleCount, int coordsCounter,
                              SequentialTransition animation) {
        while (coordsCounter < gameBoard.getBoardSizeX()) {
            int finalScheduleCount = scheduleCount;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    animation.pause();
                    setImage("RIGHT");
                    coords[0] += 1;
                    animation.playFrom(String.valueOf((long) MILLS_DELAY * finalScheduleCount));
                }
            };
            timer.schedule(task, (long) MILLS_DELAY * scheduleCount);
            scheduleCount += 1;
            coordsCounter += 1;
        }
        return scheduleCount;
    }

    /**
     * moves assassin across tile to left.
     * @param timer the timer.
     * @param scheduleCount the schedule count.
     * @param coordsCounter the coords counter.
     * @param animation the sequential transition of animations.
     * @return the schedule count.
     */
    private int moveLeftTile(Timer timer, int scheduleCount, int coordsCounter,
                             SequentialTransition animation) {
        while (coordsCounter > 0) {
            int finalScheduleCount = scheduleCount;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    animation.pause();
                    setImage("LEFT");
                    coords[0] -= 1;
                    animation.playFrom(String.valueOf((long) MILLS_DELAY * finalScheduleCount));
                }
            };
            timer.schedule(task, (long) MILLS_DELAY * scheduleCount);
            scheduleCount += 1;
            coordsCounter -= 1;
        }
        return scheduleCount;
    }

    /**
     * moves assassin across tile to down.
     * @param timer the timer.
     * @param scheduleCount the schedule count.
     * @param coordsCounter the coords counter.
     * @param animation the sequential transition of animations.
     * @return the schedule count.
     */
    private int moveDownTile(Timer timer, int scheduleCount, int coordsCounter,
                             SequentialTransition animation) {
        while (coordsCounter < gameBoard.getBoardSizeY()) {
            int finalScheduleCount = scheduleCount;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    animation.pause();
                    setImage("DOWN");
                    coords[1] += 1;
                    animation.playFrom(String.valueOf(MILLS_DELAY * finalScheduleCount));
                }
            };
            timer.schedule(task, (long) MILLS_DELAY * scheduleCount);
            scheduleCount += 1;
            coordsCounter += 1;
        }
        return scheduleCount;
    }

    /**
     * moves assassin across tile to up.
     * @param timer the timer.
     * @param scheduleCount the schedule count.
     * @param coordsCounter the coords counter.
     * @param animation the sequential transition of animations.
     * @return the schedule count.
     */
    private int moveUpTile(Timer timer, int scheduleCount, int coordsCounter,
                           SequentialTransition animation) {
        while (coordsCounter > 0) {
            int finalScheduleCount = scheduleCount;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    animation.pause();
                    setImage("UP");
                    coords[1] -= 1;
                    animation.playFrom(String.valueOf(MILLS_DELAY * finalScheduleCount));
                }
            };
            timer.schedule(task, (long) MILLS_DELAY * scheduleCount);
            scheduleCount += 1;
            coordsCounter -= 1;
        }
        return scheduleCount;
    }

    /**
     * works out the transition starting with right.
     * @return the sequential transition.
     */
    private SequentialTransition moveStartRightTransition() {
        int startMove = gameBoard.getBoardSizeX() - coords[0];
        TranslateTransition moveRightStartLoop = moveStartHorizontalTransition(startMove, 1);

        TranslateTransition moveLeft = moveHorizontalLeftTransition();
        TranslateTransition moveRight = moveHorizontalRightTransition();
        SequentialTransition moveHorizontal = new SequentialTransition();
        moveHorizontal.getChildren().addAll(moveLeft, moveRight);
        moveHorizontal.setCycleCount(SequentialTransition.INDEFINITE);

        SequentialTransition moveLeftStartTimeline = new SequentialTransition();
        moveLeftStartTimeline.getChildren().addAll(moveRightStartLoop, moveHorizontal);
        return moveLeftStartTimeline;
    }

    /**
     * works out the transition starting with left.
     * @return the sequential transition.
     */
    private SequentialTransition moveStartLeftTransition() {
        int startMove = (gameBoard.getBoardSizeX() - (gameBoard.getBoardSizeX() - coords[0]) - 1);
        TranslateTransition moveLeftStartLoop = moveStartHorizontalTransition(startMove, -1);

        TranslateTransition moveRight = moveHorizontalRightTransition();
        TranslateTransition moveLeft = moveHorizontalLeftTransition();
        SequentialTransition moveHorizontal = new SequentialTransition();
        moveHorizontal.getChildren().addAll(moveRight, moveLeft);
        moveHorizontal.setCycleCount(SequentialTransition.INDEFINITE);

        SequentialTransition moveLeftStartTimeline = new SequentialTransition();
        moveLeftStartTimeline.getChildren().addAll(moveLeftStartLoop, moveHorizontal);
        moveLeftStartTimeline.play();
        return moveLeftStartTimeline;
    }

    /**
     * works out the transition starting with down.
     * @return the sequential transition.
     */
    private SequentialTransition moveStartDownTransition() {
        int startMove = gameBoard.getBoardSizeY() - coords[1];
        TranslateTransition moveUpStartLoop = moveStartVerticalTransition(startMove, 1);

        TranslateTransition moveDown = moveVerticalDownTransition();
        TranslateTransition moveUp = moveVerticalUpTransition();
        SequentialTransition moveVertical = new SequentialTransition();
        moveVertical.getChildren().addAll(moveDown, moveUp);
        moveVertical.setCycleCount(SequentialTransition.INDEFINITE);

        SequentialTransition moveDownStartTimeline = new SequentialTransition();
        moveDownStartTimeline.getChildren().addAll(moveUpStartLoop, moveVertical);
        return moveDownStartTimeline;
    }

    /**
     * works out the transition starting with up.
     * @return the sequential transition.
     */
    private SequentialTransition moveStartUpTransition() {
        int startMove = (gameBoard.getBoardSizeY() - (gameBoard.getBoardSizeY() - coords[1]) - 1);
        TranslateTransition moveDownStartLoop = moveStartVerticalTransition(startMove, -1);

        TranslateTransition moveUp = moveVerticalUpTransition();
        TranslateTransition moveDown = moveVerticalDownTransition();
        SequentialTransition moveVertical = new SequentialTransition();
        moveVertical.getChildren().addAll(moveUp, moveDown);
        moveVertical.setCycleCount(SequentialTransition.INDEFINITE);

        SequentialTransition moveDownStartTimeline = new SequentialTransition();
        moveDownStartTimeline.getChildren().addAll(moveDownStartLoop, moveVertical);
        moveDownStartTimeline.play();
        return moveDownStartTimeline;
    }

    /**
     * transition across tiles horizontally .
     * @param startMove the start time.
     * @param direction the direction.
     * @return the trnslation.
     */
    private TranslateTransition moveStartHorizontalTransition(int startMove, int direction) {
        TranslateTransition moveHorizontalStartLoop = new TranslateTransition();
        moveHorizontalStartLoop.setNode(assassin);
        moveHorizontalStartLoop.setDuration(Duration.millis(startMove * MILLS_DELAY));
        moveHorizontalStartLoop.setByX(startMove * gameBoard.getTileSize() * direction);
        return moveHorizontalStartLoop;
    }

    /**
     * transition across tile to right.
     * @return the translation.
     */
    private TranslateTransition moveHorizontalRightTransition() {
        TranslateTransition moveRight = new TranslateTransition();
        moveRight.setNode(assassin);
        moveRight.setDuration(Duration.millis(gameBoard.getBoardSizeX() * MILLS_DELAY));
        moveRight.setByX((gameBoard.getBoardSizeX() - 1) * gameBoard.getTileSize());
        return moveRight;
    }

    /**
     * transition across tile to left.
     * @return the trnslation.
     */
    private TranslateTransition moveHorizontalLeftTransition() {
        TranslateTransition moveLeft = new TranslateTransition();
        moveLeft.setNode(assassin);
        moveLeft.setDuration(Duration.millis(gameBoard.getBoardSizeX() * MILLS_DELAY));
        moveLeft.setByX(-((gameBoard.getBoardSizeX() - 1) * gameBoard.getTileSize()));
        return moveLeft;
    }

    /**
     * transition across tiles horizontally .
     * @param startMove the start time.
     * @param direction the direction.
     * @return the translation.
     */
    private TranslateTransition moveStartVerticalTransition(int startMove, int direction) {
        TranslateTransition moveVerticalStartLoop = new TranslateTransition();
        moveVerticalStartLoop.setNode(assassin);
        moveVerticalStartLoop.setDuration(Duration.millis(startMove * MILLS_DELAY));
        moveVerticalStartLoop.setByY(startMove * gameBoard.getTileSize() * direction);
        return moveVerticalStartLoop;
    }

    /**
     * transition across tile to up.
     * @return the trnslation.
     */
    private TranslateTransition moveVerticalUpTransition() {
        TranslateTransition moveUp = new TranslateTransition();
        moveUp.setNode(assassin);
        moveUp.setDuration(Duration.millis(gameBoard.getBoardSizeY() * MILLS_DELAY));
        moveUp.setByY((gameBoard.getBoardSizeY() - 1) * gameBoard.getTileSize());
        return moveUp;
    }

    /**
     * transition across tile to down.
     * @return the trnslation.
     */
    private TranslateTransition moveVerticalDownTransition() {
        TranslateTransition moveDown = new TranslateTransition();
        moveDown.setNode(assassin);
        moveDown.setDuration(Duration.millis(gameBoard.getBoardSizeY() * MILLS_DELAY));
        moveDown.setByY(-((gameBoard.getBoardSizeY() - 1) * gameBoard.getTileSize()));
        return moveDown;
    }

    /**
     * sets image of flying assassin.
     * @param direction the direction.
     */
    private void setImage(String direction) {
        Image assassinImage = switch (direction) {
            case "LEFT" -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_LEFT_PATH)));
            case "RIGHT" -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_RIGHT_PATH)));
            case "UP" -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_UP_PATH)));
            default -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_DOWN_PATH)));
        };
        assassin.setImage(assassinImage);
        assassin.setFitWidth(IMAGE_SIZE);
        assassin.setFitHeight(IMAGE_SIZE);
    }

    /**
     * set that player has lost game.
     */
    public void setLose() {
        boolean isLose = true;
    }

    /**
     * return image view of flying assassin.
     * @return the image view
     */
    public ImageView getAssassin() {
        return assassin;
    }

    /**
     * Returns the direction of assassin.
     * @return the direction
     */
    public String getStartDirection() {
        return startDirection;
    }

    /**
     * returns the stack pane.
     * @return the stack pane.
     */
    @Override
    public StackPane getStackPane() {
        return assassinStackPane;
    }

    /**
     * return the coordinates.
     * @return the coordinates.
     */
    @Override
    public int[] getCoords() {
        return coords;
    }
}
