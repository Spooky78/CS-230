package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Door {

    private boolean isPicked = false;
    private static final String DOOR_PATH = "/Items/door.png";
    private final Board currentBoard;
    private ImageView door;
    StackPane doorPane = new StackPane();
    private int[] doorPosition;

    public Door(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        createItem(position);
    }


    protected void createItem(int[] position) {
        Image gateImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(DOOR_PATH)));
        door = new ImageView(gateImage);
        door.setFitWidth(50);
        door.setFitHeight(50);
        doorPane.getChildren().add(door);
        int tileSize = currentBoard.getTileSize();
        doorPane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
        doorPane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
        doorPosition = currentBoard.getDoorCoords();
    }

    public boolean isCollectedByPlayer(int[] playerCoords) {
        if (playerCoords[0] +1 == doorPosition[0] && playerCoords[1] +1 == doorPosition[1] && !isPicked) {

            isPicked = true;
            return true;
        } else {
            return false;
        }
    }


    public StackPane getDoorPane() {
        return doorPane;
    }

}

