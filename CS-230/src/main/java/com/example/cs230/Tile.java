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

public class Tile {
    private static final String A_TILE_PATH = "Tile1.png";
    private static final String B_TILE_PATH = "Tile2.png";
    private static final String C_TILE_PATH = "Tile3.png";
    private static final String D_TILE_PATH = "Tile4.png";
    private final TilePane tilePane = new TilePane();
    private final StackPane tileBorderPane = new StackPane();
    private final char[] tileIds = new char[4];
    private final int tileSize;

    /**
     * get from file reader and change the representing code to the image of tiles.
     * @param topLeft returns green image
     * @param topRight returns orange image
     * @param bottomLeft returns yellow imagine
     * @param bottomRight returns pink imagine
     */
    public Tile(char topLeft, char topRight, char bottomLeft, char bottomRight, int tileSize){
        this.tileSize = tileSize;
        tilePane.setPrefColumns(2);
        tilePane.setPrefRows(2);

        tileIds[0] = topLeft;
        tileIds[1] = topRight;
        tileIds[2] = bottomLeft;
        tileIds[3] = bottomRight;

        String[] tilePaths = new String[4];
        for(int i = 0; i < tileIds.length; i++) {
            switch (tileIds[i]) {
                case 'A' -> tilePaths[i] = A_TILE_PATH;
                case 'B' -> tilePaths[i] = B_TILE_PATH;
                case 'C' -> tilePaths[i] = C_TILE_PATH;
                case 'D' -> tilePaths[i] = D_TILE_PATH;
                default -> throw new IllegalArgumentException(
                    "Invalid tiles ID: " + tileIds[i]);
            }
        }
        for (int i=0; i< tileIds.length; i++) {
            ImageView currentTile = new ImageView(tilePaths[i]);
            currentTile.setFitHeight((tileSize/2)-1);
            currentTile.setFitWidth((tileSize/2)-1);
            tilePane.getChildren().add(currentTile);
        }

        makeTileBoarderPane();
    }

    public boolean hasSubTile(char[] newIds){
        for (char newId : newIds) {
            for (char tileId : tileIds) {
                if (tileId == newId) {
                    return true;
                }
            }
        }
        return false;
    }

    private void makeTileBoarderPane(){
        tileBorderPane.setBackground(new Background(new BackgroundFill(Color.BLACK,
            CornerRadii.EMPTY, Insets.EMPTY)));
        tileBorderPane.getChildren().add(tilePane);
        tileBorderPane.setAlignment(Pos.CENTER);
        tileBorderPane.setPrefSize(tileSize, tileSize);
    }

    /**
     * called from the game
     * @return the colour(s) of the tile
     */
    public StackPane getTile(){
        return tileBorderPane;
    }

    public char[] getTileIds(){
        return tileIds;
    }
}
