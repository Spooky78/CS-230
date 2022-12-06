package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Lever {
    private static final String LEVER_GOLD_PATH = "/Items/goldKey.png";
    private static final String LEVER_SILVER_PATH = "/Items/silverKey.png";
    private StackPane leverPane = new StackPane();
    private Board currentBoard;
    private String leverColour;
    private int[] goldenLeverPosition;
    private int[] silverLeverPosition;

    public Lever(Board currentBoard, int[] position, String colour) {
        this.currentBoard = currentBoard;
        this.leverColour = colour;
        goldenLeverPosition = position;
        silverLeverPosition = position;
        createItem(leverColour, position);
    }

    protected void createItem(String colour, int[] position) {
        ImageView goldenLever = new ImageView();
        ImageView silverLever = new ImageView();
        leverColour = colour;
        switch (colour) {
            case "GOLD" -> goldenLever.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(LEVER_GOLD_PATH))));
            case "SILVER" -> silverLever.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(LEVER_SILVER_PATH))));
        }
        goldenLever.setFitWidth(33);
        goldenLever.setFitHeight(25);
        silverLever.setFitWidth(33);
        silverLever.setFitHeight(25);

        leverPane.getChildren().add(goldenLever);
        leverPane.getChildren().add(silverLever);
        int tileSize = currentBoard.getTileSize();
        leverPane.setLayoutX((position[0] * tileSize) - (tileSize / 2.0));
        leverPane.setLayoutY((position[1] * tileSize) - (tileSize / 2.0));
    }

    public StackPane getLeverPane() {
        return leverPane;
    }

    public String getLeverColour() {
        return leverColour;
    }

    public void collected() {

    }

    public boolean isCollectedByPlayer1(int[] playerCoords) {
        return playerCoords[0] + 1 == goldenLeverPosition[0] && playerCoords[1] + 1 == goldenLeverPosition[1];
    }

    public boolean isCollectedByPlayer2(int[] playerCoords) {
        return playerCoords[0] + 1 == silverLeverPosition[0] && playerCoords[1] + 1 == silverLeverPosition[1];
    }
}
