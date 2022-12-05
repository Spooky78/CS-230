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
    private ArrayList<Integer> timings = new ArrayList<>();

    public FloorFollowingThief (Board board, int[] startCoords, StackPane stackPane, int indexID) {
        gameBoard = board;
        coords = startCoords;
        animationCoords = startCoords;
        ffThiefStackPane = stackPane;
        this.indexID = indexID;
        createNPC();
        move();
//        TranslateTransition test = moveRightTile();
//        test.play();
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
                startRightAnticlockwiseMovement();
                //startRightAnticlockwiseTransition();
                break;
            case "LEFT":
                //startLeftMovement();
                break;
            case "DOWN":
                //startDownMovement();
                break;
            case "UP":
                //startUpMovement();
                break;
        }

    }

    private void startRightAnticlockwiseMovement() {
        SequentialTransition animation = startRightAnticlockwiseTransition();
        int previous = 0;
        Timer timer = new Timer();
        setImage("RIGHT");


    }

    private SequentialTransition startRightAnticlockwiseTransition() {
        SequentialTransition startRightTransition = new SequentialTransition();
        int count = 0;
        while (count < 100) {
            int currentTiming = 0;
            //right movement
            int[] currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] rightTile = new int[]{animationCoords[0], animationCoords[1] - 1};
            while (animationCoords[0] < gameBoard.getBoardSizeX() && gameBoard.canMove(currentCoords, rightTile)) {
                startRightTransition.getChildren().add(moveRightTile());
                rightTile[0] += 1;
                currentTiming +=1;
            }
            timings.add(currentTiming);
            currentTiming = 0;

            //Up movement
            currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] upTile = new int[]{animationCoords[0] - 1, animationCoords[1] - 2};
            while (animationCoords[1] - 1 > 0 && gameBoard.canMove(currentCoords, upTile)) {
                startRightTransition.getChildren().add(moveUpTile());
                upTile[1] -= 1;
                currentTiming += 1;
            }
            timings.add(currentTiming);
            currentTiming = 0;

            //Left movement
            currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] leftTile = new int[]{animationCoords[0] - 2, animationCoords[1] - 1};
            while (animationCoords[0] - 1 > 0 && gameBoard.canMove(currentCoords, leftTile)) {
                startRightTransition.getChildren().add(moveLeftTile());
                leftTile[0] -= 1;
                currentTiming += 1;
            }
            timings.add(currentTiming);
            currentTiming = 0;

            //Down movement
            currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] downTile = new int[]{animationCoords[0] - 1, animationCoords[1]};
            while (animationCoords[1] < gameBoard.getBoardSizeY() && gameBoard.canMove(currentCoords, downTile)) {
                startRightTransition.getChildren().add(moveDownTile());
                upTile[1] += 1;
                currentTiming += 1;
            }
            timings.add(currentTiming);
            count +=1;
        }
        startRightTransition.play();
        return startRightTransition;
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
}
