package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Lever {
    private static final String LEVER_GOLD_PATH = "/Items/goldKey.png";
    private static final String LEVER_SILVER_PATH = "/Items/silverKey.png";
    private ImageView lever;
    private StackPane leverPane = new StackPane();
    private Board currentBoard;
    private String leverColour;

    public Lever(Board currentBoard, int[] position, String colour) {
        this.currentBoard = currentBoard;
        this.leverColour = colour;
        createItem(position);
    }

    protected void createItem(int[] position) {
        lever = new ImageView();
        switch (leverColour) {
            case "GOLD":
                lever.setImage(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(LEVER_GOLD_PATH))));
                break;
            case "SILVER":
                lever.setImage(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(LEVER_SILVER_PATH))));
                break;
        }
        lever.setFitWidth(33);
        lever.setFitHeight(25);
        leverPane.getChildren().add(lever);
        int tileSize = currentBoard.getTileSize();
        leverPane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
        leverPane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
    }

    public StackPane getLeverPane() {
        return leverPane;
    }
    public String getLeverColour() {return leverColour;}
}

