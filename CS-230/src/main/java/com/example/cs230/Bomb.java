package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class for properties of bombs.
 *
 * @author Dimitrios, Chrysis, Arran
 */
public class Bomb extends Item {
    private static final double IMAGE_SIZE = 40.0;
    private static final int TIME_TO_EXPLODE = 3000;
    private static final String BOMB_PATH = "/Items/bomb.png";
    private final StackPane bombPane = new StackPane();
    private final Board currentBoard;
    private final int[] coords;
    private ImageView bomb = new ImageView();
    private boolean timerStarted = false;
    private boolean isExploded = false;
    private Timer t;

    /**
     * Bomb.
     *
     * @param currentBoard the board
     * @param position the current position
     */
    public Bomb(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        coords = position;
        createItem(position);
    }

    /**
     * creates bomb items and puts them on the screen.
     *
     * @param position the position
     */
    protected void createItem(int[] position) {
        Image bombImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(BOMB_PATH)));
        bomb = new ImageView(bombImage);
        bomb.setFitWidth(IMAGE_SIZE);
        bomb.setFitHeight(IMAGE_SIZE);
        bombPane.getChildren().add(bomb);
        int tileSize = currentBoard.getTileSize();
        bombPane.setLayoutX((position[0] * tileSize - tileSize / 2));
        bombPane.setLayoutY((position[1] * tileSize - tileSize / 2));
    }

    /**
     * checks if the player has collided with the bomb's triggering radius.
     *
     * @param playerCoords player postion
     * @return true if there is a collision
     */
    public boolean isCollisionPlayer(int[] playerCoords) {
        if (playerCoords[0] + 2 == coords[0] && playerCoords[1] + 1 == coords[1]) {
            return true;
        }
        if (playerCoords[0] == coords[0] && playerCoords[1] + 1 == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 1 == coords[0] && playerCoords[1] + 2 == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 1 == coords[0] && playerCoords[1] == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 2 == coords[0] && playerCoords[1] == coords[1]) {
            return true;
        }
        if (playerCoords[0] + 2 == coords[0] && playerCoords[1] + 2 == coords[1]) {
            return true;
        }
        if (playerCoords[0] == coords[0] && playerCoords[1] == coords[1]) {
            return true;
        }
        if (playerCoords[0] == coords[0] && playerCoords[1] + 2 == coords[1]) {
            return true;
        }
        return false;
    }

    /**
     * checks if the npc has collided with the bomb's triggering radius.
     *
     * @param thiefCoords thief postion
     * @return true if there is a collision
     */
    public boolean isBombCollisionNPC(int[] thiefCoords) {
        if (thiefCoords[0] + 1 == coords[0] && thiefCoords[1] == coords[1]) {
            return true;
        }
        if (thiefCoords[0] - 1 == coords[0] && thiefCoords[1] == coords[1]) {
            return true;
        }
        if (thiefCoords[0] == coords[0] && thiefCoords[1] + 1 == coords[1]) {
            return true;
        }
        if (thiefCoords[0] == coords[0] && thiefCoords[1] - 1 == coords[1]) {
            return true;
        }
        if (thiefCoords[0] + 1 == coords[0] && thiefCoords[1] - 1 == coords[1]) {
            return true;
        }
        if (thiefCoords[0] + 1 == coords[0] && thiefCoords[1] + 1 == coords[1]) {
            return true;
        }
        if (thiefCoords[0] - 1 == coords[0] && thiefCoords[1] - 1 == coords[1]) {
            return true;
        }
        if (thiefCoords[0] - 1 == coords[0] && thiefCoords[1] + 1 == coords[1]) {
            return true;
        }
        return false;
    }

    /**
     * start countdown.
     */
    public void countdown() {
        if (!timerStarted) {
            t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExploded = true;
                }
            }, TIME_TO_EXPLODE);
        }
        timerStarted = true;
    }

    /**
     * whether bomb exploded.
     *
     * @return true if it exploded
     */
    public Boolean isExploded() {
        return isExploded;
    }

    /**
     * gets the stack pane.
     *
     * @return stack pane
     */
    @Override
    protected StackPane getStackPane() {
        return bombPane;
    }

    /**
     * gets bomb position.
     *
     * @return bomb position
     */
    @Override
    protected int[] getCoords() {
        return coords;
    }
}
