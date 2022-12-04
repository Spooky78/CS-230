package com.example.cs230;

import java.util.Objects;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FloorFollowingThief extends NPC {

    private static final String FFTHIEF_DOWN_PATH = "/Skeleton/SkeletonDown.png";
    private static final String FFTHIEF_UP_PATH = "/Skeleton/keletonUp.png";
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
                //startRightMovement();
                startRightAnticlockwiseTransition();
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

    private void startRightAnticlockwiseTransition() {
        SequentialTransition startRightTransition = new SequentialTransition();

        int count = 0;
        while (count < 100) {
            //right movement
            int[] currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] rightTile = new int[]{animationCoords[0], animationCoords[1] - 1};
            while (animationCoords[0] < gameBoard.getBoardSizeX() && gameBoard.canMove(currentCoords, rightTile)) {
                startRightTransition.getChildren().add(moveRightTile());
                rightTile[0] += 1;
            }

            //Up movement
            currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] upTile = new int[]{animationCoords[0] - 1, animationCoords[1] - 2};
            while (animationCoords[1] - 1 > 0 && gameBoard.canMove(currentCoords, upTile)) {
                startRightTransition.getChildren().add(moveUpTile());
                upTile[1] -= 1;
            }

            //Left movement
            currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] leftTile = new int[]{animationCoords[0] - 2, animationCoords[1] - 1};
            while (animationCoords[0] - 1 > 0 && gameBoard.canMove(currentCoords, leftTile)) {
                startRightTransition.getChildren().add(moveLeftTile());
                leftTile[0] -= 1;
            }

            //DOwn movement
            currentCoords = new int[]{animationCoords[0] - 1, animationCoords[1] - 1};
            int[] downTile = new int[]{animationCoords[0] - 1, animationCoords[1]};
            while (animationCoords[1] < gameBoard.getBoardSizeY() && gameBoard.canMove(currentCoords, downTile)) {
                startRightTransition.getChildren().add(moveDownTile());
                upTile[1] += 1;
            }
            count +=1;
        }
        startRightTransition.play();
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

    public ImageView getffThief() {
        return ffThief;
    }
}
