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

    public Tile(char topLeft, char topRight, char bottomLeft, char bottomRight){
        tilePane.setPrefColumns(2);

        tileIds[0] = topLeft;
        tileIds[1] = topRight;
        tileIds[2] = bottomLeft;
        tileIds[3] = bottomRight;

        for (int i=0; i< tileIds.length;i++){
            if (tileIds[i] == 'A'){
                tilePaths[i] = A_TILE_PATH;
            } else if (tileIds[i] == 'B') {
                tilePaths[i] = B_TILE_PATH;
            } else if (tileIds[i] == 'C') {
                tilePaths[i] = C_TILE_PATH;
            } else {
                tilePaths[i] = D_TILE_PATH;
            }
        }

        for (int i=0; i< tileIds.length;i++){
            ImageView currentTile = new ImageView(tilePaths[i]);
            tilePane.getChildren().add(currentTile);
        }
    }

    public TilePane getTile(){
        return tilePane;
    }
}
