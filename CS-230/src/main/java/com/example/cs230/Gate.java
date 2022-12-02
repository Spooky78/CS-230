package com.example.cs230;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Gate {
    private static final String GOLDEN_GATE_PATH = "goldGate.png";
    private static final String SILVER_GATE_PATH = "silverGate.png";
    private static final int GATE_SIZE = 50;
    private ImageView goldenGate = new ImageView();
    private ImageView silverGate = new ImageView();
    private StackPane silverGatePane = new StackPane();
    private StackPane goldenGatePane = new StackPane();
    private final Board currentBoard;

    public Gate(String gateType, Board currentBoard, int[] position) {
        createAGate(gateType, position);
        this.currentBoard = currentBoard;

    }

    protected void createAGate(String gateType, int[] position) {

        switch (gateType) {

            case "GOLDEN" -> {
                //int tileSize = currentBoard.getTileSize();
                int tileSize = 25;
                goldenGate = new ImageView(GOLDEN_GATE_PATH);
                goldenGate.setFitWidth(GATE_SIZE);
                goldenGate.setFitHeight(GATE_SIZE);
                goldenGatePane.getChildren().add(goldenGate);
                goldenGatePane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
                goldenGatePane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
            }
            case "SILVER" -> {
                //int tileSize = currentBoard.getTileSize();
                int tileSize = 25;
                silverGate = new ImageView(SILVER_GATE_PATH);
                silverGate.setFitWidth(GATE_SIZE);
                silverGate.setFitHeight(GATE_SIZE);
                silverGatePane.getChildren().add(silverGate);
                silverGatePane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
                silverGatePane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
            }
        }
    }

    public StackPane getGoldenGatePane() { return goldenGatePane; }

    public StackPane getSilverGatePane() {
        return silverGatePane;
    }
}
