package com.example.cs230;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class Board {
    private TilePane boardPane;
    private Tile[][] board = new Tile[4][2];
    private int boardWidth = 4;
    private int boardY;
    private int currentLevelID;
    private FileReader readLevelFile = new FileReader();

    public Board(int currentLevel){
        currentLevelID = currentLevel;
        createBoard();
    }

    private void createBoard(){
        //FileReader levelTest = new FileReader();

        Tile testTile0 = new Tile('A','A','A','A');
        board[0][0] = testTile0;
        Tile testTile1 = new Tile('A','A','A','A');
        board[1][0] = testTile1;
        Tile testTile2 = new Tile('A','A','A','A');
        board[2][0] = testTile2;
        Tile testTile3 = new Tile('A','A','A','A');
        board[3][0] = testTile3;

        Tile testTile4 = new Tile('A','A','B','B');
        board[0][1] = testTile4;
        Tile testTile5 = new Tile('B','B','B','B');
        board[1][1] = testTile5;
        Tile testTile6 = new Tile('B','B','B','B');
        board[2][1] = testTile6;
        Tile testTile7 = new Tile('B','B','B','B');
        board[3][1] = testTile7;


        boardPane = new TilePane();
        //boardPane.setPadding(new Insets(50));
        boardPane.getChildren().addAll(testTile0.getTile(),testTile1.getTile(), testTile2.getTile(), testTile3.getTile());
        boardPane.getChildren().addAll(testTile4.getTile(),testTile5.getTile(),testTile6.getTile(),testTile7.getTile());
        boardPane.setPrefColumns(boardWidth);
        //boardPane.setPrefRows(2);
    }

    public boolean canMove(int[] currentTile, int[] newTile){
        int currentX = currentTile[0];
        int currentY = currentTile[1];
        int newX = newTile[0];
        int newY = newTile[1];
        char[] newIds = board[newX][newY].getTileIds();
        if(board[currentX][currentY].hasSubTile(newIds)){
            return true;
        } else {
            return false;
        }
    }

    public TilePane getBoardPane(){
        return boardPane;
    }

    public int getBoardWidth(){
        return boardWidth;
    }

    public int[] getStartCharacterPosition(){
        return readLevelFile.getPlayerStartCoords();
    }
}
