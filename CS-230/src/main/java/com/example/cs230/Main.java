package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * The main class which contains main method to run application.
 * Test Comment
 * @author Spooky78
 */

//TODO: read in level file in Board class.
//TODO: draw board to screen using tile pane in Board class.
//TODO: crop sprites (make sure as close to image as possible).
//TODO: draw NPCs to screen (any where).
//TODO: Game window top of border pane make buttons (pause, save, ect) no function
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