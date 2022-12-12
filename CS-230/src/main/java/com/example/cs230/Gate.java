package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * Gate on the board and player/npc cant touch it, only if after lever collided, gate disappear.
 *
 * @author Ziming, Dimitrios
 */
public class Gate extends Item {
    private static final String GOLDEN_GATE_PATH = "/Items/goldGate.png";
    private static final String SILVER_GATE_PATH = "/Items/silverGate.png";
    private static final int GATE_SIZE = 50;
    private ImageView gate = new ImageView();
    private StackPane gatePane = new StackPane();
    private int[] gatePosition;
    private int[] silverGatePosition;
    private boolean canPass;
    private final Board currentBoard;
    private String colour; //decide types of gate
    private String gateType;

    /**
     * game loading and add gate on the game screen.
     * Initialisation status: the gate cannot be passed.
     *
     * @param gateType golden/silver gate
     * @param board    game screen
     * @param position gate position
     */
    public Gate(String gateType, Board board, int[] position) {
        this.gateType = gateType;
        this.currentBoard = board;
        colour = gateType;
        gatePosition = position;
        createAGate(gateType, position);
        canPass = false;
    }

    /**
     * create door images and put these on the board,decided by golden/silver.
     *
     * @param gateType gate type
     * @param position gate position
     */
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
            default -> {
            }
        }
        gate.setFitWidth(GATE_SIZE);
        gate.setFitHeight(GATE_SIZE);
        gatePane.getChildren().add(gate);
        gatePane.setLayoutX((position[0] * tileSize) - (tileSize / 2.0));
        gatePane.setLayoutY((position[1] * tileSize) - (tileSize / 2.0));
    }

    /**
     * judgement player collided gate or not.
     *
     * @param playerCoords player position
     * @return true when player collision with gate
     */
    public boolean isCollisionPlayer(int[] playerCoords) {
        return playerCoords[0] == gatePosition[0] + 1 && playerCoords[1] == gatePosition[1];
    }

    /**
     * judgement for whether gate can be passed through.
     *
     * @return true when player/npc collect lever
     */
    public boolean getCanPass() {
        return canPass;
    }

    /**
     * get gate colour.
     *
     * @return gate colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * get gate type.
     *
     * @return 1 for gold gate/ 2 for silver gate
     */
    public int getGateType() {
        switch (gateType) {
            case "GOLD":
                return 1;
            default:
                return 2;
        }
    }

    /**
     * get gate stack pane.
     *
     * @return gate stack pane
     */
    @Override
    protected StackPane getStackPane() {
        return gatePane;
    }

    /**
     * get gate position.
     *
     * @return gate position
     */
    @Override
    protected int[] getCoords() {
        return gatePosition;
    }
}
