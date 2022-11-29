package com.example.cs230;

import javafx.scene.image.ImageView;

public class Door{
    private static final String DOOR_PATH = "door.png";
    private ImageView door;

    public Door(){
        createItem();
    }


    protected void createItem() {
        door = new ImageView(DOOR_PATH);
        door.setFitWidth(50);
        door.setFitHeight(50);
    }

    public ImageView getDoor(){
        return door;
    }

}
