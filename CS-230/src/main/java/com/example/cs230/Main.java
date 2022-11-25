package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class which contains main method to run application.
 * Test Comment
 * @author Spooky78
 */

//TODO: finish sprite design.
//TODO: player profile ask to select in menu

//TODO: rest of player movement.
//TODO: text for help and credits.
//TODO: read in level file in Board class.
//TODO: draw board to screen using tile pane in Board class.
//TODO: crop sprites (make sure as close to image as possible).
    //TODO: draw NPCs to screen (any where).
//TODO: Game window top of border pane make buttons (pause, save, ect) no function
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
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