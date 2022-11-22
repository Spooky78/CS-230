package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class which contains main method to run application.
 * Test Comment
 * @author Spooky78
 */

//TODO: (during meeting) created all base classes. DONE!!
//TODO: (during meeting) Board class basic variables (including tile pane).
//TODO: (during meeting) draw player to screen & make basic move.

//TODO: frame rate cap for NPC animation???
//TODO: finish sprite design.
//TODO: player profile ask to select in menu

//TODO: rest of player movement.
//TODO: text for help and credits.
//TODO: in Tile class make sure that parameters can only be passed are A,B,C, OR D.
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