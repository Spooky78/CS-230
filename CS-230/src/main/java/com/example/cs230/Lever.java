package com.example.cs230;

import javafx.scene.image.ImageView;

public class Lever {
    private static final String LEVER_PATH = "checkmark.png";
    private ImageView lever;

    public Lever() {

        createItem();
    }

    protected void createItem() {
        lever = new ImageView(LEVER_PATH);
        lever.setFitWidth(20);
        lever.setFitHeight(20);
    }

    public ImageView getLever() {
        return lever;
    }

}

