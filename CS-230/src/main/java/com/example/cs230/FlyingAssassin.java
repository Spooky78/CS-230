package com.example.cs230;

import java.util.Objects;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FlyingAssassin extends NPC{
    private static final String ASSASSIN_DOWN_PATH = "/Assassin/assassinDown.png";
    private static final String ASSASSIN_UP_PATH = "/Assassin/assassinUp.png";
    private static final String ASSASSIN_LEFT_PATH = "/Assassin/assassinLeft.png";
    private static final String ASSASSIN_RIGHT_PATH = "/Assassin/assassinRight.png";
    private ImageView assassin;
    private Board gameBoard;
    private StackPane assassinStackPane;
    private int[] coords;
    //private StackPane assassinStack;
    public FlyingAssassin(Board board, int[] startCoords, StackPane stackPane){
        gameBoard = board;
        coords = startCoords;
        assassinStackPane = stackPane;
        createNPC();
        move();
    }

    @Override
    protected void createNPC(){
        Image assassinImage = new Image(
            Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_DOWN_PATH)));
        assassin = new ImageView(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
        int tileSize = gameBoard.getTileSize();
        assassinStackPane.setLayoutX((coords[0]*tileSize) - (tileSize/2));
        assassinStackPane.setLayoutY((coords[1]*tileSize) - (tileSize/2));
        //assassinStack.getChildren().add(assassin);
    }

    protected void move(){
        String startDirection = gameBoard.getAssassinStartDirection();
        switch (startDirection){
            case "RIGHT":
                moveStartRight();
                break;
        }
    }

    private void moveStartRight(){
        //setImageRight();
        int moveLeftStart = gameBoard.getBoardSizeX() - coords[0];
        TranslateTransition moveRightStartLoop = new TranslateTransition();
        moveRightStartLoop.setNode(assassin);
        moveRightStartLoop.setDuration(Duration.millis(moveLeftStart*100));
        moveRightStartLoop.setByX(moveLeftStart * gameBoard.getTileSize());

        SequentialTransition moveLeftStartTimeline = new SequentialTransition();
        moveLeftStartTimeline.getChildren().addAll(moveRightStartLoop, moveHorizontalStartRight());
        moveLeftStartTimeline.play();
    }

    private SequentialTransition moveHorizontalStartRight(){
        //setImageLeft();
        TranslateTransition moveLeft = new TranslateTransition();
        moveLeft.setNode(assassin);
        moveLeft.setDuration(Duration.millis(gameBoard.getBoardSizeX()*100));
        moveLeft.setByX(-((gameBoard.getBoardSizeX() -1) * gameBoard.getTileSize()));

        //setImageRight();
        TranslateTransition moveRight = new TranslateTransition();
        moveRight.setNode(assassin);
        moveRight.setDuration(Duration.millis(gameBoard.getBoardSizeX()*100));
        moveRight.setByX((gameBoard.getBoardSizeX() -1) * gameBoard.getTileSize());


        SequentialTransition moveHorizontal = new SequentialTransition();
        moveHorizontal.getChildren().addAll(moveLeft, moveRight);
        moveHorizontal.setCycleCount(SequentialTransition.INDEFINITE);
        return moveHorizontal;
    }

    private void setImageLeft(){
        Image assassinImage = new Image(
            Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_LEFT_PATH)));
        //assassin = new ImageView(assassinImage);
        assassin.setImage(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
    }

    private void setImageRight(){
        Image assassinImage = new Image(
            Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_RIGHT_PATH)));
        //assassin = new ImageView(assassinImage);
        assassin.setImage(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
    }

    public ImageView getAssassin(){
        return assassin;
    }
}
