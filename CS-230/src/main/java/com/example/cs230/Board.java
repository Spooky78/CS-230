package com.example.cs230;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.layout.TilePane;

/**
 * @authors Dimitrios, Vic, Rex
 */
public class Board {
    private TilePane boardPane;
    private Tile[][] board;
    private int currentLevelID;
    private int screenWidth;
    private int boardSizeX;
    private int boardSizeY;
    private ArrayList<Tile> boardTile = new ArrayList<>();
    private int[] playerStartCoords = new int[2];
    private ArrayList<String> assassinStartDirection = new ArrayList<>();
    private ArrayList<Integer> assassinStartCoords = new ArrayList<>();
    private ArrayList<Integer> smartThiefStartCoords = new ArrayList<>();
    private ArrayList<String> floorFollowingThiefColours = new ArrayList<>();
    private ArrayList<String> floorFollowingThiefDirectionStart = new ArrayList<>();
    private ArrayList<String> floorFollowingThiefDirectionTurn = new ArrayList<>();
    private ArrayList<Integer> floorFollowingThiefStartCoords = new ArrayList<>();
    private ArrayList<String> coinColor = new ArrayList<>();

    private ArrayList<Integer> coinCoords = new ArrayList<>();
    private ArrayList<Integer> clockCoords = new ArrayList<>();
    private int[] doorCoords = new int[2];

    //index of coords is /2 for colour (e.g) colour index2 has coords at index2,3
    private ArrayList<String> leverColours = new ArrayList<>();
    private ArrayList<Integer> leverCoords = new ArrayList<>();
    private ArrayList<Integer> gate1Coords = new ArrayList<>();
    private ArrayList<Integer> gate2Coords = new ArrayList<>();
    private ArrayList<Integer> bombCoords = new ArrayList<>();
    private int seconds;

    public Board(String currentLevel, int screenWidth) {
        this.screenWidth = screenWidth;
        String filename = "CS-230/src/main/resources/Levels/" + currentLevel;
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
                    break;
                case "COIN":
                    coinCoordsReader(params);
                    //run 11 times
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
        assassinStartDirection.add(params[1]);
        assassinStartCoords.add(Integer.parseInt(params[2]));
        assassinStartCoords.add(Integer.parseInt(params[3]));

    }

    private void sThiefCoordsReader(String[] params) {
        smartThiefStartCoords.add(Integer.parseInt(params[1]));
        smartThiefStartCoords.add(Integer.parseInt(params[2]));

    }

    private void ffThiefCoordsReader(String[] params) {
        floorFollowingThiefColours.add(params[1]);
        floorFollowingThiefDirectionStart.add(params[2]);
        floorFollowingThiefDirectionTurn.add(params[3]);
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[4]));
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[5]));
    }

    private void coinCoordsReader(String[] params) {
        coinColor.add(params[1]);
        coinCoords.add(Integer.parseInt(params[2]));
        coinCoords.add(Integer.parseInt(params[3]));
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

    private boolean isLockedTile(int[] newTile) {
        for(int i=0; i<bombCoords.size(); i+=2) {
            if (newTile[0]+1 == bombCoords.get(i) && newTile[1]+1 == bombCoords.get(i+1)){
                System.out.println("ON BOMB");
                return true;
            }
        }
        return false;
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
        if (board[currentX][currentY].hasSubTile(newIds) && !isLockedTile(newTile)) {
            return true;
        } else {
            return false;
        }
    }

    public int[] getPlayerStartCoords() {
        return playerStartCoords;
    }

    public ArrayList<String> getAssassinStartDirection() {
        return assassinStartDirection;
    }

    public ArrayList<Integer> getAssassinStartCoords() {
        return assassinStartCoords;
    }

    public ArrayList<Integer> getSmartThiefStartCoords() {
        return smartThiefStartCoords;
    }
    public ArrayList<String> getFloorFollowingThiefColours() {return floorFollowingThiefColours;}
    public ArrayList<String> getFloorFollowingThiefDirectionStart() {return floorFollowingThiefDirectionStart;}
    public ArrayList<String> getFloorFollowingThiefDirectionTurn() {return floorFollowingThiefDirectionTurn;}

    public ArrayList<Integer> getFloorFollowingThiefStartCoords() {
        return floorFollowingThiefStartCoords;
    }

    public ArrayList<Integer> getCoinCoords() {return coinCoords;}
    public ArrayList<Integer> getClockCoords() {
        return clockCoords;
    }
    public ArrayList<String> getLeverColours(){return leverColours;}

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

    public ArrayList<String> getCoinColor() {return coinColor;}

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
