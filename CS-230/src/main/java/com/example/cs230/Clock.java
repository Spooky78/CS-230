package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * Achievement for clock belongs to Item, including creation and collision for player and Npc.
 *
 * @author Ziming, Omar
 */
public class Clock extends Item {
    private static final String CLOCK_PATH = "/Items/clock.png";
    private static final int CLOCK_SIZE = 40;
    private static int TIME_CHANGE = 5;
    private ImageView clock = new ImageView();
    StackPane clockPane = new StackPane();
    private Board currentBoard;
    private int[] clockPosition;
    private int currentTime;

    /**
     * game loading and add clock on the game screen.
     *
     * @param currentBoard game scene
     * @param position clock position
     */
    public Clock(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        clockPosition = position;
        createAClock(position);
        currentTime = TIME_CHANGE;
    }

    /**
     * create a clock within image,size and position on the board.
     *
     * @param position shows where the clock is
     */
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

    /**
     * get current time.
     *
     * @return current time
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * judgement player collided clock or not.
     *
     * @param playerCoords player position
     * @return whether player collision with clock
     */
    public boolean isCollectedByPlayer(int[] playerCoords) {
        return playerCoords[0] + 1 == clockPosition[0] && playerCoords[1] + 1 == clockPosition[1];
    }

    /**
     * judgement npc collided clock or not.
     *
     * @param npcCoords npc position
     * @return true when npc collision with clock
     */
    public boolean isClockCollisionNPC(int[] npcCoords) {
        return npcCoords[0] == clockPosition[0] && npcCoords[1] == clockPosition[1];
    }

    /**
     * get clock pane.
     *
     * @return clock pane
     */
    public StackPane getClockPane() {
        return clockPane;
    }

    /**
     * get stack pane.
     *
     * @return clock pane
     */
    @Override
    protected StackPane getStackPane() {
        return clockPane;
    }

    /**
     * get clock position.
     *
     * @return clock position
     */
    @Override
    protected int[] getCoords() {
        return clockPosition;
    }
}
