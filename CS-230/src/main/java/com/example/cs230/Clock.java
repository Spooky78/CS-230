package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Clock {
    private static final String CLOCK_PATH = "/Items/clock.png";
    private static final int CLOCK_SIZE = 40;
    private ImageView clock = new ImageView();
    StackPane clockPane = new StackPane();
    private Board currentBoard;

    public Clock(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        createAClock(position);
    }

    protected void createAClock(int[] position) {
        Image clockImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(CLOCK_PATH)));
        clock = new ImageView(clockImage);
        clock.setFitWidth(CLOCK_SIZE);
        clock.setFitHeight(CLOCK_SIZE);
        clockPane.getChildren().add(clock);
        int tileSize = currentBoard.getTileSize();
        clockPane.setLayoutX((position[0] * tileSize) - (tileSize / 2.0));
        clockPane.setLayoutY((position[1] * tileSize) - (tileSize / 2.0));
    }

    protected void addingTime() {

    }

    protected void reducingTime() {

    }

    public StackPane getClockPane() {
        return clockPane;
    }
}
