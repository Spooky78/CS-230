package com.example.cs230;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Responsible for the main game window.
 * @author Spooky78
 */
public class GameViewManager {
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private BorderPane gamePane;
    private HBox topBorderPane = new HBox();

    private StackPane gamePlayPane = new StackPane();
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private Player currentPlayer;
    private Board testBoard;

    /**
     * Creates a GameViewManager.
     */
    public GameViewManager() {
        initializeStage();
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
        createAssassin();
        createPlayer(chosenNinja);
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
        currentPlayer = new Player(gameScene, chosenNinja);
        StackPane currentPlayerStack = currentPlayer.getPlayerStack();
        currentPlayerStack.setLayoutX(100);
        currentPlayerStack.setLayoutY(100);

        int boardColumns = testBoard.getBoardWidth();
        int tileSize = GAME_WIDTH/ boardColumns;
        currentPlayer.setMovementOffset(tileSize);
        //gamePlayPane.getChildren().add(currentPlayerStack);
        gamePane.getChildren().add(currentPlayerStack);
    }

    private void createAssassin(){
        FlyingAssassin currentAssassin = new FlyingAssassin();
        StackPane currentStackPane = new StackPane();
        currentStackPane.getChildren().add(currentAssassin.getAssassin());
        currentStackPane.setLayoutX(400);
        currentStackPane.setLayoutY(200);

        gamePane.getChildren().add(currentStackPane);
    }

    private void createBoard(){
        testBoard = new Board(0);

        gamePane.setCenter(testBoard.getBoardPane());
        //gamePlayPane.getChildren().add(testBoard.getBoardPane());
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
