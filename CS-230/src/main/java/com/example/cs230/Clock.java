package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Clock extends Item{
    private static final String CLOCK_PATH = "/Items/clock.png";
    private static final int CLOCK_SIZE = 40;
    private static int TIME_CHANGE = 5;
    private ImageView clock = new ImageView();
    StackPane clockPane = new StackPane();
    private Board currentBoard;
    private int[] clockPosition;
    private int currentTime;

    public Clock(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        clockPosition = position;
        createAClock(position);
        currentTime = TIME_CHANGE;
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

    public int getCurrentTime() {
        return currentTime;
    }

    public boolean isCollectedByPlayer(int[] playerCoords) {
        return playerCoords[0] + 1 == clockPosition[0] && playerCoords[1] + 1 == clockPosition[1];
    }

    public StackPane getClockPane() {
        return clockPane;
    }

    @Override
    protected StackPane getStackPane() {
        return clockPane;
    }

    @Override
    protected int[] getCoords() {
        return clockPosition;
    }
}
