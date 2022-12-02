package com.example.cs230;

import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FloorFollowingThief extends Thief {

    private static final String FFTHIEF_DOWN_PATH = "/Skeleton/skeletonDown.png";
    private static final String FFTHIEF_UP_PATH = "/Skeleton/skeletonUp.png";
    private static final String FFTHIEF_LEFT_PATH = "/Skeleton/skeletonLeft.png";
    private static final String FFTHIEF_RIGHT_PATH = "/Skeleton/skeletonRight.png";
    private static final int MILLS_DELAY = 500;
    private static final int SCHEDULING_DELAY = 200;
    private ImageView ffThief;
    private Board gameBoard;
    private StackPane ffThiefStackPane;
    private int[] coords;

    /**
     *
     * @param board
     * @param startCoords
     * @param stackPane
     * @author Dimitrios
     */
    //private StackPane ffThiefStack;
    public FloorFollowingThief(Board board, int[] startCoords, StackPane stackPane) {
        gameBoard = board;
        coords = startCoords;
        ffThiefStackPane = stackPane;
        createNPC();
        move();
    }

    /**
     * @author Dimitrios
     */
    //@Override THIS MIGHT NEED TO BE UNCOMMENTED AT SOME POINT AFTER THE BEHAVIOUR IS IMPLEMENTED
    //CURRENTLY, IT HAS A RED UNDERLINE.
    protected void createNPC() {
        Image ffThiefImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_DOWN_PATH)));
        ffThief = new ImageView(ffThiefImage);
        ffThief.setFitWidth(50);
        ffThief.setFitHeight(50);
        int tileSize = gameBoard.getTileSize();
        ffThiefStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        ffThiefStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    protected void move() {

    }

    public ImageView getffThief() {
        return ffThief;
    }
}
