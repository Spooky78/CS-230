package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * The main class which contains main method to run application.
 *
 * @author Vic
 */
public class Main extends Application {
    //private Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        final Stage mainStage = new Stage();
        ViewManager manager = new ViewManager();
        manager.createNewMenu(mainStage);
        //primaryStage = manager.getMainStage();
        //primaryStage.show();
    }

    /**
     * Entry to main method.
     *
     * @param args command-line arguments array.
     */
    public static void main(String[] args) {
        launch(args);
    }

}