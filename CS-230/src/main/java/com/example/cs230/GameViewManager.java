package com.example.cs230;

import static javafx.geometry.Pos.TOP_LEFT;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Responsible for the main game window.
 * @author Spooky78
 */
public class GameViewManager {
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    private VBox gamePane;
    private HBox topRow = new HBox();

    private BorderPane gamePlayPane = new BorderPane();
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private Player currentPlayer;
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
        createTopRow();
        createBoard();
        createDoor();
        createBronzeCoins();
        createSilverCoins();
        createAssassin();
        createPlayer(chosenNinja);

        gamePane.getChildren().add(gamePlayPane);


        gameStage.show();
    }

    /**
     * Initializes the stage, scene, & pane.
     */
    private void initializeStage() {
        gamePane = new VBox();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void createPlayer(Ninja chosenNinja){
        currentPlayer = new Player(gameScene, chosenNinja, currentBoard);
        StackPane currentPlayerStack = currentPlayer.getPlayerStack();

        int tileSize = currentBoard.getTileSize();
        currentPlayer.setMovementOffset(tileSize);

        int[] playerStart = currentBoard.getPlayerStartCoords();
        int playerStartX = playerStart[0];
        int playerStartY = playerStart[1];
        currentPlayerStack.setLayoutX(playerStartX * tileSize + (tileSize / 2.0));
        currentPlayerStack.setLayoutY(playerStartY * tileSize + (tileSize / 2.0));

        gamePlayPane.getChildren().add(currentPlayerStack);
    }

    private void createAssassin(){
        ArrayList<Integer> coords = currentBoard.getAssassinStartCoords();
        //Each iteration of loop creates new assassin.
        for (int i=0; i<coords.size(); i+=2) {
            FlyingAssassin currentAssassin = new FlyingAssassin();
            StackPane currentStackPane = new StackPane();
            currentStackPane.getChildren().add(currentAssassin.getAssassin());

            int coordX = coords.get(i);
            int coordY = coords.get(i+1);
            int tileSize = currentBoard.getTileSize();
            currentStackPane.setLayoutX((coordX*tileSize) - (tileSize/2));
            currentStackPane.setLayoutY((coordY*tileSize) - (tileSize/2));

            gamePlayPane.getChildren().add(currentStackPane);
        }
    }

    private void createBronzeCoins(){
        ArrayList<Integer> coords = currentBoard.getCoin1Coords();
        //Each iteration of loop creates new bronze coin.
        for (int i=0; i<coords.size(); i+=2) {
            Coin currentBronzeCoin = new Coin("BRONZE");
            StackPane currentStackPane = new StackPane();
            currentStackPane.getChildren().add(currentBronzeCoin.getBronzeCoin());

            int coordX = coords.get(i);
            int coordY = coords.get(i+1);
            int tileSize = currentBoard.getTileSize();
            currentStackPane.setLayoutX((coordX*tileSize) - (tileSize/2));
            currentStackPane.setLayoutY((coordY*tileSize) - (tileSize/2));

            gamePlayPane.getChildren().add(currentStackPane);
        }
    }

    private void createSilverCoins(){
        ArrayList<Integer> coords = currentBoard.getCoin2Coords();
        //Each iteration of loop creates new silver coin.
        for (int i=0; i<coords.size(); i+=2) {
            Coin currentSilverCoin = new Coin("SILVER");
            StackPane currentStackPane = new StackPane();
            currentStackPane.getChildren().add(currentSilverCoin.getSilverCoin());

            int coordX = coords.get(i);
            int coordY = coords.get(i+1);
            int tileSize = currentBoard.getTileSize();
            currentStackPane.setLayoutX((coordX*tileSize) - (tileSize/2));
            currentStackPane.setLayoutY((coordY*tileSize) - (tileSize/2));

            gamePlayPane.getChildren().add(currentStackPane);
        }
    }

    private void createDoor(){
        Door door = new Door();
        StackPane doorPane = new StackPane();
        doorPane.getChildren().add(door.getDoor());
        int[] positionCoords = currentBoard.getDoorCoords();
        int tileSize = currentBoard.getTileSize();
        doorPane.setLayoutX((positionCoords[0]*tileSize) - (tileSize/2));
        doorPane.setLayoutY((positionCoords[1]*tileSize) - (tileSize/2));
        gamePlayPane.getChildren().add(doorPane);
    }

    private void createBoard(){
        currentBoard = new Board(0, GAME_WIDTH);
        gamePlayPane.setLeft(currentBoard.getBoardPane());
    }

    /**
     * Creates top row of game window, which contains time left.
     */
    private void createTopRow(){
        topRow.setPadding(new Insets(20));
        topRow.setSpacing(20);

        Text timeCounter = new Text("Time Left:");
        timeCounter.setFont(Font.font("Arial", 20));
        topRow.getChildren().add(timeCounter);
        topRow.setAlignment(Pos.CENTER_RIGHT);
        gamePane.getChildren().add(topRow);
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
