package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class which contains main method to run application.
 * @author Spooky78
 */

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