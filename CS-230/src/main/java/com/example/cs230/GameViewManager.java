package com.example.cs230;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
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
import javafx.stage.Stage;

/**
 * Responsible for the main game window.
 *
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
    private AnimationTimer gameTimer;
    private Player currentPlayer;
    private StackPane currentPlayerStack;
    private Board currentBoard;
    private ArrayList<StackPane> allAssassinStacks = new ArrayList<>();
    private ArrayList<FlyingAssassin> allAssassins = new ArrayList<>();
    private int playerScore = 0;

    /**
     * Creates a GameViewManager.
     */
    public GameViewManager() {
        initializeStage();
    }

    /**
     * Creates a new game.
     *
     * @param stage       The previous stage (usually menuStage).
     * @param chosenNinja The player character.
     */
    public void createNewGame(Stage stage, Ninja chosenNinja) {
        this.menuStage = stage;
        this.menuStage.hide();
        createBackground();
        updateTopRow();
        createBoard();
        createDoor();
        createClock();
        createGoldenGate();
        createSilverGate();
        createLever();
        createCoins();
        //createAssassin();
        createPlayer(chosenNinja);
        createSmartThief();

        createGameLoop();
        topRow.setAlignment(Pos.CENTER_RIGHT);
        gamePane.getChildren().add(topRow);
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

    private void createGameLoop(){
        System.out.print("FUCK");
        //Interactions interactions = new Interactions();
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //assassinKill();
                updateTopRow();
            }
        };
        gameTimer.start();
    }

    private void assassinKill(){
//        Interactions interactions = new Interactions();
//        for(int i=0; i<allAssassins.size();i++){
//            //System.out.println("FUCKK");
//            if (interactions.isCollide(allAssassins.get(i).getAssassin(),
//                allAssassins.get(i).getRadius(), currentPlayerStack,
//                currentPlayer.getRadius())){
//                System.out.println("DIE");
//            }
//            int[] playerC =currentPlayer.getPlayerCoords();
//            //System.out.println(playerC[0] + " "+playerC[1]);
//            int[] aC = allAssassins.get(i).getCoords();
//            System.out.println(playerC[0] + " "+playerC[1]+" "+aC[0] + " " + aC[1]);
//            if (allAssassins.get(i).getCoords() == currentPlayer.getPlayerCoords()){
//                System.out.println("DIE");
//            }
//        }
    }

    private void createPlayer(Ninja chosenNinja) {
        currentPlayer = new Player(gameScene, chosenNinja, currentBoard);
        currentPlayerStack = currentPlayer.getPlayerStack();

        int tileSize = currentBoard.getTileSize();
        currentPlayer.setMovementOffset(tileSize);

        int[] playerStart = currentBoard.getPlayerStartCoords();
        int playerStartX = playerStart[0];
        int playerStartY = playerStart[1];
        currentPlayerStack.setLayoutX(playerStartX * tileSize + (tileSize / 2.0));
        currentPlayerStack.setLayoutY(playerStartY * tileSize + (tileSize / 2.0));

        gamePlayPane.getChildren().add(currentPlayerStack);
    }

    private void createSmartThief() {
        ArrayList<Integer> coords = currentBoard.getSmartThiefStartCoords();

        for (int i = 0; i < coords.size(); i += 2) {
            int[] currentCoords = new int[2];
            currentCoords[0] = coords.get(i);
            currentCoords[1] = coords.get(i + 1);
            StackPane currentStackPane = new StackPane();
            SmartThief currentSmartThief = new SmartThief(currentBoard, currentCoords, currentStackPane);
            currentStackPane.getChildren().add(currentSmartThief.getSmartThief());
            gamePlayPane.getChildren().add(currentStackPane);
        }
    }

    private void createAssassin() {
        ArrayList<Integer> coords = currentBoard.getAssassinStartCoords();
        //Each iteration of loop creates new assassin.
        for (int i = 0; i < coords.size(); i += 2) {
            int[] currentCoords = new int[2];
            currentCoords[0] = coords.get(i);
            currentCoords[1] = coords.get(i + 1);
            StackPane currentStackPane = new StackPane();
            FlyingAssassin currentAssassin = new FlyingAssassin(currentBoard, currentCoords, currentStackPane);
            allAssassinStacks.add(currentStackPane);
            allAssassins.add(currentAssassin);
            currentStackPane.getChildren().add(currentAssassin.getAssassin());
            gamePlayPane.getChildren().add(currentStackPane);
        }
    }

    private void createCoins() {
        ArrayList<String> coinColor = currentBoard.getCoinColor();
        ArrayList<Integer> coords = currentBoard.getCoinCoords();
        //Each iteration of loop creates new bronze coin.
        for (int i = 0; i < coinColor.size(); i += 1) {
            int[] currentCoinCoords = {coords.get(i * 2), coords.get((i * 2) + 1)};
            /*

             */
            Coin currentCoin = new Coin(coinColor.get(i), currentBoard, currentCoinCoords);
            gamePlayPane.getChildren().add(currentCoin.getCoinStackPane());
            System.out.println(coinColor.get(i) + "FUCK" + currentCoinCoords[0] + "FUCK" + currentCoinCoords[1]);
        }
    }

    private void createLever() {
        ArrayList<Integer> positionCoords = currentBoard.getLeverCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] currentLeverCoords = {positionCoords.get(i), positionCoords.get(i+1)};
            Lever lever = new Lever(currentBoard, currentLeverCoords);
            gamePlayPane.getChildren().add(lever.getLeverPane());
        }
    }

    private void createDoor() {
        Door door = new Door();
        StackPane doorPane = new StackPane();
        doorPane.getChildren().add(door.getDoor());
        int[] positionCoords = currentBoard.getDoorCoords();
        int tileSize = currentBoard.getTileSize();
        doorPane.setLayoutX((positionCoords[0] * tileSize) - (tileSize / 2));
        doorPane.setLayoutY((positionCoords[1] * tileSize) - (tileSize / 2));
        gamePlayPane.getChildren().add(doorPane);
    }

    private void createClock() {
        ArrayList<Integer> positionCoords = currentBoard.getClockCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            Clock clock = new Clock();
            StackPane clockPane = new StackPane();
            clockPane.getChildren().add(clock.getClock());
            int tileSize = currentBoard.getTileSize();
            clockPane.setLayoutX((positionCoords.get(i) * tileSize) - (tileSize / 2.0));
            clockPane.setLayoutY((positionCoords.get(i + 1) * tileSize) - (tileSize / 2.0));
            gamePlayPane.getChildren().add(clockPane);
        }
    }

    private void createGoldenGate() {
        Gate gate = new Gate("GOLDEN");
        StackPane gatePane = new StackPane();
        gatePane.getChildren().add(gate.getGoldenGate());
        int[] positionCoords = currentBoard.getGate1Coords();
        int tileSize = currentBoard.getTileSize();
        gatePane.setLayoutX((positionCoords[0] * tileSize) - (tileSize / 2.0));
        gatePane.setLayoutY((positionCoords[1] * tileSize) - (tileSize / 2.0));
        gamePlayPane.getChildren().add(gatePane);
    }

    private void createSilverGate() {
        Gate gate = new Gate("SILVER");
        StackPane gatePane = new StackPane();
        gatePane.getChildren().add(gate.getSilverGate());
        int[] positionCoords = currentBoard.getGate2Coords();
        int tileSize = currentBoard.getTileSize();
        gatePane.setLayoutX((positionCoords[0] * tileSize) - (tileSize / 2.0));
        gatePane.setLayoutY((positionCoords[1] * tileSize) - (tileSize / 2.0));
        gamePlayPane.getChildren().add(gatePane);
    }

    private void createBoard() {
        currentBoard = new Board(0, GAME_WIDTH);
        gamePlayPane.setLeft(currentBoard.getBoardPane());
    }

    /**
     * Creates top row of game window, which contains time left.
     */
    private void updateTopRow() {
        topRow.setPadding(new Insets(20));
        topRow.setSpacing(20);
        topRow.getChildren().clear();
        Text timeCounter = new Text("Time Left: ");
        timeCounter.setFont(Font.font("Arial", 20));
        topRow.getChildren().add(timeCounter);

        Text playerScore = new Text("SCORE: " + updateScorePlayer());
        playerScore.setFont(Font.font("Arial", 20));
        topRow.getChildren().add(playerScore);


    }

    private int updateScorePlayer(){
        //
        return playerScore;
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
