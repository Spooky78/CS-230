package com.example.cs230;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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

    //private StackPane assassinStack;
    public FlyingAssassin(Board board, int[] startCoords, StackPane stackPane) {
        gameBoard = board;
        coords = startCoords;
        assassinStackPane = stackPane;
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
        String startDirection = gameBoard.getAssassinStartDirection();
        switch (startDirection) {
            case "RIGHT":
                //startRightMovement();
                startMovementRightDirection();
                break;
            case "LEFT":
                //startLeftMovement();
                startMovementLeftDirection();
                break;
            case "DOWN":
                //startDownMovement();
                startMovementDownDirection();
                break;
            case "UP":
                //startUpMovement();
                startMovementUpDirection();
                break;
        }
    }

    private void startMovementRightDirection(){
        int count = 0;
        while(count<100){
            setImageRight();
            while(coords[0] > gameBoard.getBoardSizeX()){
                moveTile("RIGHT");
            }
            setImageLeft();
            while(coords[0] < -1){
                moveTile("LEFT");
            }
        }
    }

    private void startMovementLeftDirection(){
        int count = 0;
        while(count<100){
            setImageLeft();
            while(coords[0] < -1){
                moveTile("LEFT");
            }
            setImageRight();
            while(coords[0] > gameBoard.getBoardSizeX()){
                moveTile("RIGHT");
            }
        }
    }

    private void startMovementDownDirection(){
        int count = 0;
        while(count<100){
            setImageDown();
            while(coords[1] < gameBoard.getBoardSizeY()){
                moveTile("DOWN");
            }
            setImageUp();
            while (coords[1] > -1){
                moveTile("UP");
            }
        }
    }

    private  void startMovementUpDirection() {
        int count = 0;
        while (count < 100) {
            setImageUp();
            while (coords[1] > -1){
                moveTile("UP");
            }
            setImageDown();
            while(coords[1] < gameBoard.getBoardSizeY()){
                moveTile("DOWN");
            }
        }
    }

    private void moveTile(String direction){
        TranslateTransition moveAcrossTile = new TranslateTransition(Duration.millis(MILLS_DELAY));
        moveAcrossTile.setNode(assassin);
        moveAcrossTile.setDuration(Duration.millis(MILLS_DELAY));
        if (direction == "RIGHT"){
            moveAcrossTile.setByX(gameBoard.getBoardSizeX());
            coords[0] += 1;
        } else if (direction == "LEFT") {
            moveAcrossTile.setByX(-gameBoard.getBoardSizeX());
            coords[0] -= 1;
        } else if (direction == "UP") {
            moveAcrossTile.setByY(gameBoard.getBoardSizeY());
            coords[1] += 1;
        } else if (direction == "DOWN") {
            moveAcrossTile.setByY(-gameBoard.getTileSize());
            coords[1] -= 1;
        }
        moveAcrossTile.setCycleCount(1);
        moveAcrossTile.play();
    }

    private void startRightMovementOld() {
        int durationRightStart = (gameBoard.getBoardSizeX() - coords[0]) * MILLS_DELAY;
        SequentialTransition transition = moveStartRightTransition();
        setImageRight();
        transition.play();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                transition.pause();
                setImageLeft();
                transition.playFrom(Duration.millis(durationRightStart));
            }
        };
        timer.schedule(task, durationRightStart);

        int count = 1;
        //TODO: stop when end screen shown.
        //if you go through 1000 cycles, you've been playing too long!!
        while (count < 100) {
            moveRightTimer(timer, count, durationRightStart - SCHEDULING_DELAY, transition);
            moveLeftTimer(timer, count + 1, durationRightStart - SCHEDULING_DELAY, transition);
            count += 2;
        }
    }

