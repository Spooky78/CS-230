package com.example.cs230;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FlyingAssassin extends NPC{
    private static final String ASSASSIN_PATH = "player.png";
    private ImageView assassin;
    //private StackPane assassinStack;
    public FlyingAssassin(){
        createNPC();
        move();
    }

    @Override
    protected void createNPC(){
        assassin = new ImageView(ASSASSIN_PATH);
        assassin.setFitWidth(50);
        assassin.setFitHeight(50);
        //assassinStack.getChildren().add(assassin);
    }

    protected void move(){
        TranslateTransition moveAssassin = new TranslateTransition();
        moveAssassin.setNode(assassin);
        moveAssassin.setDuration(Duration.millis(1000));
        moveAssassin.setCycleCount(TranslateTransition.INDEFINITE);
        moveAssassin.setByX(500);
        moveAssassin.setAutoReverse(true);
        moveAssassin.play();
    }

    public ImageView getAssassin(){
        return assassin;
    }
}
