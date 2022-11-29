package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * The main class which contains main method to run application.
 * @author Vic
 */


//LAST WEEK TASKS
//TODO: read in level file in Board class.
//TODO: draw board to screen using tile pane in Board class.
//TODO: crop sprites (make sure as close to image as possible).
//TODO: draw NPCs to screen (any where).
//TODO: Game window top of border pane make buttons (pause, save, ect) no function

//TASKS FOR THIS WEEK
//TODO: maybe combine Board and FileReader Classes, as redundent to get coords fron file reader, pass to board and then pass to where it is relevant

//TODO: (for items & NPCs get it at least to draw on screen in correct position)
//TODO: coin item (coin values 1,3,5,10)??
//TODO: lever item
//TODO: gate item
//TODO: clock item
//TODO: smart thief
//TODO: floor following thief
//TODO: draw time to screen
//TODO: finish player movement (jump across tiles)

//If spare time
//TODO: Resume and Start New Game
//TODO: Text font, Text boxes will resize when the game window is resized
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();
        primaryStage.show();
    }

    /**
     * Entry to main method.
     * @param args command-line arguments array.
     */
    public static void main(String[] args) {
        launch(args);
    }
}