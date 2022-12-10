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
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlyingAssassin extends NPC {
    private static final String ASSASSIN_DOWN_PATH = "/Assassin/assassinDown.png";
    private static final String ASSASSIN_UP_PATH = "/Assassin/assassinUp.png";
    private static final String ASSASSIN_LEFT_PATH = "/Assassin/assassinLeft.png";
    private static final String ASSASSIN_RIGHT_PATH = "/Assassin/assassinRight.png";
    private static final int MILLS_DELAY = 500;
    private static final int SCHEDULING_DELAY = 200;
    private ImageView assassin;
    private Board gameBoard;
    private StackPane assassinStackPane;
    private int[] coords;
    private boolean isKilled = false;
    private GameViewManager gameOver;
    private final int indexID;
    private boolean isLose;
    private Timer timer;

    //private StackPane assassinStack;
    public FlyingAssassin(Board board, int[] startCoords, StackPane stackPane, GameViewManager gameOver, int indexID) {
        gameBoard = board;
        coords = startCoords;
        assassinStackPane = stackPane;
        this.indexID = indexID;
        this.gameOver = gameOver;
        isKilled = false;
        createNPC();
        move();
    }

    @Override
    protected void createNPC() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_DOWN_PATH)));
        assassin = new ImageView(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
        int tileSize = gameBoard.getTileSize();
        assassinStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        assassinStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    protected void move() {
        String startDirection = gameBoard.getAssassinStartDirection().get(indexID);
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

    @Override
    public StackPane getStackPane() {
        return assassinStackPane;
    }

    @Override
    public int[] getCoords() {
        return coords;
    }

    public void stopTimer() {
        timer.cancel();
        timer.purge();
    }
    public boolean collidedPlayer(int[] killableCoords, StackPane killable, BorderPane pane){
        if (killableCoords[0] +1 == coords[0] && killableCoords[1] +1 == coords[1] & !isKilled) {
            pane.getChildren().remove(killable);
            System.out.println("DIE");
            isKilled = true;
            timer.cancel();
            timer.purge();
            return true;
        } else {
            return false;
        }
    }

    public boolean collidedThief(int[] killableCoords, StackPane killable, BorderPane pane){
        if (killableCoords[0] == coords[0] && killableCoords[1] == coords[1] & !isKilled) {
            pane.getChildren().remove(killable);
            System.out.println("DIE");
            isKilled = true;
            timer.cancel();
            timer.purge();
            return true;
        } else {
            return false;
        }
    }

    private void startRightMovement() {
        SequentialTransition movement = moveStartRightTransition();
        setImage("RIGHT");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        int coordsCounter = coords[0];
        while (scheduleCount < 100) {
            scheduleCount = moveRightTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeX();
            setImage("LEFT");
            scheduleCount = moveLeftTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("RIGHT");
        }
    }

    private void startLeftMovement() {
        SequentialTransition movement = moveStartLeftTransition();
        setImage("LEFT");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        coords[0] -=1;
        int coordsCounter = coords[0];
        while (scheduleCount < 100) {
            scheduleCount = moveLeftTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("RIGHT");
            scheduleCount = moveRightTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeX();
            setImage("LEFT");
        }
    }

    private void startDownMovement() {
        SequentialTransition movement = moveStartDownTransition();
        setImage("DOWN");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        int coordsCounter = coords[1];
        while (scheduleCount < 100) {
            scheduleCount = moveDownTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeY();
            setImage("UP");
            scheduleCount = moveUpTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("DOWN");
        }
    }

    private void startUpMovement() {
        SequentialTransition movement = moveStartUpTransition();
        setImage("UP");
        movement.play();
        timer = new Timer();

        int scheduleCount = 0;
        coords[1] -=1;
        int coordsCounter = coords[1];
        while (scheduleCount < 100) {
            scheduleCount = moveUpTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = 0;
            setImage("DOWN");
            scheduleCount = moveDownTile(timer, scheduleCount, coordsCounter, movement);
            coordsCounter = gameBoard.getBoardSizeY();
            setImage("UP");
        }
    }

    private int moveRightTile(Timer timer, int scheduleCount, int coordsCounter, SequentialTransition animation) {
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

    private int moveLeftTile(Timer timer, int scheduleCount, int coordsCounter, SequentialTransition animation) {
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

    private int moveDownTile(Timer timer, int scheduleCount, int coordsCounter, SequentialTransition animation) {
        while (coordsCounter < gameBoard.getBoardSizeY()) {
            int finalScheduleCount = scheduleCount;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    animation.pause();
                    setImage("DOWN");
                    coords[1] += 1;
                    animation.playFrom(String.valueOf(MILLS_DELAY* finalScheduleCount));
                }
            };
            timer.schedule(task, (long) MILLS_DELAY * scheduleCount);
            scheduleCount += 1;
            coordsCounter += 1;
        }
        return scheduleCount;
    }

    private int moveUpTile(Timer timer, int scheduleCount, int coordsCounter, SequentialTransition animation) {
        while (coordsCounter > 0) {
            int finalScheduleCount = scheduleCount;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    animation.pause();
                    setImage("UP");
                    coords[1] -= 1;
                    animation.playFrom(String.valueOf(MILLS_DELAY* finalScheduleCount));
                }
            };
            timer.schedule(task, (long) MILLS_DELAY * scheduleCount);
            scheduleCount += 1;
            coordsCounter -= 1;
        }
        return scheduleCount;
    }

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

    private TranslateTransition moveStartHorizontalTransition(int startMove, int direction) {
        TranslateTransition moveHorizontalStartLoop = new TranslateTransition();
        moveHorizontalStartLoop.setNode(assassin);
        moveHorizontalStartLoop.setDuration(Duration.millis(startMove * MILLS_DELAY));
        moveHorizontalStartLoop.setByX(startMove * gameBoard.getTileSize() * direction);
        return moveHorizontalStartLoop;
    }

    private TranslateTransition moveHorizontalRightTransition() {
        TranslateTransition moveRight = new TranslateTransition();
        moveRight.setNode(assassin);
        moveRight.setDuration(Duration.millis(gameBoard.getBoardSizeX() * MILLS_DELAY));
        moveRight.setByX((gameBoard.getBoardSizeX() - 1) * gameBoard.getTileSize());
        return moveRight;
    }

    private TranslateTransition moveHorizontalLeftTransition() {
        TranslateTransition moveLeft = new TranslateTransition();
        moveLeft.setNode(assassin);
        moveLeft.setDuration(Duration.millis(gameBoard.getBoardSizeX() * MILLS_DELAY));
        moveLeft.setByX(-((gameBoard.getBoardSizeX() - 1) * gameBoard.getTileSize()));
        return moveLeft;
    }

    private TranslateTransition moveStartVerticalTransition(int startMove, int direction) {
        TranslateTransition moveVerticalStartLoop = new TranslateTransition();
        moveVerticalStartLoop.setNode(assassin);
        moveVerticalStartLoop.setDuration(Duration.millis(startMove * MILLS_DELAY));
        moveVerticalStartLoop.setByY(startMove * gameBoard.getTileSize() * direction);
        return moveVerticalStartLoop;
    }

    private TranslateTransition moveVerticalUpTransition() {
        TranslateTransition moveUp = new TranslateTransition();
        moveUp.setNode(assassin);
        moveUp.setDuration(Duration.millis(gameBoard.getBoardSizeY() * MILLS_DELAY));
        moveUp.setByY((gameBoard.getBoardSizeY() - 1) * gameBoard.getTileSize());
        return moveUp;
    }

    private TranslateTransition moveVerticalDownTransition() {
        TranslateTransition moveDown = new TranslateTransition();
        moveDown.setNode(assassin);
        moveDown.setDuration(Duration.millis(gameBoard.getBoardSizeY() * MILLS_DELAY));
        moveDown.setByY(-((gameBoard.getBoardSizeY() - 1) * gameBoard.getTileSize()));
        return moveDown;
    }

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
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
    }

    public void killTimer(){
        timer.cancel();
        timer.purge();
    }
    public void setLose() {
        isLose = true;
    }
    public ImageView getAssassin() {
        return assassin;
    }
}
