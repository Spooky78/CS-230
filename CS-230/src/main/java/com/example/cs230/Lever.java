package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * Connected with gates for same colour, only if lever collected by player/npc,
 * the gate can disappear, and they can walk through.
 *
 * @author Ziming, Dimitrios
 */
public class Lever extends Item {
    private static final String LEVER_GOLD_PATH = "/Items/goldKey.png";
    private static final String LEVER_SILVER_PATH = "/Items/silverKey.png";
    private StackPane leverPane = new StackPane();
    private static final int LEVER_WIDTH = 33;
    private static final int LEVER_HEIGHT = 25;
    private Board currentBoard;
    private String leverColour;
    private int[] goldenLeverPosition;

    /**
     * game load and add lever on the screen.
     *
     * @param currentBoard game screen
     * @param position lever position
     * @param colour   lever type
     */
    public Lever(Board currentBoard, int[] position, String colour) {
        this.currentBoard = currentBoard;
        this.leverColour = colour;
        goldenLeverPosition = position;
        createItem(leverColour, position);
    }

    /**
     * create lever and put them on the board, decided by golden/silver.
     *
     * @param colour   lever type
     * @param position lever position
     */
    protected void createItem(String colour, int[] position) {
        ImageView lever = new ImageView();
        leverColour = colour;
        switch (colour) {
            case "GOLD" -> lever.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(LEVER_GOLD_PATH))));
            case "SILVER" -> lever.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(LEVER_SILVER_PATH))));
            default -> {
            }
        }
        lever.setFitWidth(LEVER_WIDTH);
        lever.setFitHeight(LEVER_HEIGHT);

        leverPane.getChildren().add(lever);
        int tileSize = currentBoard.getTileSize();
        leverPane.setLayoutX((position[0] * tileSize) - (tileSize / 2.0));
        leverPane.setLayoutY((position[1] * tileSize) - (tileSize / 2.0));
    }

    /**
     * judgement player collided lever or not.
     *
     * @param playerCoords player position
     * @return true when player collision with lever
     */
    public boolean isCollectedByPlayer(int[] playerCoords) {
        return playerCoords[0] + 1 == goldenLeverPosition[0] && playerCoords[1] + 1 == goldenLeverPosition[1];
    }

    /**
     * judgement player collided lever or not.
     *
     * @param npcCoords player position
     * @return true when player collision with lever
     */
    public boolean isLeverCollisionNPC(int[] npcCoords) {
        return npcCoords[0] == goldenLeverPosition[0] && npcCoords[1] == goldenLeverPosition[1];
    }

    /**
     * decide the type of the lever.
     *
     * @return lever type
     */
    public String getLeverColour() {
        return leverColour;
    }

    /**
     * get lever stack pane.
     *
     * @return lever pane
     */
    @Override
    protected StackPane getStackPane() {
        return leverPane;
    }

    /**
     * get lever position.
     *
     * @return lever position
     */
    @Override
    protected int[] getCoords() {
        return goldenLeverPosition;
    }
}
