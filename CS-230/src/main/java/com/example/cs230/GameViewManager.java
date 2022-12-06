package com.example.cs230;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Responsible for the main game window.
 *
 * @author Everyone
 */
public class GameViewManager {
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    private static final int SECOND = 1000;
    private VBox gamePane;
    private final HBox topRow = new HBox();

    private int currentLevel;

    private BorderPane gamePlayPane = new BorderPane();
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private AnimationTimer gameTimer;
    private Player currentPlayer;
    private StackPane currentPlayerStack;
    private Board currentBoard;
    private Board newBoard;
    private Door door;
    private final ArrayList<StackPane> allAssassinStacks = new ArrayList<>();
    private final ArrayList<FlyingAssassin> allAssassins = new ArrayList<>();
    private final ArrayList<NPC> allKillable = new ArrayList<>();
    private final ArrayList<Coin> allCoins = new ArrayList<>();
    private final ArrayList<Clock> allClock = new ArrayList<>();
    private int timeLeft;
    private final boolean isLose = false;
    private GameOverViewManager gameOver;
    private Timer timer;
    private final boolean isTimerEnd = false;
    private Ninja chosenNinja;

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
        this.chosenNinja = chosenNinja;
        this.menuStage = stage;
        this.menuStage.hide();
        gameOver = new GameOverViewManager();
        createBackground();
        createBoard();
        createStuff();
//        createDoor();
//        createClock();
//        createGoldenGate();
//        createSilverGate();
//        createLever();
//        createCoins();
//        createBomb();
//        createAssassin();
//        createFloorFollowingThief();
//        createPlayer(chosenNinja);
//        createSmartThief();
//
//        updateTopRow();
//        createGameLoop();
        topRow.setAlignment(Pos.CENTER_RIGHT);
        gamePane.getChildren().add(topRow);
        gamePane.getChildren().add(gamePlayPane);
        gameStage.show();
    }

    private void createStuff(){
        createDoor();
        createClock();
        createGoldenGate();
        createSilverGate();
        createLever();
        createCoins();
        createBomb();
        createAssassin();
        createFloorFollowingThief();
        createPlayer(chosenNinja);
        createSmartThief();

        updateTopRow();
        createGameLoop();
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

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (FlyingAssassin allAssassin : allAssassins) {
                    if (allAssassin.collidedPlayer(currentPlayer.getPlayerCoords(),
                            currentPlayerStack, gamePlayPane, gameStage)) {
                        gameOver.createGameOver(gameStage, currentPlayer);
                        allAssassin.setLose();
                    }
//                    for(int i=0; i<allKillable.size(); i++) {
//                        if (allAssassin.collidedPlayer(allKillable.get(i).getCoords(), allKillable.get(i).getStackPane(), gamePlayPane, gameStage)) {
//
//                        }
//                    }
                }
                updateTopRow();
                ArrayList<Coin> coinsToRemove = new ArrayList<>();
                ArrayList<Clock> clockToRemove = new ArrayList<>();
                for (Coin allCoin : allCoins) {
                    if (allCoin.isCollisionPlayer(currentPlayer.getPlayerCoords())) {
                        gamePlayPane.getChildren().remove(allCoin.getCoinStackPane());
                        coinsToRemove.add(allCoin);
                        currentPlayer.setScore(currentPlayer.getScore() + allCoin.getCoinScore());
                    }
                }
                for (Coin coin : coinsToRemove) {
                    allCoins.remove(coin);
                }
                coinsToRemove.clear();

                for (Clock allClocks : allClock) {
                    if (allClocks.isCollectedByPlayer(currentPlayer.getPlayerCoords())) {
                        gamePlayPane.getChildren().remove(allClocks.getClockPane());
                        clockToRemove.add(allClocks);
                        timeLeft += 5;
                    }
                }
                for (Clock clock : clockToRemove) {
                    allClock.remove(clock);
                }
                clockToRemove.clear();


                if (door.isCollectedByPlayer(currentPlayer.getPlayerCoords())) {
                    gamePlayPane.getChildren().remove(door.getDoorPane());
                    currentLevel += 1;
                    System.out.println(currentLevel);
                    nextLevel();
                }


            }
        };
        gameTimer.start();
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
            SmartThief currentSmartThief =
                    new SmartThief(currentBoard, currentCoords, currentStackPane);
            currentStackPane.getChildren().add(currentSmartThief.getSmartThief());
            allKillable.add(currentSmartThief);
            gamePlayPane.getChildren().add(currentStackPane);
        }
    }

    private void createFloorFollowingThief() {
        ArrayList<String> colours = currentBoard.getFloorFollowingThiefColours();
        ArrayList<Integer> coords = currentBoard.getFloorFollowingThiefStartCoords();
        for (int i = 0; i < colours.size(); i++) {
            int[] currentCoords = {coords.get(i * 2), coords.get(i * 2 + 1)};
            StackPane ffThiefStack = new StackPane();
            FloorFollowingThief currentThief =
                    new FloorFollowingThief(currentBoard, currentCoords, ffThiefStack, i);
            ffThiefStack.getChildren().add(currentThief.getffThief());
            gamePlayPane.getChildren().add(ffThiefStack);
        }
    }

    private void createAssassin() {
        ArrayList<String> direction = currentBoard.getAssassinStartDirection();
        ArrayList<Integer> coords = currentBoard.getAssassinStartCoords();
        //Each iteration of loop creates new assassin.
        for (int i = 0; i < direction.size(); i += 1) {
            int[] currentCoords = {coords.get(i * 2), coords.get(i * 2 + 1)};
            StackPane currentStackPane = new StackPane();
            FlyingAssassin currentAssassin =
                    new FlyingAssassin(currentBoard, currentCoords, currentStackPane, this, i);
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
        System.out.println("SHIT");
        for (int i = 0; i < coinColor.size(); i += 1) {
            int[] currentCoinCoords = {coords.get(i * 2), coords.get((i * 2) + 1)};
            Coin currentCoin = new Coin(coinColor.get(i), currentBoard, currentCoinCoords);
            allCoins.add(currentCoin);
            gamePlayPane.getChildren().add(currentCoin.getCoinStackPane());
        }
    }

    private void createLever() {
        ArrayList<String> colours = currentBoard.getLeverColours();
        ArrayList<Integer> positionCoords = currentBoard.getLeverCoords();
        for (int i = 0; i < colours.size(); i += 1) {
            int[] currentLeverCoords = {positionCoords.get(i * 2), positionCoords.get(i * 2 + 1)};
            Lever lever = new Lever(currentBoard, currentLeverCoords, colours.get(i));
            gamePlayPane.getChildren().add(lever.getLeverPane());
        }
    }


    private void createDoor() {
        int[] positionCoords = currentBoard.getDoorCoords();
        door = new Door(currentBoard, positionCoords);
        gamePlayPane.getChildren().add(door.getDoorPane());
    }

    private void createClock() {
        ArrayList<Integer> positionCoords = currentBoard.getClockCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            Clock clock = new Clock(currentBoard, positionCoords2);
            allClock.add(clock);
            gamePlayPane.getChildren().add(clock.getClockPane());
        }
    }

    private void createGoldenGate() {
        ArrayList<Integer> positionCoords = currentBoard.getGate1Coords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            Gate gate = new Gate("GOLD", currentBoard, positionCoords2);
            gamePlayPane.getChildren().add(gate.getGatePane());
        }
    }

    private void createSilverGate() {
        ArrayList<Integer> positionCoords = currentBoard.getGate2Coords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            Gate gate = new Gate("SILVER", currentBoard, positionCoords2);
            gamePlayPane.getChildren().add(gate.getGatePane());
        }
    }

    private void createBomb() {
        ArrayList<Integer> positionCoords = currentBoard.getBombCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            Bomb bomb = new Bomb(currentBoard, positionCoords2);

            gamePlayPane.getChildren().add(bomb.getBombPane());
        }
    }

    private void createBoard() {
        currentLevel = 0;
        currentBoard = new Board("Level00.txt", GAME_WIDTH);
        gamePlayPane.setLeft(currentBoard.getBoardPane());
    }

    private void nextLevel() {
        for(int i=0; i<allAssassins.size(); i++) {
            allAssassins.get(i).deleteAssassin();
        }
        String inputLevel = "Level0" + currentLevel + ".txt";
        gamePlayPane.getChildren().clear();
        //gamePlayPane.getChildren().remove(currentBoard);
        gameStage.hide();
        currentBoard = new Board(inputLevel, GAME_WIDTH);
        gamePlayPane = new BorderPane();
        //createStuff();
//        Coin testCoin = new Coin("GOLD", currentBoard, new int[]{1,1});
//        gamePlayPane.getChildren().add(testCoin.getCoinStackPane());
        gamePlayPane.setLeft(currentBoard.getBoardPane());
        createStuff();
        gamePane.getChildren().add(gamePlayPane);
        gameStage.show();
    }

    /**
     * Creates top row of game window, which contains time left.
     */
    private void updateTopRow() {
        topRow.setPadding(new Insets(20));
        topRow.setSpacing(20);
        topRow.getChildren().clear();
        Text timeCounter = new Text("Time Left: " + timeLeft);
        timeCounter.setFont(Font.font("Arial", 20));
        topRow.getChildren().add(timeCounter);

        Text playerScore = new Text("SCORE: " + currentPlayer.getScore());
        playerScore.setFont(Font.font("Arial", 20));
        topRow.getChildren().add(playerScore);


    }

    /**
     * Creates the background.
     */
    private void createBackground() {
        Background background = new Background(new BackgroundFill(
                Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY));
        gamePane.setBackground(background);
    }

    public boolean isLose() {
        return isLose;
    }

}
