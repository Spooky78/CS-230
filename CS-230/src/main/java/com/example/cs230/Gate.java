package com.example.cs230;

import javafx.scene.image.ImageView;

public class Gate {
    private static final String GOLDEN_GATE_PATH = "goldGate.png";

    private static final String SILVER_GATE_PATH = "silverGate.png";
    private static final int GATE_SIZE = 50;
    private ImageView goldenGate = new ImageView();
    private ImageView silverGate = new ImageView();

    public Gate(String gateType) {
       createAGate(gateType);
    }

    protected void createAGate(String gateType) {
        switch (gateType) {
            case "GOLDEN" -> {
                goldenGate = new ImageView(GOLDEN_GATE_PATH);
                goldenGate.setFitWidth(GATE_SIZE);
                goldenGate.setFitHeight(GATE_SIZE);
            }
            case "SILVER" -> {
                silverGate = new ImageView(SILVER_GATE_PATH);
                silverGate.setFitWidth(GATE_SIZE);
                silverGate.setFitHeight(GATE_SIZE);
            }
        }
    }

    public ImageView getGoldenGate() {
        return goldenGate;
    }

    public ImageView getSilverGate() {
        return silverGate;
    }
}
