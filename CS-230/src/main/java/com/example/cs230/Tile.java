package com.example.cs230;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

/**
 * Creates tiles that are of specified colours for the board.
 *
 * @author Vic, Rex
 */
public class Tile {
    private static final String A_TILE_PATH = "Tile1.png";
    private static final String B_TILE_PATH = "Tile2.png";
    private static final String C_TILE_PATH = "Tile3.png";
    private static final String D_TILE_PATH = "Tile4.png";
    private static final int NUMBER_OF_SUB_TILES = 4;
    private static final int INDEX_BOTTOM_RIGHT = 3;
    private static final int INDEX_BOTTOM_LEFT = 2;
    private static final int INDEX_TOP_RIGHT = 1;
    private static final int INDEX_TOP_LEFT = 0;
    private final TilePane tilePane = new TilePane();
    private final StackPane tileBorderPane = new StackPane();
    private final char[] tileIds = new char[NUMBER_OF_SUB_TILES];
    private final int tileSize;

    /**
     * get from file reader and change the representing code to the image of tiles.
     *
     * @param topLeft     returns green image
     * @param topRight    returns orange image
     * @param bottomLeft  returns yellow imagine
     * @param bottomRight returns pink imagine
     * @param size        returns tile size
     */
    public Tile(char topLeft, char topRight, char bottomLeft, char bottomRight, int size) {
        this.tileSize = size;
        tilePane.setPrefColumns(2);
        tilePane.setPrefRows(2);

        tileIds[INDEX_TOP_LEFT] = topLeft;
        tileIds[INDEX_TOP_RIGHT] = topRight;
        tileIds[INDEX_BOTTOM_LEFT] = bottomLeft;
        tileIds[INDEX_BOTTOM_RIGHT] = bottomRight;

        String[] tilePaths = new String[NUMBER_OF_SUB_TILES];
        for (int i = 0; i < tileIds.length; i++) {
            switch (tileIds[i]) {
                case 'A' -> tilePaths[i] = A_TILE_PATH;
                case 'B' -> tilePaths[i] = B_TILE_PATH;
                case 'C' -> tilePaths[i] = C_TILE_PATH;
                case 'D' -> tilePaths[i] = D_TILE_PATH;
                default -> throw new IllegalArgumentException(
                        "Invalid tiles ID: " + tileIds[i]);
            }
        }
        for (int i = 0; i < tileIds.length; i++) {
            ImageView currentTile = new ImageView(tilePaths[i]);
            currentTile.setFitHeight((tileSize / 2.0) - 1);
            currentTile.setFitWidth((tileSize / 2.0) - 1);
            tilePane.getChildren().add(currentTile);
        }

        makeTileBoarderPane();
    }

    /**
     * check if it has sub tile.
     *
     * @param newIds new id
     * @return true if it has
     */
    public boolean hasSubTile(char[] newIds) {
        for (char newId : newIds) {
            for (char tileId : tileIds) {
                if (tileId == newId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Makes a border pane with tiles for drawing to the screen.
     */
    private void makeTileBoarderPane() {
        tileBorderPane.setBackground(new Background(new BackgroundFill(Color.BLACK,
                CornerRadii.EMPTY, Insets.EMPTY)));
        tileBorderPane.getChildren().add(tilePane);
        tileBorderPane.setAlignment(Pos.CENTER);
        tileBorderPane.setPrefSize(tileSize, tileSize);
    }

    /**
     * called from the game
     *
     * @return the colour(s) of the tile
     */
    public StackPane getTile() {
        return tileBorderPane;
    }

    /**
     * get tile id.
     *
     * @return the tile id
     */
    public char[] getTileIds() {
        return tileIds;
    }
}
