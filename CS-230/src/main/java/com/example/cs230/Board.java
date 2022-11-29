package com.example.cs230;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.layout.TilePane;

public class Board {
    private TilePane boardPane;
    private Tile[][] board;
    private int currentLevelID;
    private static int screenWidth;
    private static int boardSizeX;
    private static int boardSizeY;
    private static ArrayList<Tile> boardTile = new ArrayList<>();
    private static int[] playerStartCoords = new int[2];
    private static ArrayList<Integer> assassinStartCoords = new ArrayList<>();
    private static ArrayList<Integer> smartThiefStartCoords = new ArrayList<>();
    private static ArrayList<Integer> floorFollowingThiefStartCoords = new ArrayList<>();
    private static ArrayList<Integer> coin1Coords = new ArrayList<>();
    private static ArrayList<Integer> coin2Coords = new ArrayList<>();
    private static ArrayList<Integer> coin3Coords = new ArrayList<>();
    private static ArrayList<Integer> coin4Coords = new ArrayList<>();
    private static ArrayList<Integer> clockCoords = new ArrayList<>();
    private static int[] doorCoords = new int[2];
    private static ArrayList<Integer> leverCoords = new ArrayList<>();
    private static ArrayList<Integer> gateCoords = new ArrayList<>();
    private static ArrayList<Integer> bombCoords = new ArrayList<>();
    private static int seconds;

    public Board(int currentLevel, int screenWidth){
        currentLevelID = currentLevel;
        this.screenWidth = screenWidth;
        String filename = "CS-230/src/main/resources/Levels/Level00.txt";
        try {
            File circles = new File(filename);
            Scanner in = new Scanner(circles);
            readLineByLine(in);
        }

        catch (FileNotFoundException e){
            System.out.println("Could not find " + filename);
            System.exit(0);
        }
        createBoard();
    }

    private static void readLineByLine(Scanner in) {
        while(in.hasNextLine()) {
            String scannerVariable = in.nextLine();
            String[] params = scannerVariable.split(" ");

            switch (params[0]) {
                case "PLAYER":
                    playerCoordsReader(params);
                    break;
                case "ASSASSIN":
                    assassinCoordsReader(params);
                    break;
                case "STHIEF":
                    sThiefCoordsReader(params);
                    break;
                case "FFTHIEF":
                    ffThiefCoordsReader(params);
                case "COIN1":
                    coin1CoordsReader(params);
                    break;
                case "COIN2":
                    coin2CoordsReader(params);
                    break;
                case "COIN3":
                    coin3CoordsReader(params);
                    break;
                case "COIN4":
                    coin4CoordsReader(params);
                    break;
                case "CLOCK":
                    clockCoordsReader(params);
                    break;
                case "DOOR":
                    doorCoordsReader(params);
                    break;
                case "LEVER":
                    leverCoordsReader(params);
                    break;
                case "GATE":
                    gateCoordsReader(params);
                    break;
                case "BOMB":
                    bombCoordsReader(params);
                    break;
                case "SECONDS":
                    seconds = Integer.parseInt(params[1]);
                    break;
                case "SIZE":
                    sizeReader(params);
                    break;
                case "BOARD":
                    while(in.hasNextLine()){
                        String scannerVariableBoard = in.nextLine();
                        String[] paramsBoard = scannerVariableBoard.split(" ");
                        boardLineReader(paramsBoard);
                    }
                    break;
            }
        }
        in.close();
    }

    private static void playerCoordsReader(String[] params){
        playerStartCoords[0] = Integer.parseInt(params[1]);
        playerStartCoords[1] = Integer.parseInt(params[2]);
    }

    private static void assassinCoordsReader(String[] params){
        assassinStartCoords.add(Integer.parseInt(params[1]));
        assassinStartCoords.add(Integer.parseInt(params[2]));

    }

    private static void sThiefCoordsReader(String[] params){
        smartThiefStartCoords.add(Integer.parseInt(params[1]));
        smartThiefStartCoords.add(Integer.parseInt(params[2]));

    }

