package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.Objects;

public class Bomb extends Item{
    private static final String BOMB_PATH = "/Items/bomb.png";
    private ImageView bomb = new ImageView();
    private StackPane bombPane = new StackPane();
    private Board currentBoard;
    private int[] coords;

    public Bomb(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        coords = position;
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
    public boolean isCollisionPlayer(int[] playerCoords) {

        if (playerCoords[0]+2  == coords[0]  && playerCoords[1]+1 == coords[1] ) {
            System.out.println("left");
            return true;
        }
        if (playerCoords[0]  == coords[0] && playerCoords[1]+1  == coords[1]) {
            System.out.println("right");

            return true;
        }
        if (playerCoords[0]+1 == coords[0] && playerCoords[1]+2  == coords[1]) {
            System.out.println("above");

            return true;
        }
        if (playerCoords[0]+1 == coords[0] && playerCoords[1]  == coords[1]) {
            System.out.println("below");

            return true;
        }
        else {
            return false;
        }
    }

    public StackPane getBombPane() { return bombPane; }

    @Override
    protected StackPane getStackPane() {
        return bombPane;
    }

    @Override
    protected int[] getCoords() {
        return coords;
    }
}
