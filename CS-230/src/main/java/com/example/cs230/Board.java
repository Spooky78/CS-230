package com.example.cs230;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class Board {
    private TilePane boardPane;
    private int boardWidth = 4;
    private int boardY;
    private int currentLevelID;
    //TODO: add item coord variables.

    public Board(int currentLevel){
        currentLevelID = currentLevel;
        createBoard();
    }

    private void createBoard(){
        //FileReader levelTest = new FileReader();

        Tile testTile0 = new Tile('A','A','A','A');
        Tile testTile1 = new Tile('A','A','A','A');
        Tile testTile2 = new Tile('A','A','A','A');
        Tile testTile3 = new Tile('A','A','A','A');

        Tile testTile4 = new Tile('A','A','B','B');
        Tile testTile5 = new Tile('B','B','B','B');
        Tile testTile6 = new Tile('B','B','B','B');
        Tile testTile7 = new Tile('B','B','B','B');


        boardPane = new TilePane();
        //boardPane.setPadding(new Insets(50));
        boardPane.getChildren().addAll(testTile0.getTile(),testTile1.getTile(), testTile2.getTile(), testTile3.getTile());
        boardPane.getChildren().addAll(testTile4.getTile(),testTile5.getTile(),testTile6.getTile(),testTile7.getTile());
        boardPane.setPrefColumns(boardWidth);
        //boardPane.setPrefRows(2);
    }

    public TilePane getBoardPane(){
        return boardPane;
    }

    public int getBoardWidth(){
        return boardWidth;
    }
}