    private static void ffThiefCoordsReader(String[] params){
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[1]));
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[2]));
    }

    private static void coin1CoordsReader(String[] params){
        coin1Coords.add(Integer.parseInt(params[1]));
        coin1Coords.add(Integer.parseInt(params[2]));
    }

    private static void coin2CoordsReader(String[] params){
        coin2Coords.add(Integer.parseInt(params[1]));
        coin2Coords.add(Integer.parseInt(params[2]));
    }

    private static void coin3CoordsReader(String[] params){
        coin3Coords.add(Integer.parseInt(params[1]));
        coin3Coords.add(Integer.parseInt(params[2]));
    }

    private static void coin4CoordsReader(String[] params){
        coin4Coords.add(Integer.parseInt(params[1]));
        coin4Coords.add(Integer.parseInt(params[2]));
    }

    private static void clockCoordsReader(String[] params){
        clockCoords.add(Integer.parseInt(params[1]));
        clockCoords.add(Integer.parseInt(params[2]));
    }

    private static void doorCoordsReader(String[] params){
        doorCoords[0] = Integer.parseInt(params[1]);
        doorCoords[1] = Integer.parseInt(params[2]);
    }

    private static void leverCoordsReader(String[] params){
        leverCoords.add(Integer.parseInt(params[1]));
        leverCoords.add(Integer.parseInt(params[2]));
    }

    private static void gateCoordsReader(String[] params){
        gateCoords.add(Integer.parseInt(params[1]));
        gateCoords.add(Integer.parseInt(params[2]));
    }

    private static void bombCoordsReader(String[] params){
        bombCoords.add(Integer.parseInt(params[1]));
        bombCoords.add(Integer.parseInt(params[2]));
    }

    private static void sizeReader(String[] params){
        boardSizeX = Integer.parseInt(params[1]);
        boardSizeY = Integer.parseInt(params[2]);
    }

    private static void boardLineReader(String[] params){
        //TODO read in the tiles, then make tiles and put into boardTiles[][] in correct place.
        for(int i=0; i<boardSizeX; i++) {
            char[] tileIds = new char[4];
            String currentTile;
            currentTile = params[i];
            tileIds[0] = currentTile.charAt(0);
            tileIds[1] = currentTile.charAt(1);
            tileIds[2] = currentTile.charAt(2);
            tileIds[3] = currentTile.charAt(3);
            int tileSize = screenWidth/boardSizeX;
            Tile newTile = new Tile(tileIds[0], tileIds[1], tileIds[2], tileIds[3], tileSize);

            boardTile.add(newTile);
        }
    }

    private void createBoard(){
        boardPane = new TilePane();
        for (int i=0; i<boardSizeX*boardSizeY; i++){
            boardPane.getChildren().add(boardTile.get(i).getTile());
            //System.out.println(boardTile.get(i).getTileIds());
            boardPane.setMaxWidth(screenWidth);
            boardPane.setMinWidth(screenWidth);

        }
    }

    public boolean canMove(int[] currentTile, int[] newTile){
        int currentX = currentTile[0];
        int currentY = currentTile[1];
        int newX = newTile[0];
        int newY = newTile[1];
        board = new Tile[boardSizeX][boardSizeY];
        int index = 0;
        for (int i=0; i<boardSizeY; i++){
            for (int j=0; j<boardSizeX; j++){
                board[j][i] = boardTile.get(index);
                index++;
            }
        }
        char[] newIds = board[newX][newY].getTileIds();
        if(board[currentX][currentY].hasSubTile(newIds)){
            return true;
        } else {
            return false;
        }
    }
    public static int[] getPlayerStartCoords() {
        return playerStartCoords;
    }

    public static ArrayList<Integer> getAssassinStartCoords() {
        return assassinStartCoords;
    }

    public static ArrayList<Integer> getSmartThiefStartCoords() {
        return smartThiefStartCoords;
    }

    public static ArrayList<Integer> getFloorFollowingThiefStartCoords() {
        return floorFollowingThiefStartCoords;
    }

    public static int[] getDoorCoords() {
        return doorCoords;
    }

    public static int getBoardSizeX() {
        return boardSizeX;
    }

    public static int getBoardSizeY() {
        return boardSizeY;
    }

    public static ArrayList<Tile> getBoardTile() {
        return boardTile;
    }

    public int getTileSize(){
        int tileSize = screenWidth/boardSizeX;
        System.out.println(tileSize);
        return tileSize;
    }

    public TilePane getBoardPane(){
        return boardPane;
    }
}
