package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * The main class which contains main method to run application.
 * @author Vic
 */


//LAST WEEK TASKS

//TODO: crop sprites (make sure as close to image as possible).


//TODO: (for items & NPCs get it at least to draw on screen in correct position)
//TODO: coin item (coin values 1,3,5,10)?? Omar
//TODO: lever item chrysis
//TODO: gate item lewis
//TODO: clock item lewis
//TODO: bomb item Arran
//TODO: smart thief Rex
//TODO: floor following thief Vic
//TODO: draw time to screen
//TODO: finish player movement (jump across tiles)

//If spare time
//TODO: Resume and Start New Game
//TODO: Text font, Text boxes will resize when the game window is resized
    //TODO: score
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