package com.example.cs230;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Door {

    private static final String DOOR_PATH = "door.png";
    private final Board currentBoard;
    private ImageView door;
    StackPane doorPane = new StackPane();

    public Door(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        createItem(position);
    }


    protected void createItem(int[] position) {
        door = new ImageView(DOOR_PATH);
        door.setFitWidth(50);
        door.setFitHeight(50);
        doorPane.getChildren().add(door);
        int tileSize = currentBoard.getTileSize();
        doorPane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
        doorPane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
    }

    public StackPane getDoorPane() {
        return doorPane;
    }

}

