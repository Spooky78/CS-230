package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * The main class which contains main method to run application.
 *
 * @author Vic
 */

//TODO: Player:                                     DONE
    //TODO: Movement                                DONE

//TODO: Assassin:
    //TODO: Movement                    Vic         DONE
    //TODO: Collision player            Vic         DONE
    //TODO: Collision NPCs              Vic

//TODO: Smart Thief:
//TODO: Draw                            Rex         DONE
//TODO: Movement

//TODO: Floor Following Thief:
//TODO: Draw                            Dimitrios   DONE
//TODO: Movement

//TODO: Coins:
    //TODO: Draw                        Omar        DONE
    //TODO: Collision                   Meeting     HALF
    //TODO: Collision

//TODO: Lever:
    //TODO: Draw                        Chrysis     DONE
    //TODO: Collision player/NPC

//TODO: Gate:
    //TODO: Draw                        Lewis       DONE

//TODO: Door:
    //TODO: Draw                        Vic         DONE
    //TODO: Collision

//TODO: Clock:
    //TODO: Draw                        Lewis       DONE
    //TODO: Collision/Add Time

//TODO: Bomb:
    //TODO: Draw                        Arran
    //TODO: Collision

//TODO: Time
    //TODO: Draw time to screen

//TODO: Player Profiles:
    //TODO: Write names to txt file
    //TODO: Readin names from txt file
    //TODO: Delete names from txt file

//TODO: End Screen

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();
        primaryStage.show();
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