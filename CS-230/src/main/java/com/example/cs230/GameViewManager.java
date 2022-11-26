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
    private int playerCurrentX;
    private int playerCurrentY;
    private Board currentBoard;

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
        currentPlayer = new Player(gameScene, chosenNinja, currentBoard);
        StackPane currentPlayerStack = currentPlayer.getPlayerStack();

        int boardColumns = currentBoard.getBoardWidth();
        int tileSize = GAME_WIDTH/ boardColumns;
        currentPlayer.setMovementOffset(tileSize);

        int[] playerStart = currentBoard.getStartCharacterPosition();
        int playerStartX = playerStart[0];
        int playerStartY = playerStart[1];
        currentPlayerStack.setLayoutX(playerStartX * tileSize + (tileSize / 2));
        currentPlayerStack.setLayoutY(playerStartY * tileSize + (tileSize / 2));

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
        currentBoard = new Board(0);

        gamePane.setCenter(currentBoard.getBoardPane());
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

    public Board getCurrentBoard(){
        return currentBoard;
    }
}
