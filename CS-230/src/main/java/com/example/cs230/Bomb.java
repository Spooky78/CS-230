package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.util.Objects;

public class Bomb {
    private static final String BOMB_PATH = "/Items/bomb.png";
    private ImageView bomb = new ImageView();
    private StackPane bombPane = new StackPane();
    private Board currentBoard;

    public Bomb(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        createItem(position);
    }
    protected void createItem(int[] position) {
        Image bombImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(BOMB_PATH)));
        bomb = new ImageView(bombImage);
        bomb.setFitWidth(40.0);
        bomb.setFitHeight(40.0);
        bombPane.getChildren().add(bomb);
        int tileSize = currentBoard.getTileSize();
        bombPane.setLayoutX((position[0] * tileSize - tileSize / 2));
        bombPane.setLayoutY((position[1] * tileSize - tileSize / 2));
    }

    public StackPane getBombPane() { return bombPane; }
}
