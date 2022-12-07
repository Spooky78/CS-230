package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class SmartThief extends NPC {
    //no smart thief at the moment so just borrow one from flying assassin
    //can use reptile for smart thief now.
    private static final String SMARTTHIEF_DOWN_PATH = "/Reptile/ReptileDown.png";
    private static final String SMARTTHIEF_UP_PATH = "/Reptile/ReptileUp.png";
    private static final String SMARTTHIEF_LEFT_PATH = "/Reptile/ReptileLeft.png";
    private static final String SMARTTHIEF_RIGHT_PATH = "/Reptile/ReptileRight.png";
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
    protected void createNPC() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(SMARTTHIEF_DOWN_PATH)));
        sThief = new ImageView(assassinImage);
        sThief.setFitWidth(50);
        sThief.setFitHeight(50);
        int tileSize = gameBoard.getTileSize();
        sThiefStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        sThiefStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    protected void move() {
    }

    @Override
    protected StackPane getStackPane() {
        return sThiefStackPane;
    }

    @Override
    public int[] getCoords() {
        return coords;
    }

    public ImageView getSmartThief() {
        return sThief;
    }
}
