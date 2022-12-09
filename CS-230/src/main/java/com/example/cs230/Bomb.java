package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Item {
    private static final String BOMB_PATH = "/Items/bomb.png";
    private final StackPane bombPane = new StackPane();
    private final Board currentBoard;
    private final int[] coords;
    private ImageView bomb = new ImageView();
    private boolean timerStarted = false;
    private boolean isExploded = false;
    private Timer t;

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
        if (playerCoords[0] + 2 == coords[0] && playerCoords[1] + 1 == coords[1]) {
            return true;
        }
        if (playerCoords[0] == coords[0] && playerCoords[1] + 1 == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 1 == coords[0] && playerCoords[1] + 2 == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 1 == coords[0] && playerCoords[1] == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 2 == coords[0] && playerCoords[1] == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 2 == coords[0] && playerCoords[1] + 2 == coords[1]) {
            return true;
        }
        if (playerCoords[0] == coords[0] && playerCoords[1] == coords[1]) {
            return true;
        }
        if (playerCoords[0]  == coords[0] && playerCoords[1] + 2== coords[1]) {
            return true;
        }
        return false;
    }

    public void countdown() {
        if (!timerStarted) {
            t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExploded = true;
                }
            }, 2500);
        }
        timerStarted = true;
    }

    public Boolean isExploded() {
        return isExploded;
    }

    public StackPane getBombPane() {
        return bombPane;
    }

    @Override
    protected StackPane getStackPane() {
        return bombPane;
    }

    @Override
    protected int[] getCoords() {
        return coords;
    }
}
