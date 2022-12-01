package com.example.cs230;

import javafx.scene.image.ImageView;

public class Clock {
    private static final String CLOCK_PATH = "clock.png";
    private static final int CLOCK_SIZE = 40;
    private ImageView clock = new ImageView();

    public Clock() {
        createAClock();
    }

    protected void createAClock() {
        clock = new ImageView(CLOCK_PATH);
        clock.setFitWidth(CLOCK_SIZE);
        clock.setFitHeight(CLOCK_SIZE);
    }

    protected void addingTime() {

    }

    protected void reducingTime() {

    }

    public ImageView getClock() {
        return clock;
    }
}
