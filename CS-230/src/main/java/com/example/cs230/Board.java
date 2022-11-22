package com.example.cs230;

public class Board {
    private Tile[][] boardTiles;
    private int[] playerStartCoords;
    private int[] assassinStartCoords;
    private int[] smartThiefStartCoords;
    private int[] floorFollowingThiefStartCoords;

    private int currentLevelID;
    //TODO: add item coord variables.

    public Board(int currentLevel){
        currentLevelID = currentLevel;
    }
    private void readLevelFile(){

        //if ( readINFile == 'PLAYER'){
            //playerStartCoords = readInCoors
        //}
        //Read in tiles, make tile object, put in boardTiles[][]
    }

    private void createBoard(){
        readLevelFile();
    }


}
