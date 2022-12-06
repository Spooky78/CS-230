package com.example.cs230;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FloorFollowingThief extends NPC {

    private static final String FFTHIEF_DOWN_PATH = "/Skeleton/SkeletonDown.png";
    private static final String FFTHIEF_UP_PATH = "/Skeleton/SkeletonUp.png";
    private static final String FFTHIEF_LEFT_PATH = "/Skeleton/SkeletonLeft.png";
    private static final String FFTHIEF_RIGHT_PATH = "/Skeleton/SkeletonRight.png";
    private static final int MILLS_DELAY_TILE = 1000;
    private static final int SCHEDULING_DELAY = 200;
    private ImageView ffThief;
    private Board gameBoard;
    private StackPane ffThiefStackPane;
    private int[] coords;
    private int[] animationCoords;
    private int indexID;
    int previous = 0;
    private ArrayList<Integer> timings = new ArrayList<>();

    public FloorFollowingThief (Board board, int[] startCoords, StackPane stackPane, int indexID) {
        gameBoard = board;
        coords = startCoords;
        animationCoords = startCoords;
        ffThiefStackPane = stackPane;
        this.indexID = indexID;
        createNPC();
        move();
    }

    protected void createNPC() {
        Image ffThiefImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_DOWN_PATH)));
        ffThief = new ImageView(ffThiefImage);
        ffThief.setFitWidth(50);
        ffThief.setFitHeight(50);
        int tileSize = gameBoard.getTileSize();
        ffThiefStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        ffThiefStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    protected void move() {
        String startDirection = gameBoard.getFloorFollowingThiefDirectionStart().get(indexID);
        String turnDirection = gameBoard.getFloorFollowingThiefDirectionTurn().get(indexID);
        String direction = startDirection + " " + turnDirection;
        System.out.println(direction);
        switch (direction) {
            case "RIGHT ANTICLOCKWISE":
                //startRightAnitclockwiseAnimation();
                startRightAnticlockwiseTransition();
                break;
            case "RIGHT CLOCKWISE":
                startRightClockwiseTransition();
                break;
            case "LEFT ANTICLOCKWISE":
                startLeftAntiClockwiseTransition();
                break;
            case "LEFT CLOCKWISE":
                startLeftClockwiseTransition();
                break;
            case "DOWN ANTICLOCKWISE":
                startDownAntiClockwiseTransition();
                break;
            case "DOWN CLOCKWISE":
                startDownClockwiseTransition();
                break;
            case "UP ANTICLOCKWISE":
                startUpAntiClockwiseTransition();
                break;
            case "UP CLOCKWISE":
                startUpClockwiseTransition();
                break;
        }

    }

    private void startRightAnitclockwiseAnimation() {
        SequentialTransition animation = startRightAnticlockwiseTransition();
        setImage("RIGHT");
        Timer timer = new Timer();
        int scheduleCount = 0;
        int coordsCounter = coords[0];
        while (scheduleCount < 100) {
            scheduleCount = moveRightTile(timer, scheduleCount, coordsCounter, animation);
            setImage("DOWN");
        }
    }

    private int moveRightTile(Timer timer, int scheduleCount, int coordsCounter, SequentialTransition animation) {
        int counter = 0;
        while(counter < timings.get(scheduleCount)) {
            int finalScheduleCount = scheduleCount;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    animation.pause();
                    setImage("RIGHT");
                    coords[0] += 1;
                    animation.playFrom(String.valueOf(MILLS_DELAY_TILE * (timings.get(finalScheduleCount) + previous)));
                }
            };
            timer.schedule(task, (long)MILLS_DELAY_TILE * (timings.get(scheduleCount)));
            scheduleCount += 1;
            previous += timings.get(scheduleCount);
            coordsCounter += 1;
        }
        return scheduleCount;
    }

    private SequentialTransition startRightAnticlockwiseTransition() {
        SequentialTransition startRightTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //right movement
            int currentTiming = rightTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            //Up movement
            currentTiming = upTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            //Left movement
            currentTiming = leftTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            //Down movement
            currentTiming = downTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            count +=1;
        }
        startRightTransition.play();
        return startRightTransition;
    }

    private SequentialTransition startRightClockwiseTransition(){
        SequentialTransition startRightTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //right movement
            int currentTiming = rightTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            //Down movement
            currentTiming = downTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            //Left movement
            currentTiming = leftTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            //Up movement
            currentTiming = upTransitionLoop(startRightTransition);
            timings.add(currentTiming);
            count +=1;
        }
        startRightTransition.play();
        return startRightTransition;
    }

    private SequentialTransition startLeftAntiClockwiseTransition() {
        SequentialTransition startLeftAntiClockwiseTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //Left movement
            int currentTiming = leftTransitionLoop(startLeftAntiClockwiseTransition);
            timings.add(currentTiming);
            //Down movement
            currentTiming = downTransitionLoop(startLeftAntiClockwiseTransition);
            timings.add(currentTiming);
            //Right movement
            currentTiming = rightTransitionLoop(startLeftAntiClockwiseTransition);
            timings.add(currentTiming);
            //Up movement
            currentTiming = upTransitionLoop(startLeftAntiClockwiseTransition);
            timings.add(currentTiming);
            count += 1;
        }
        startLeftAntiClockwiseTransition.play();
        return startLeftAntiClockwiseTransition;
    }

    private SequentialTransition startLeftClockwiseTransition() {
        SequentialTransition startLeftClockwiseTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //Left movement
            int currentTiming = leftTransitionLoop(startLeftClockwiseTransition);
            timings.add(currentTiming);
            //Up movement
            currentTiming = upTransitionLoop(startLeftClockwiseTransition);
            timings.add(currentTiming);
            //Right movement
            currentTiming = rightTransitionLoop(startLeftClockwiseTransition);
            timings.add(currentTiming);
            //Down movement
            currentTiming = downTransitionLoop(startLeftClockwiseTransition);
            timings.add(currentTiming);
            count += 1;
        }
        startLeftClockwiseTransition.play();
        return startLeftClockwiseTransition;
    }

    private SequentialTransition startUpAntiClockwiseTransition() {
        SequentialTransition startUpAntiClockwiseTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //Up movement
            int currentTiming = upTransitionLoop(startUpAntiClockwiseTransition);
            timings.add(currentTiming);
            //Left movement
            currentTiming = leftTransitionLoop(startUpAntiClockwiseTransition);
            timings.add(currentTiming);
            //Down movement
            currentTiming = downTransitionLoop(startUpAntiClockwiseTransition);
            timings.add(currentTiming);
            //Right movement
            currentTiming = rightTransitionLoop(startUpAntiClockwiseTransition);
            timings.add(currentTiming);
            count += 0;
        }
        startUpAntiClockwiseTransition.play();
        return startUpAntiClockwiseTransition;
    }

    private SequentialTransition startUpClockwiseTransition() {
        SequentialTransition startUpClockwiseTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //Up movement
            int currentTiming = upTransitionLoop(startUpClockwiseTransition);
            timings.add(currentTiming);
            //Right movement
            currentTiming = rightTransitionLoop(startUpClockwiseTransition);
            timings.add(currentTiming);
            count += 1;
            //Down movement
            currentTiming = downTransitionLoop(startUpClockwiseTransition);
            timings.add(currentTiming);
            //Left movement
            currentTiming = leftTransitionLoop(startUpClockwiseTransition);
            timings.add(currentTiming);
        }
        startUpClockwiseTransition.play();
        return startUpClockwiseTransition;
    }

    private SequentialTransition startDownAntiClockwiseTransition() {
        SequentialTransition startDownAntiClockwiseTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //Down movement
            int currentTiming = downTransitionLoop(startDownAntiClockwiseTransition);
            timings.add(currentTiming);
            //Right movement
            currentTiming = rightTransitionLoop(startDownAntiClockwiseTransition);
            timings.add(currentTiming);
            //Up movement
            currentTiming = upTransitionLoop(startDownAntiClockwiseTransition);
            timings.add(currentTiming);
            //Left movement
            currentTiming = leftTransitionLoop(startDownAntiClockwiseTransition);
            timings.add(currentTiming);
            count += 1;
        }
        startDownAntiClockwiseTransition.play();
        return startDownAntiClockwiseTransition;
    }

    private SequentialTransition startDownClockwiseTransition() {
        SequentialTransition startDownClockwiseTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            //Down movement
            int currentTiming = downTransitionLoop(startDownClockwiseTransition);
            timings.add(currentTiming);
            //Left movement
            currentTiming = leftTransitionLoop(startDownClockwiseTransition);
            timings.add(currentTiming);
            //Up movement
            currentTiming = upTransitionLoop(startDownClockwiseTransition);
            timings.add(currentTiming);
            //Right movement
            currentTiming = rightTransitionLoop(startDownClockwiseTransition);
            timings.add(currentTiming);
            count += 1;
        }
        startDownClockwiseTransition.play();
        return startDownClockwiseTransition;
    }

    private int rightTransitionLoop(SequentialTransition transition) {
        int currentTiming = 0;
        int[] currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
        int[] rightTile = new int[]{animationCoords[0], animationCoords[1] - 1};
        while (animationCoords[0] < gameBoard.getBoardSizeX() && gameBoard.canMove(currentCoords, rightTile)) {
            transition.getChildren().add(moveRightTile());
            rightTile[0] += 1;
            currentTiming +=1;
        }
        return currentTiming;
    }

    private int leftTransitionLoop(SequentialTransition transition) {
        int currentTiming = 0;
        int[] currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
        int[] leftTile = new int[]{animationCoords[0] - 2, animationCoords[1] - 1};
        while (animationCoords[0] - 1 > 0 && gameBoard.canMove(currentCoords, leftTile)) {
            transition.getChildren().add(moveLeftTile());
            leftTile[0] -= 1;
            currentTiming += 1;
        }
        return currentTiming;
    }

    private int upTransitionLoop(SequentialTransition transition) {
        int currentTiming = 0;
        int[] currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
        int[] upTile = new int[]{animationCoords[0] - 1, animationCoords[1] - 2};
        while (animationCoords[1] - 1 > 0 && gameBoard.canMove(currentCoords, upTile)) {
            transition.getChildren().add(moveUpTile());
            upTile[1] -= 1;
            currentTiming += 1;
        }
        return currentTiming;
    }

    private int downTransitionLoop(SequentialTransition transition) {
        int currentTiming = 0;
        int[] currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
        int[] downTile = new int[]{animationCoords[0] - 1, animationCoords[1]};
        while (animationCoords[1] < gameBoard.getBoardSizeY() && gameBoard.canMove(currentCoords, downTile)) {
            transition.getChildren().add(moveDownTile());
            downTile[1] += 1;
            currentTiming += 1;
        }
        return currentTiming;
    }

    private TranslateTransition moveRightTile() {
        TranslateTransition moveRight = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveRight.setNode(ffThief);
        moveRight.setByX(gameBoard.getTileSize());
        animationCoords[0] += 1;
        return moveRight;
    }

    private TranslateTransition moveLeftTile() {
        TranslateTransition moveLeft = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveLeft.setNode(ffThief);
        moveLeft.setByX(-gameBoard.getTileSize());
        animationCoords[0] -= 1;
        return moveLeft;
    }

    private TranslateTransition moveDownTile() {
        TranslateTransition moveDown = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveDown.setNode(ffThief);
        moveDown.setByY(gameBoard.getTileSize());
        animationCoords[1] += 1;
        return moveDown;
    }

    private TranslateTransition moveUpTile() {
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveUp.setNode(ffThief);
        moveUp.setByY(-gameBoard.getTileSize());
        animationCoords[1] -= 1;
        return moveUp;
    }

    private void setImage(String direction) {
        Image ffThiefImage = switch (direction) {
            case "LEFT" -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_LEFT_PATH)));
            case "RIGHT" -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_RIGHT_PATH)));
            case "UP" -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_UP_PATH)));
            default -> new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_DOWN_PATH)));
        };
        ffThief.setImage(ffThiefImage);
        ffThief.setFitWidth(50);
        ffThief.setFitHeight(50);
    }

    public ImageView getffThief() {
        return ffThief;
    }

    @Override
    public StackPane getStackPane() {
        return ffThiefStackPane;
    }

    @Override
    public int[] getCoords() {
        return coords;
    }
}
