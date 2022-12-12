package com.example.cs230;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.layout.TilePane;

/**
 * Writing the board to the game screen.
 *
 * @author Everyone
 */
public class Board {
    private TilePane boardPane;
    private Tile[][] board;
    private int currentLevelID;
    private int screenWidth;
    private int boardSizeX;
    private int boardSizeY;
    private final ArrayList<Tile> boardTile = new ArrayList<>();
    private int[] playerStartCoords = new int[2];
    private ArrayList<String> assassinStartDirection = new ArrayList<>();
    private ArrayList<Integer> assassinStartCoords = new ArrayList<>();
    private ArrayList<Integer> smartThiefStartCoords = new ArrayList<>();
    private ArrayList<String> floorFollowingThiefColours = new ArrayList<>();
    private ArrayList<String> floorFollowingThiefDirectionStart = new ArrayList<>();
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
    private static ArrayList<String> tileT = new ArrayList<>();
    private int score;
    private String profileName;
    private int seconds;
    private int level;

    /**
     * Create a board, call it from a file, and read the board.
     *
     * @param currentLevel the current level
     * @param screenWidth  the width of the screen
     */
    public Board(String currentLevel, int screenWidth) {
        this.screenWidth = screenWidth;
        String filename = "CS-230/src/main/resources" + currentLevel;
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

    /**
     * reads the file line by line.
     *
     * @param in scanner
     */
    private void readLineByLine(Scanner in) {
        while (in.hasNextLine()) {
            String scannerVariable = in.nextLine();
            String[] params = scannerVariable.split(", ");

            switch (params[0]) {
                case "PLAYER":
                    playerCoordsReader(params);
                    break;
                case "PROFILE":
                    profileReader(params);
                    break;
                case "LEVEL":
                    levelReader(params);
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
                case "SCORE":
                    scoreReader(params);
                    break;
                case "BOARD":
                    int i = 0;
                    tileT.clear();
                    while (in.hasNextLine()) {
                        String scannerVariableBoard = in.nextLine();
                        tileT.add(scannerVariableBoard);
                        //System.out.println(scannerVariableBoard);
                        String[] paramsBoard = scannerVariableBoard.split(" ");
                        boardLineReader(paramsBoard);
                        i++;
                    }
                    break;
            }
        }
        in.close();
    }

    /**
     * reads the score.
     *
     * @param params the score
     */
    private void scoreReader(String[] params) {
        score = Integer.parseInt(params[1]);
    }

    /**
     * reads the player position.
     *
     * @param params the player position
     */
    private void playerCoordsReader(String[] params) {
        playerStartCoords[0] = Integer.parseInt(params[1]);
        playerStartCoords[1] = Integer.parseInt(params[2]);
    }

    /**
     * reads the profile.
     *
     * @param params the current profile
     */
    private void profileReader(String[] params) {
        profileName = params[1];
    }

    /**
     * reads the level.
     *
     * @param params the levels
     */
    private void levelReader(String[] params) {
        level = Integer.parseInt(params[1]);
    }

    /**
     * reads the position of the assassin.
     *
     * @param params the assassin information
     */
    private void assassinCoordsReader(String[] params) {
        assassinStartDirection.add(params[1]);
        assassinStartCoords.add(Integer.parseInt(params[2]));
        assassinStartCoords.add(Integer.parseInt(params[3]));

    }

    /**
     * reads the smart thief position.
     *
     * @param params smart thief information
     */
    private void sThiefCoordsReader(String[] params) {
        smartThiefStartCoords.add(Integer.parseInt(params[1]));
        smartThiefStartCoords.add(Integer.parseInt(params[2]));

    }

    /**
     * reads the floor following thief position.
     *
     * @param params floor following thief information
     */
    private void ffThiefCoordsReader(String[] params) {
        floorFollowingThiefColours.add(params[1]);
        floorFollowingThiefDirectionStart.add(params[2]);
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[3]));
        floorFollowingThiefStartCoords.add(Integer.parseInt(params[4]));
    }

    /**
     * reads the coin positions.
     *
     * @param params coin information
     */
    private void coinCoordsReader(String[] params) {
        coinColor.add(params[1]);
        coinCoords.add(Integer.parseInt(params[2]));
        coinCoords.add(Integer.parseInt(params[3]));
    }

    /**
     * reads the clock position.
     *
     * @param params clock information
     */
    private void clockCoordsReader(String[] params) {
        clockCoords.add(Integer.parseInt(params[1]));
        clockCoords.add(Integer.parseInt(params[2]));
    }

    /**
     * reads the door position.
     *
     * @param params door information
     */
    private void doorCoordsReader(String[] params) {
        doorCoords[0] = Integer.parseInt(params[1]);
        doorCoords[1] = Integer.parseInt(params[2]);
    }

    /**
     * reads the lever position.
     *
     * @param params lever information
     */
    private void leverCoordsReader(String[] params) {
        leverColours.add(params[1]);
        leverCoords.add(Integer.parseInt(params[2]));
        leverCoords.add(Integer.parseInt(params[3]));
    }

    /**
     * read golden gate position.
     *
     * @param params golden gate information
     */
    private void gate1CoordsReader(String[] params) {
        gate1Coords.add(Integer.parseInt(params[1]));
        gate1Coords.add(Integer.parseInt(params[2]));
    }

    /**
     * read silver gate position.
     *
     * @param params silver gate information
     */
    private void gate2CoordsReader(String[] params) {
        gate2Coords.add(Integer.parseInt(params[1]));
        gate2Coords.add(Integer.parseInt(params[2]));
    }

    /**
     * read the bomb position.
     *
     * @param params bomb information.
     */
    private void bombCoordsReader(String[] params) {
        bombCoords.add(Integer.parseInt(params[1]));
        bombCoords.add(Integer.parseInt(params[2]));
    }

    /**
     * read the size of the level.
     *
     * @param params size information.
     */
    private void sizeReader(String[] params) {
        boardSizeX = Integer.parseInt(params[1]);
        boardSizeY = Integer.parseInt(params[2]);
    }

    /**
     * read the board lines.
     *
     * @param params board information
     */
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

    /**
     * creates the board.
     */
    private void createBoard() {
        boardPane = new TilePane();
        for (int i = 0; i < boardSizeX * boardSizeY; i++) {
            boardPane.getChildren().add(boardTile.get(i).getTile());
            boardPane.setMaxWidth(screenWidth);
            boardPane.setMinWidth(screenWidth);
        }
    }

    /**
     * checks if the player can move onto the next tile.
     *
     * @param currentTile the current tile
     * @param newTile     the tile to move onto
     * @return true if the player can move there.
     */
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

    /**
     * checks if the NPC can move onto the next tile.
     *
     * @param currentTile the current tile
     * @param newTile     the tile to move onto
     * @return true if the NPC can move there.
     */
    public boolean canMoveNPC(int[] currentTile, int[] newTile) {
        int currentX = currentTile[0] - 1;
        int currentY = currentTile[1] - 1;
        int newX = newTile[0] - 1;
        int newY = newTile[1] - 1;
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

    /**
     * checks if the tile is the has colour.
     *
     * @param colour  tile colour
     * @param newTile the next tile
     * @return true if it's the same colour
     */
    public boolean isSameTile(char colour, int[] newTile) {
        int newX = newTile[0] - 1;
        int newY = newTile[1] - 1;
        board = new Tile[boardSizeX][boardSizeY];
        int index = 0;
        for (int i = 0; i < boardSizeY; i++) {
            for (int j = 0; j < boardSizeX; j++) {
                board[j][i] = boardTile.get(index);
                index++;
            }
        }
        for (int i = 0; i < board[newX][newY].getTileIds().length; i++) {
            if (board[newX][newY].getTileIds()[i] == colour) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets the player's start position.
     *
     * @return player start position
     */
    public int[] getPlayerStartCoords() {
        return playerStartCoords;
    }

    /**
     * gets the profile name.
     *
     * @return profile name
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * gets the score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * gets the level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * gets the board.
     *
     * @return the board
     */
    public static ArrayList<String> getBoardString() {
        return tileT;
    }

    /**
     * gets the assassin's start direction.
     *
     * @return the assassin's start direction
     */
    public ArrayList<String> getAssassinStartDirection() {
        return assassinStartDirection;
    }

    /**
     * gets the assassin's starting position.
     *
     * @return the assassin's starting position
     */
    public ArrayList<Integer> getAssassinStartCoords() {
        return assassinStartCoords;
    }

    /**
     * gets the smart thief's starting position.
     *
     * @return the smart thief's starting position
     */
    public ArrayList<Integer> getSmartThiefStartCoords() {
        return smartThiefStartCoords;
    }

    /**
     * gets the floor following thief's tile colours.
     *
     * @return the floor following thief's tile colours
     */
    public ArrayList<String> getFloorFollowingThiefColours() {
        return floorFollowingThiefColours;
    }

    /**
     * gets the floor following thief's starting direction.
     *
     * @return the floor following thief's starting direction
     */
    public ArrayList<String> getFloorFollowingThiefDirectionStart() {
        return floorFollowingThiefDirectionStart;
    }

    /**
     * gets the floor following thief's starting position.
     *
     * @return the floor following thief's starting position
     */
    public ArrayList<Integer> getFloorFollowingThiefStartCoords() {
        return floorFollowingThiefStartCoords;
    }

    /**
     * gets the coin position.
     *
     * @return coin position
     */
    public ArrayList<Integer> getCoinCoords() {
        return coinCoords;
    }

    /**
     * gets the clock position.
     *
     * @return clock position
     */
    public ArrayList<Integer> getClockCoords() {
        return clockCoords;
    }

    /**
     * gets the lever colours.
     *
     * @return lever colours
     */
    public ArrayList<String> getLeverColours() {
        return leverColours;
    }

    /**
     * gets lever position.
     *
     * @return lever position
     */
    public ArrayList<Integer> getLeverCoords() {
        return leverCoords;
    }

    /**
     * gets golden gate position.
     *
     * @return golden gate position
     */
    public ArrayList<Integer> getGate1Coords() {
        return gate1Coords;
    }

    /**
     * gets silver gate position.
     *
     * @return silver gate position
     */
    public ArrayList<Integer> getGate2Coords() {
        return gate2Coords;
    }

    /**
     * gets the bomb position.
     *
     * @return bomb position
     */
    public ArrayList<Integer> getBombCoords() {
        return bombCoords;
    }

    /**
     * gets the seconds remaining.
     *
     * @return the remaining seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * gets door position.
     *
     * @return door position
     */
    public int[] getDoorCoords() {
        return doorCoords;
    }

    /**
     * gets the x size of the board (width).
     *
     * @return the board width
     */
    public int getBoardSizeX() {
        return boardSizeX;
    }

    /**
     * gets the y size of the board (height).
     *
     * @return the board height
     */
    public int getBoardSizeY() {
        return boardSizeY;
    }

    /**
     * gets the coin colour.
     *
     * @return coin colour
     */
    public ArrayList<String> getCoinColor() {
        return coinColor;
    }

    /**
     * gets the board tile.
     *
     * @return board tile
     */
    public ArrayList<Tile> getBoardTile() {
        return boardTile;
    }

    /**
     * gets the size of the tiles.
     *
     * @return tile size
     */
    public int getTileSize() {
        System.out.println(boardSizeX + " " + boardSizeY);
        int tileSize = screenWidth / boardSizeX;
        return tileSize;
    }

    /**
     * gets the board pane.
     *
     * @return board pane
     */
    public TilePane getBoardPane() {
        return boardPane;
    }
}
