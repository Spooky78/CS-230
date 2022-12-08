package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Lever extends Item{
    private static final String LEVER_GOLD_PATH = "/Items/goldKey.png";
    private static final String LEVER_SILVER_PATH = "/Items/silverKey.png";
    private StackPane leverPane = new StackPane();
    private Board currentBoard;
    private String leverColour;
    private int[] goldenLeverPosition;

    public Lever(Board currentBoard, int[] position, String colour) {
        this.currentBoard = currentBoard;
        this.leverColour = colour;
        goldenLeverPosition = position;
        createItem(leverColour, position);
    }

    protected void createItem(String colour, int[] position) {
        ImageView lever = new ImageView();
        leverColour = colour;
        switch (colour) {
            case "GOLD" -> lever.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(LEVER_GOLD_PATH))));
            case "SILVER" -> lever.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(LEVER_SILVER_PATH))));
        }
        lever.setFitWidth(33);
        lever.setFitHeight(25);

        leverPane.getChildren().add(lever);
        int tileSize = currentBoard.getTileSize();
        leverPane.setLayoutX((position[0] * tileSize) - (tileSize / 2.0));
        leverPane.setLayoutY((position[1] * tileSize) - (tileSize / 2.0));
    }

    public boolean isCollectedByPlayer(int[] playerCoords) {
        return playerCoords[0] + 1 == goldenLeverPosition[0] && playerCoords[1] + 1 == goldenLeverPosition[1];
    }

    public boolean isLeverCollisionNPC(int[] npcCoords) {
        return npcCoords[0] == goldenLeverPosition[0] && npcCoords[1] == goldenLeverPosition[1];
    }

    public String getLeverColour() {
        return leverColour;
    }
    @Override
    protected StackPane getStackPane() {
        return leverPane;
    }

    @Override
    protected int[] getCoords() {
        return goldenLeverPosition;
    }
}
