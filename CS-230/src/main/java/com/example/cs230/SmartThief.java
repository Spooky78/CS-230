package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class SmartThief extends NPC {
    //no smart thief at the moment so just borrow one from flying assassin
    private static final String SMARTTHIEF_PATH = "/Assassin/assassinDown.png";
    private ImageView sThief;
    private int[] coords;
    private StackPane sThiefStackPane;
    private Board gameBoard;

    public SmartThief(Board board, int[] startCoords, StackPane stackPane) {
        gameBoard = board;
        coords = startCoords;
        sThiefStackPane = stackPane;
        createNPC();
    }

    @Override
    protected void createNPC(){
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(SMARTTHIEF_PATH)));
        sThief = new ImageView(assassinImage);
        sThief.setFitWidth(50);
        sThief.setFitHeight(50);
        int tileSize = gameBoard.getTileSize();
        sThiefStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        sThiefStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    protected void move() {
    }

    public ImageView getSmartThief(){
        return sThief;
    }
}
