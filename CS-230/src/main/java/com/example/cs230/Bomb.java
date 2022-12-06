package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Bomb {
    private static final String BOMB_PATH = "/Items/bomb.png";
    private boolean isPicked = false;
    private ImageView bomb = new ImageView();
    private final StackPane bombPane = new StackPane();
    private final Board currentBoard;
    private int[] bombPosition;

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
        bombPosition = currentBoard.getDoorCoords();
    }

    public boolean isCollisionPlayer(int[] playerCoords) {
        return playerCoords[0] + 1 == bombPosition[0] && playerCoords[1] + 1 == bombPosition[1];
    }

        public StackPane getBombPane() {
            return bombPane;
        }
    }
