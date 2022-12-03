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
    private ImageView silverGate = new ImageView();
    private StackPane silverGatePane = new StackPane();
    private StackPane gatePane = new StackPane();
    private final Board currentBoard;

    public Gate(String gateType, Board board, int[] position) {
        this.currentBoard = board;
        createAGate(gateType, position);
    }

    protected void createAGate(String gateType, int[] position) {
        System.out.println(currentBoard.getTileSize());
        int tileSize = currentBoard.getTileSize();
        Image gateImage;
        switch (gateType) {
            case "GOLD":
                gateImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(GOLDEN_GATE_PATH)));
                gate = new ImageView(gateImage);
                break;
            case "SILVER":
                gateImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(SILVER_GATE_PATH)));
                gate = new ImageView(gateImage);
                break;
        }
        gate.setFitWidth(GATE_SIZE);
        gate.setFitHeight(GATE_SIZE);
        gatePane.getChildren().add(gate);
        gatePane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
        gatePane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
    }

    public StackPane getGatePane() { return gatePane; }

}
