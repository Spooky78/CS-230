package com.example.cs230;

import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class Tile {
    private static final String A_TILE_PATH = "Tile1.png";
    private static final String B_TILE_PATH = "Tile2.png";
    private static final String C_TILE_PATH = "Tile3.png";
    private static final String D_TILE_PATH = "Tile4.png";
    private TilePane tilePane = new TilePane();
    private char[] tileIds = new char[4];
    private String[] tilePaths = new String[4];

    /**
     * get from file reader and change the representing code to the image of tiles.
     * @param topLeft returns green image
     * @param topRight returns orange image
     * @param bottomLeft returns yellow imagine
     * @param bottomRight returns pink imagine
     */
    public Tile(char topLeft, char topRight, char bottomLeft, char bottomRight){
        tilePane.setPrefColumns(2);

        tileIds[0] = topLeft;
        tileIds[1] = topRight;
        tileIds[2] = bottomLeft;
        tileIds[3] = bottomRight;

        for(int i = 0; i < tileIds.length; i++) {
            switch (tileIds[i]) {
                case 'A':
                    tilePaths[i] = A_TILE_PATH;
                    break;
                case 'B':
                    tilePaths[i] = B_TILE_PATH;
                    break;
                case 'C':
                    tilePaths[i] = C_TILE_PATH;
                    break;
                case 'D':
                    tilePaths[i] = D_TILE_PATH;
                    break;
                default:
                    throw new IllegalArgumentException (
                            "Invalid tiles ID: " + tileIds[i]);
            }
        }
        for (int i=0; i< tileIds.length; i++) {
            ImageView currentTile = new ImageView(tilePaths[i]);
            tilePane.getChildren().add(currentTile);
        }
    }

    /**
     * called from the game
     * @return the colour(s) of the tile
     */
    public TilePane getTile(){
        return tilePane;
    }
}
