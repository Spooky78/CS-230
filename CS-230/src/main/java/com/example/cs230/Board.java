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
    private int screenWidth;
    private int boardSizeX;
    private int boardSizeY;
    private ArrayList<Tile> boardTile = new ArrayList<>();
    private int[] playerStartCoords = new int[2];
    private ArrayList<Integer> assassinStartCoords = new ArrayList<>();
    private ArrayList<Integer> smartThiefStartCoords = new ArrayList<>();
    private ArrayList<Integer> floorFollowingThiefStartCoords = new ArrayList<>();
    private ArrayList<Integer> coin1Coords = new ArrayList<>();
    private ArrayList<Integer> coin2Coords = new ArrayList<>();
    private ArrayList<Integer> coin3Coords = new ArrayList<>();
    private ArrayList<Integer> coin4Coords = new ArrayList<>();
    private ArrayList<Integer> clockCoords = new ArrayList<>();
    private int[] doorCoords = new int[2];

    //index of coords is /2 for colour (e.g) colour index2 has coords at index2,3
    private ArrayList<String> leverColours = new ArrayList<>();
    private ArrayList<Integer> leverCoords = new ArrayList<>();
    private ArrayList<Integer> gate1Coords = new ArrayList<>();
    private ArrayList<Integer> gate2Coords = new ArrayList<>();
    private ArrayList<Integer> bombCoords = new ArrayList<>();
    private String assassinStartDirection;
    private int seconds;

    public Board(int currentLevel, int screenWidth) {
        currentLevelID = currentLevel;
        this.screenWidth = screenWidth;
        String filename = "CS-230/src/main/resources/Levels/Level00.txt";
        try {
            File circles = new File(filename);
            Scanner in = new Scanner(circles);
            readLineByLine(in);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find " + filename);
            System.exit(0);
        }
        createBoard();
    }

    private void readLineByLine(Scanner in) {
        while (in.hasNextLine()) {
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
                case "GATE1":
                    gate1CoordsReader(params);
                    break;
                case "GATE2":
                    gate2CoordsReader(params);
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
                    while (in.hasNextLine()) {
                        String scannerVariableBoard = in.nextLine();
                        String[] paramsBoard = scannerVariableBoard.split(" ");
                        boardLineReader(paramsBoard);
                    }
                    break;
            }
        }
        in.close();
    }

    private void playerCoordsReader(String[] params) {
        playerStartCoords[0] = Integer.parseInt(params[1]);
        playerStartCoords[1] = Integer.parseInt(params[2]);
    }

    private void assassinCoordsReader(String[] params) {
        assassinStartDirection = params[1];
        assassinStartCoords.add(Integer.parseInt(params[2]));
        assassinStartCoords.add(Integer.parseInt(params[3]));

    }

    private void sThiefCoordsReader(String[] params) {
        smartThiefStartCoords.add(Integer.parseInt(params[1]));
        smartThiefStartCoords.add(Integer.parseInt(params[2]));

    }

    private void ffThiefCoordsReader(String[] params) {
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[1]));
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[2]));
    }

    private void coin1CoordsReader(String[] params) {
        coin1Coords.add(Integer.parseInt(params[1]));
        coin1Coords.add(Integer.parseInt(params[2]));
    }

    private void coin2CoordsReader(String[] params) {
        coin2Coords.add(Integer.parseInt(params[1]));
        coin2Coords.add(Integer.parseInt(params[2]));
    }

    private void coin3CoordsReader(String[] params) {
        coin3Coords.add(Integer.parseInt(params[1]));
        coin3Coords.add(Integer.parseInt(params[2]));
    }

    private void coin4CoordsReader(String[] params) {
        coin4Coords.add(Integer.parseInt(params[1]));
        coin4Coords.add(Integer.parseInt(params[2]));
    }

    private void clockCoordsReader(String[] params) {
        clockCoords.add(Integer.parseInt(params[1]));
        clockCoords.add(Integer.parseInt(params[2]));
    }

    private void doorCoordsReader(String[] params) {
        doorCoords[0] = Integer.parseInt(params[1]);
        doorCoords[1] = Integer.parseInt(params[2]);
    }

    private void leverCoordsReader(String[] params) {
        leverColours.add(params[1]);
        leverCoords.add(Integer.parseInt(params[2]));
        leverCoords.add(Integer.parseInt(params[3]));
    }

    private void gate1CoordsReader(String[] params) {
        gate1Coords.add(Integer.parseInt(params[1]));
        gate1Coords.add(Integer.parseInt(params[2]));
    }

    private void gate2CoordsReader(String[] params) {
        gate2Coords.add(Integer.parseInt(params[1]));
        gate2Coords.add(Integer.parseInt(params[2]));
    }

    private void bombCoordsReader(String[] params) {
        bombCoords.add(Integer.parseInt(params[1]));
        bombCoords.add(Integer.parseInt(params[2]));
    }

    private void sizeReader(String[] params) {
        boardSizeX = Integer.parseInt(params[1]);
        boardSizeY = Integer.parseInt(params[2]);
    }

    private void boardLineReader(String[] params) {
        //TODO read in the tiles, then make tiles and put into boardTiles[][] in correct place.
        for (int i = 0; i < boardSizeX; i++) {
            char[] tileIds = new char[4];
            String currentTile;
            currentTile = params[i];
            tileIds[0] = currentTile.charAt(0);
            tileIds[1] = currentTile.charAt(1);
            tileIds[2] = currentTile.charAt(2);
            tileIds[3] = currentTile.charAt(3);
            int tileSize = screenWidth / boardSizeX;
            Tile newTile = new Tile(tileIds[0], tileIds[1], tileIds[2], tileIds[3], tileSize);

            boardTile.add(newTile);
        }
    }

    private void createBoard() {
        boardPane = new TilePane();
        for (int i = 0; i < boardSizeX * boardSizeY; i++) {
            boardPane.getChildren().add(boardTile.get(i).getTile());
            boardPane.setMaxWidth(screenWidth);
            boardPane.setMinWidth(screenWidth);

        }
    }

    public boolean canMove(int[] currentTile, int[] newTile) {
        int currentX = currentTile[0];
        int currentY = currentTile[1];
        int newX = newTile[0];
        int newY = newTile[1];
        board = new Tile[boardSizeX][boardSizeY];
        int index = 0;
        for (int i = 0; i < boardSizeY; i++) {
            for (int j = 0; j < boardSizeX; j++) {
                board[j][i] = boardTile.get(index);
                index++;
            }
        }
        char[] newIds = board[newX][newY].getTileIds();
        if (board[currentX][currentY].hasSubTile(newIds)) {
            return true;
        } else {
            return false;
        }
    }

    public int[] getPlayerStartCoords() {
        return playerStartCoords;
    }

    public String getAssassinStartDirection() {
        return assassinStartDirection;
    }

    public ArrayList<Integer> getAssassinStartCoords() {
        return assassinStartCoords;
    }

    public ArrayList<Integer> getSmartThiefStartCoords() {
        return smartThiefStartCoords;
    }

    public ArrayList<Integer> getFloorFollowingThiefStartCoords() {
        return floorFollowingThiefStartCoords;
    }

    public ArrayList<Integer> getCoin1Coords() {
        return coin1Coords;
    }

    public ArrayList<Integer> getCoin2Coords() {
        return coin2Coords;
    }

    public ArrayList<Integer> getCoin3Coords() {
        return coin3Coords;
    }

    public ArrayList<Integer> getCoin4Coords() {
        return coin4Coords;
    }

    public ArrayList<Integer> getClockCoords() {
        return clockCoords;
    }

    public ArrayList<String> getLeverColours() {
        return leverColours;
    }

    public ArrayList<Integer> getLeverCoords() {
        return leverCoords;
    }

    public ArrayList<Integer> getGate1Coords() {
        return gate1Coords;
    }

    public ArrayList<Integer> getGate2Coords() {
        return gate2Coords;
    }

    public ArrayList<Integer> getBombCoords() {
        return bombCoords;
    }

    public int getSeconds() {
        return seconds;
    }

    public int[] getDoorCoords() {
        return doorCoords;
    }

    public int getBoardSizeX() {
        return boardSizeX;
    }

    public int getBoardSizeY() {
        return boardSizeY;
    }

    public ArrayList<Tile> getBoardTile() {
        return boardTile;
    }

    public int getTileSize() {
        int tileSize = screenWidth / boardSizeX;
        return tileSize;
    }

    public TilePane getBoardPane() {
        return boardPane;
    }
}
