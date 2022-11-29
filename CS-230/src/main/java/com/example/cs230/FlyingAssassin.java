package com.example.cs230;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FlyingAssassin extends NPC{
    private static final String ASSASSIN_PATH = "/Assassin/assassinDown.png";
    private ImageView assassin;
    private Board gameBoard;
    //private StackPane assassinStack;
    public FlyingAssassin(Board board){
        gameBoard = board;
        createNPC();
        //move();
    }

    @Override
    protected void createNPC(){
        Image assassinImage = new Image(
            Objects.requireNonNull(getClass().getResourceAsStream(ASSASSIN_PATH)));
        assassin = new ImageView(assassinImage);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
        //assassinStack.getChildren().add(assassin);
    }

    protected void move(){
        TranslateTransition moveAssassin = new TranslateTransition();
        moveAssassin.setNode(assassin);
        moveAssassin.setDuration(Duration.millis(100));
        moveAssassin.setCycleCount(TranslateTransition.INDEFINITE);
        moveAssassin.setByX(500);
        moveAssassin.setAutoReverse(true);
        moveAssassin.play();
    }

    public ImageView getAssassin(){
        return assassin;
    }
}
