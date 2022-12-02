package com.example.cs230;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Lever {
    private static final String LEVER_PATH = "checkmark.png";
    private ImageView lever;
    private StackPane leverPane = new StackPane();
    private Board currentBoard;

    public Lever(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        createItem(position);
    }

    protected void createItem(int[] position) {
        lever = new ImageView(LEVER_PATH);
        lever.setFitWidth(20);
        lever.setFitHeight(20);
        leverPane.getChildren().add(lever);
        int tileSize = currentBoard.getTileSize();
        leverPane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
        leverPane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
    }

    public StackPane getLeverPane() {
        return leverPane;
    }

}

