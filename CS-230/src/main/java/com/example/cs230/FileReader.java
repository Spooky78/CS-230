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


    private static void readLineByLine(Scanner in) {
        while(in.hasNextLine()) {
            String scannerVariable = in.nextLine();
            String[] params = scannerVariable.split(" ");

            switch (params[0]) {
                case "PLAYER":
                    break;
                case "SIZE":
                    sizeReader(params);
                    break;
                case "BOARD":
                    while(in.hasNextLine()){
                        in.nextLine();
                        boardLineReader(params);
                    }
                    break;
            }
        }
        in.close();
    }


    private static void sizeReader(String[] params){
        boardSizeX = Integer.parseInt(params[1]);
        boardSizeY = Integer.parseInt(params[2]);
    }

    private static void boardLineReader(String[] params){
        //TODO read in the tiles, then make tiles and put into boardTiles[][] in correct place.
    }

    public static void readDataFile(String filename) {

        try {
            File circles = new File(filename);
            Scanner in = new Scanner(circles);
            readLineByLine(in);
        }

        catch (FileNotFoundException e){
            System.out.println("Could not find " + filename);
            System.exit(0);
        }
    }

    public int[] getPlayerStartCoords(){
        //temp:
        playerStartCoords = new int[] {1, 0};
        return playerStartCoords;
    }
}