//    private void startMoveRight2(){
//        int count = 0;
//        while(count<100){
//            setImageRight();
//            while(coords[0] > gameBoard.getBoardSizeX()){
//                moveRightTile();
//            }
//            setImageLeft();
//            while(coords[0] < -1){
//                moveLeftTile();
//            }
//        }
//    }
//
//    private void moveRightTile(){
//        TranslateTransition moveRight = new TranslateTransition(Duration.millis(MILLS_DELAY));
//        moveRight.setNode(assassin);
//        moveRight.setDuration(Duration.millis(MILLS_DELAY));
//        moveRight.setByX(gameBoard.getTileSize());
//        coords[0] +=1;
//        moveRight.setCycleCount(1);
//        moveRight.play();
//    }
//
//    private void moveLeftTile(){
//        TranslateTransition moveLeft = new TranslateTransition(Duration.millis(MILLS_DELAY));
//        moveLeft.setNode(assassin);
//        moveLeft.setDuration(Duration.millis(MILLS_DELAY));
//        moveLeft.setByX(-gameBoard.getTileSize());
//        coords[0] -=1;
//        moveLeft.setCycleCount(1);
//        moveLeft.play();
//    }

    private void startLeftMovement() {
        int durationLeftStart = (gameBoard.getBoardSizeX() - (gameBoard.getBoardSizeX() - coords[0]) - 1) * MILLS_DELAY;
        SequentialTransition transition = moveStartLeftTransition();
        setImageLeft();
        transition.play();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                transition.pause();
                setImageRight();
                transition.playFrom(Duration.millis(durationLeftStart));
            }
        };
        timer.schedule(task, durationLeftStart);
        int count = 1;
        //TODO: stop when end screen shown.
        //if you go through 1000 cycles, you've been playing too long!!
        while (count < 100) {
            moveLeftTimer(timer, count, durationLeftStart - SCHEDULING_DELAY, transition);
            moveRightTimer(timer, count + 1, durationLeftStart - SCHEDULING_DELAY, transition);
            count += 2;
        }
    }

    private void startDownMovement() {
        int durationDownStart = (gameBoard.getBoardSizeY() - coords[1]) * MILLS_DELAY;
        SequentialTransition transition = moveStartDownTransition();
        setImageDown();
        transition.play();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                transition.pause();
                setImageUp();
                transition.playFrom(Duration.millis(durationDownStart));
            }
        };
        timer.schedule(task, durationDownStart);

        int count = 1;
        //TODO: stop when end screen shown.
        //if you go through 1000 cycles, you've been playing too long!!
        while (count < 100) {
            moveDownTimer(timer, count, durationDownStart - SCHEDULING_DELAY, transition);
            moveUpTimer(timer, count + 1, durationDownStart - SCHEDULING_DELAY, transition);
            count += 2;
        }
    }

    private void startUpMovement() {
        int durationUpStart = (gameBoard.getBoardSizeY() - (gameBoard.getBoardSizeY() - coords[1]) - 1) * MILLS_DELAY;
        SequentialTransition transition = moveStartUpTransition();
        setImageUp();
        transition.play();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                transition.pause();
                setImageDown();
                transition.playFrom(Duration.millis(durationUpStart));
            }
        };
        timer.schedule(task, durationUpStart);
        int count = 1;
        //TODO: stop when end screen shown.
        //if you go through 1000 cycles, you've been playing too long!!
        while (count < 100) {
            moveDownTimer(timer, count + 1, durationUpStart - SCHEDULING_DELAY, transition);
            moveUpTimer(timer, count, durationUpStart - SCHEDULING_DELAY, transition);
            count += 2;
        }
    }

    private void moveRightTimer(Timer timer, int count, int startDelay, SequentialTransition transition) {
        long delayRight = (long) gameBoard.getBoardSizeX() * MILLS_DELAY * count;
        TimerTask taskLoop = new TimerTask() {
            public void run() {
                transition.pause();
                setImageRight();
                transition.playFrom(Duration.millis(delayRight + startDelay));
            }
        };
        timer.schedule(taskLoop, startDelay + delayRight);
    }

    private void moveLeftTimer(Timer timer, int count, int startDelay, SequentialTransition transition) {
        long delayLeft = (long) gameBoard.getBoardSizeX() * MILLS_DELAY * count;
        TimerTask taskLoopLeft = new TimerTask() {
            public void run() {
                transition.pause();
                setImageLeft();
                transition.playFrom(Duration.millis(delayLeft + startDelay));
            }
        };
        timer.schedule(taskLoopLeft, startDelay + delayLeft);
    }

    private void moveDownTimer(Timer timer, int count, int startDelay, SequentialTransition transition) {
        long delayUp = (long) gameBoard.getBoardSizeY() * MILLS_DELAY * count;
        TimerTask taskLoopUp = new TimerTask() {
            public void run() {
                transition.pause();
                setImageDown();
                transition.playFrom(Duration.millis(delayUp + startDelay));
            }
        };
        timer.schedule(taskLoopUp, startDelay + delayUp);
    }

    private void moveUpTimer(Timer timer, int count, int startDelay, SequentialTransition transition) {
        long delayDown = (long) gameBoard.getBoardSizeY() * MILLS_DELAY * count;
        TimerTask taskLoopDown = new TimerTask() {
            public void run() {
                transition.pause();
                setImageUp();
                transition.playFrom(Duration.millis(delayDown + startDelay));
            }
        };
        timer.schedule(taskLoopDown, startDelay + delayDown);
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

    private void setImageLeft() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_LEFT_PATH)));
        assassin.setImage(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
    }

    private void setImageRight() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_RIGHT_PATH)));
        assassin.setImage(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
    }

    private void setImageUp() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_UP_PATH)));
        assassin.setImage(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
    }

    private void setImageDown() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_DOWN_PATH)));
        assassin.setImage(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
    }

    public ImageView getAssassin() {
        return assassin;
    }
}
