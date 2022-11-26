package com.example.cs230;

import java.util.Random;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Responsible for the main game window.
 * @author Spooky78
 */
public class GameViewManager {
    private static final int GAME_WIDTH = 1200;
    private static final int GAME_HEIGHT = 800;
    private BorderPane gamePane;
    private VBox topBorderPane = new VBox();
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
        //createButtons();
        createBoard();
        createPlayer(chosenNinja);
        createAssassin();
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

    private void createPlayer(Ninja chosenNinja){
        Player currentPlayer = new Player(gameScene, chosenNinja);
        StackPane currentPlayerStack = currentPlayer.getPlayerStack();
        currentPlayerStack.setLayoutX(200);
        currentPlayerStack.setLayoutY(200);
        gamePane.getChildren().add(currentPlayerStack);
    }

    private void createAssassin(){
        FlyingAssassin currentAssassin = new FlyingAssassin();
        StackPane currentStackPane = new StackPane();
        currentStackPane.getChildren().add(currentAssassin.getAssassin());
        currentStackPane.setLayoutX(400);
        currentStackPane.setLayoutY(400);

        gamePane.getChildren().add(currentStackPane);
    }

    private void createBoard(){
        Tile testTile = new Tile('A','B','C','D');
        Tile testTile2 = new Tile('A','B','A','B');
        TilePane boardPane = new TilePane(testTile.getTile());
        boardPane.getChildren().add(testTile2.getTile());
        gamePane.setCenter(boardPane);
    }

    private void createButtons(){
        MainMenuButton pauseButton = new MainMenuButton("Pause");
        MainMenuButton saveButton = new MainMenuButton("Save");

        topBorderPane.getChildren().add(pauseButton);
        topBorderPane.getChildren().add(saveButton);
        topBorderPane.setPadding(new Insets(50, 50,0,50));
        topBorderPane.setSpacing(20);

        gamePane.setTop(topBorderPane);
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
