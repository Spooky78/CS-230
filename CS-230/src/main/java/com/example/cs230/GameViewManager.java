package com.example.cs230;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

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
 * @author Everyone
 */
public class GameViewManager {
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    private static final int SECOND = 1000;
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
    private ArrayList<Coin> allCoins = new ArrayList<>();
    private ArrayList<Clock> allClock = new ArrayList<>();
    private int timeLeft;
    private boolean isLose = false;
    private GameOverViewManager gameOver;

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
        gameOver = new GameOverViewManager();
        createBackground();
        createBoard();
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
        startTimer();
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
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (timeLeft == 0 && !isLose) {
                    gameOver.createGameOver(gameStage, currentPlayer);
                    isLose = true;
                }
                for (FlyingAssassin allAssassin : allAssassins) {
                    allAssassin.collidedPlayer(currentPlayer.getPlayerCoords(), currentPlayerStack, gamePlayPane, gameStage, currentPlayer);
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
            SmartThief currentSmartThief = new SmartThief(currentBoard, currentCoords, currentStackPane);
            currentStackPane.getChildren().add(currentSmartThief.getSmartThief());
            gamePlayPane.getChildren().add(currentStackPane);
        }
    }

    private void createFloorFollowingThief() {
        ArrayList<String> colours = currentBoard.getFloorFollowingThiefColours();
        ArrayList<Integer> coords = currentBoard.getFloorFollowingThiefStartCoords();
        for (int i = 0; i <colours.size(); i++){
            int[] currentCoords = {coords.get(i * 2), coords.get(i * 2 +1 )};
            StackPane ffThiefStack = new StackPane();
            FloorFollowingThief currentThief = new FloorFollowingThief(currentBoard, currentCoords, ffThiefStack, i);
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
            FlyingAssassin currentAssassin = new FlyingAssassin(currentBoard, currentCoords, currentStackPane, gameOver, i);
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
        StackPane doorPane = new StackPane();
        int[] positionCoords = currentBoard.getDoorCoords();
        Door door = new Door(currentBoard, positionCoords);
        gamePlayPane.getChildren().add(door.getDoorPane());
    }

    private void createClock() {
        ArrayList<Integer> positionCoords = currentBoard.getClockCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 ={positionCoords.get(i), positionCoords.get(i + 1)};
            Clock clock = new Clock(currentBoard,positionCoords2);
            allClock.add(clock);
            gamePlayPane.getChildren().add(clock.getClockPane());
        }
    }

    private void createGoldenGate() {
        ArrayList<Integer> positionCoords = currentBoard.getGate1Coords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 ={positionCoords.get(i), positionCoords.get(i + 1)};
            Gate gate = new Gate("GOLD",currentBoard,positionCoords2);
            gamePlayPane.getChildren().add(gate.getGatePane());
        }
    }

    private void createSilverGate() {
        ArrayList<Integer> positionCoords = currentBoard.getGate2Coords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 ={positionCoords.get(i), positionCoords.get(i + 1)};
            Gate gate = new Gate("SILVER",currentBoard,positionCoords2);
            gamePlayPane.getChildren().add(gate.getGatePane());
        }
    }

    private void createBomb() {
        ArrayList<Integer> positionCoords = currentBoard.getBombCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
           int[] positionCoords2 ={positionCoords.get(i), positionCoords.get(i + 1)};
            Bomb bomb = new Bomb(currentBoard,positionCoords2);
            gamePlayPane.getChildren().add(bomb.getBombPane());
        }
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
        Text timeCounter = new Text("Time Left: " + timeLeft);
        timeCounter.setFont(Font.font("Arial", 20));
        topRow.getChildren().add(timeCounter);

        Text playerScore = new Text("SCORE: " + currentPlayer.getScore());
        playerScore.setFont(Font.font("Arial", 20));
        topRow.getChildren().add(playerScore);


    }

    private void startTimer() {
        timeLeft = currentBoard.getSeconds();
         int timeForGame = timeLeft;
        Timer timer = new Timer();
        for (int i = 0; i < timeForGame; i++) {
            TimerTask countDown1Sec = new TimerTask() {
                @Override
                public void run() {
                    timeLeft -=1;
                }
            };
            timer.schedule(countDown1Sec, (long) SECOND * i);
        }
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
