package com.example.cs230;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private static int boardSizeX;
    private static int boardSizeY;


    private static void readLineByLine(Scanner in) {
        // Queue<ClosedShape> shapeQueue = new Queue<ClosedShape>();
		/*  reads the first value in each line in order to know what shape is being constructed
			then it enques that shape do shapeQueue
		 */
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
}
