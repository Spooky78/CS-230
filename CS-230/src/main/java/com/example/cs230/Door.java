package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * Creates a door in the level that the player can exit out of to proceed to the next level.
 * The player can exit when all loot is collected.
 *
 * @author Vic, Rex, Omar
 */
public class Door extends Item{

    private boolean isPicked = false;
    private static final String DOOR_PATH = "/Items/door.png";
    private final Board currentBoard;
    private ImageView door;
    StackPane doorPane = new StackPane();
    private int[] coords;

    /**
     * game loading and add door on the game screen.
     *
     * @param currentBoard    game screen
     * @param position door position
     */
    public Door(Board currentBoard, int[] position) {
        this.currentBoard = currentBoard;
        createItem(position);
    }

    /**
     * create door images and put these on the board, when collision by player,
     * reach to next level.
     *
     * @param position coin position
     */
    protected void createItem(int[] position) {
        Image gateImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(DOOR_PATH)));
        door = new ImageView(gateImage);
        door.setFitWidth(50);
        door.setFitHeight(50);
        doorPane.getChildren().add(door);
        int tileSize = currentBoard.getTileSize();
        doorPane.setLayoutX((position[0] * tileSize) - (tileSize / 2.0));
        doorPane.setLayoutY((position[1] * tileSize) - (tileSize / 2.0));
        coords = currentBoard.getDoorCoords();
    }

    /**
     * judgement player collided door or not.
     *
     * @param playerCoords player position
     * @return true when player collision with door
     */
    public boolean isCollectedByPlayer(int[] playerCoords) {
        if (playerCoords[0] +1 == coords[0] && playerCoords[1] +1 == coords[1] && !isPicked) {
            return true;
        } else {

            return false;
        }
    }

    /**
     * whether picked by player.
     */
    public void setPicked() {
        isPicked = true;
    }

    /**
     * get door pane.
     *
     * @return door pane
     */
    public StackPane getDoorPane() {
        return doorPane;
    }

    /**
     * get stack pane.
     *
     * @return door stack pane
     */
    @Override
    protected StackPane getStackPane() {
        return doorPane;
    }

    /**
     * get door position.
     *
     * @return door location
     */
    @Override
    protected int[] getCoords() {
        return coords;
    }
}

