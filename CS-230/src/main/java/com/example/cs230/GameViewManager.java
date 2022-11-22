package com.example.cs230;

import java.util.Random;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * Responsible for the main game window.
 * @author Spooky78
 */
public class GameViewManager {
    private static final int GAME_WIDTH = 1200;
    private static final int GAME_HEIGHT = 800;
    private BorderPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private final Random randomPositionGenerator;

    /**
     * Creates a GameViewManager.
     */
    public GameViewManager() {
        initializeStage();
        randomPositionGenerator = new Random();
    }

    /**
     * Creates a new game.
     * @param stage The previous stage (usually menuStage).
     * @param chosenNinja The player character.
     */
    public void createNewGame(Stage stage, Ninja chosenNinja) {
        this.menuStage = stage;
        this.menuStage.hide();
        createBackground();
        createBoard();
        gameStage.show();
    }

    /**
     * Initializes the stage, scene, & pane.
     */
    private void initializeStage() {
        gamePane = new BorderPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void createBoard(){
        Tile testTile = new Tile('A','B','C','D');
        Tile testTile2 = new Tile('A','B','A','B');
        TilePane boardPane = new TilePane(testTile.getTile());
        boardPane.getChildren().add(testTile2.getTile());
        gamePane.setCenter(boardPane);
    }

    /**
     * Creates the background.
     */
    private void createBackground() {
        Background background = new Background(new BackgroundFill(
            Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY));
        gamePane.setBackground(background);
    }

}
