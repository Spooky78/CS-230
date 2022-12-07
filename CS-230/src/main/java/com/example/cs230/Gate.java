package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Gate {
    private static final String GOLDEN_GATE_PATH = "/Items/goldGate.png";
    private static final String SILVER_GATE_PATH = "/Items/silverGate.png";
    private static final int GATE_SIZE = 50;
    private ImageView gate = new ImageView();
    private StackPane gatePane = new StackPane();
    private int[] gatePosition;
    private int[] silverGatePosition;
    private boolean canPass;
    private final Board currentBoard;
    private String colour;

    public Gate(String gateType, Board board, int[] position) {
        this.currentBoard = board;
        colour = gateType;
        gatePosition = position;
        createAGate(gateType, position);
        canPass = false;
    }

    protected void createAGate(String gateType, int[] position) {
        //System.out.println(currentBoard.getTileSize());
        int tileSize = currentBoard.getTileSize();
        Image gateImage;
        switch (gateType) {
            case "GOLD" -> {
                gateImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(GOLDEN_GATE_PATH)));
                gate = new ImageView(gateImage);
            }
            case "SILVER" -> {
                gateImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(SILVER_GATE_PATH)));
                gate = new ImageView(gateImage);
            }
        }
        gate.setFitWidth(GATE_SIZE);
        gate.setFitHeight(GATE_SIZE);
        gatePane.getChildren().add(gate);
        gatePane.setLayoutX((position[0] * tileSize) - (tileSize / 2.0));
        gatePane.setLayoutY((position[1] * tileSize) - (tileSize / 2.0));
    }

    public boolean isCollisionPlayer(int[] playerCoords) {
        return playerCoords[0] == gatePosition[0] + 1 && playerCoords[1] == gatePosition[1];
    }

    public boolean getCanPass() {
        return canPass;
    }
    public String getColour() {
        return colour;
    }

    public StackPane getGatePane() {
        return gatePane;
    }
}
