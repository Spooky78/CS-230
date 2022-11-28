package com.example.cs230;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private static int boardSizeX;
    private static int boardSizeY;
    private Tile[][] boardTiles;
    private static int[] playerStartCoords = new int[2];
    private int[] assassinStartCoords;
    private int[] smartThiefStartCoords;
    private int[] floorFollowingThiefStartCoords;

    /**
     * Reads the level file and extracts the starting coordinates
     * or other relevant information of the entities.
     *
     * @param args
     * @author user6073
     */
    public static void main(String[] args) {
        try {
            File levelFile = new File("CS-230/src/main/resources/Level00.txt");
            Scanner levelFileReader = new Scanner(levelFile);
            while (levelFileReader.hasNextLine()) {
                String data = levelFileReader.nextLine();
                //System.out.println(data);

                if (data.contains("PLAYER")) {
                    String startCoordsString = data.substring(7);
                    startCoordsString.split(" ");
                    System.out.println("Player coordinates: " + startCoordsString);
                } else if (data.contains("ASSASSIN")) {
                    String startCoordsString = data.substring(9);
                    startCoordsString.split(" ");
                    System.out.println("Assassin coordinates: " + startCoordsString);
                } else if (data.contains("STHIEF")) {
                    String startCoordsString = data.substring(7);
                    startCoordsString.split(" ");
                    System.out.println("Smart Thief coordinates: " + startCoordsString);
                } else if (data.contains("FFTHIEF")) {
                    String startCoordsString = data.substring(8);
                    startCoordsString.split(" ");
                    System.out.println("Floor Following Thief coordinates and direction: " +
                            startCoordsString);
                } else if (data.contains("COIN1")) {
                    String startCoordsString = data.substring(6);
                    startCoordsString.split(" ");
                    System.out.println("Coin 1 coordinates: " + startCoordsString);
                } else if (data.contains("COIN2")) {
                    String startCoordsString = data.substring(6);
                    startCoordsString.split(" ");
                    System.out.println("Coin 2 coordinates: " + startCoordsString);
                } else if (data.contains("COIN3")) {
                    String startCoordsString = data.substring(6);
                    startCoordsString.split(" ");
                    System.out.println("Coin 3 coordinates: " + startCoordsString);
                } else if (data.contains("COIN4")) {
                    String startCoordsString = data.substring(6);
                    startCoordsString.split(" ");
                    System.out.println("Coin 4 coordinates: " + startCoordsString);
                } else if (data.contains("CLOCK")) {
                    String startCoordsString = data.substring(6);
                    startCoordsString.split(" ");
                    System.out.println("Clock coordinates: " + startCoordsString);
                } else if (data.contains("DOOR")) {
                    String startCoordsString = data.substring(5);
                    startCoordsString.split(" ");
                    System.out.println("Door coordinates: " + startCoordsString);
                } else if (data.contains("LEVER")) {
                    String startCoordsString = data.substring(6);
                    startCoordsString.split(" ");
                    System.out.println("Lever coordinates: " + startCoordsString);
                } else if (data.contains("GATE")) {
                    String startCoordsString = data.substring(5);
                    startCoordsString.split(" ");
                    System.out.println("Gate coordinates: " + startCoordsString);
                } else if (data.contains("BOMB")) {
                    String startCoordsString = data.substring(5);
                    startCoordsString.split(" ");
                    System.out.println("Bomb coordinates: " + startCoordsString);
                } else if (data.contains("SECONDS")) {
                    String startCoordsString = data.substring(8);
                    startCoordsString.split(" ");
                    System.out.println("Seconds: " + startCoordsString);
                } else if (data.contains("SIZE")) {
                    String startCoordsString = data.substring(5);
                    startCoordsString.split(" ");
                    System.out.println("Size dimensions: " + startCoordsString);
                } else if (data.contains("BOARD")) {
                    String startCoordsString = data.substring(5);
                    startCoordsString.split(" ");
                    System.out.println("Board: " + startCoordsString);
                    //Select every line "BOARD" until the end of the file.
                }
            }
            levelFileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

//    public String substring(int beginIndex) {
//        if (beginIndex < 0) {
//            throw new StringIndexOutOfBoundsException(beginIndex);
//        }
//        int subLen = value.length - beginIndex;
//        if (subLen < 0) {
//            throw new StringIndexOutOfBoundsException(subLen);
//        }
//        return (beginIndex == 0) ? this : new String(value, beginIndex, subLen);
//    }

    private static void readLineByLine(Scanner in) throws FileNotFoundException {
        while (in.hasNextLine()) {
            String scannerVariable = in.nextLine();
            String[] params = scannerVariable.split(" ");

            switch (params[0]) {
                case "PLAYER":
                    break;
                case "SIZE":
                    sizeReader(params);
                    break;
                case "BOARD":
                    while (in.hasNextLine()) {
                        in.nextLine();
                        boardLineReader(params);
                    }
                    break;
            }
        }
        in.close();
    }


    private static void sizeReader(String[] params) {
        boardSizeX = Integer.parseInt(params[1]);
        boardSizeY = Integer.parseInt(params[2]);
    }

    private static void boardLineReader(String[] params) {
        //TODO read in the tiles, then make tiles and put into boardTiles[][] in correct place.
    }

    public static void readDataFile(String filename) {

        try {
            File circles = new File(filename);
            Scanner in = new Scanner(circles);
            readLineByLine(in);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find " + filename);
            System.exit(0);
        }
    }

    public int[] getPlayerStartCoords() {
        //temp:
        playerStartCoords = new int[] {1, 0};
        return playerStartCoords;
    }
}
