package com.example.cs230;

import java.util.*;

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
    private static final int ADDED_TIME = 5;
    private final HBox topRow = new HBox();
    private final BorderPane gamePlayPane = new BorderPane();
    private final ArrayList<StackPane> allAssassinStacks = new ArrayList<>();
    private final ArrayList<FlyingAssassin> allAssassins = new ArrayList<>();
    private final ArrayList<NPC> allThieves = new ArrayList<>();
    private final ArrayList<Coin> allCoins = new ArrayList<>();
    private final ArrayList<Clock> allClock = new ArrayList<>();
    private final ArrayList<Lever> allLever = new ArrayList<>();
    private final ArrayList<Gate> allGates = new ArrayList<>();
    private final ArrayList<Bomb> allBomb = new ArrayList<>();
    private final ArrayList<Item> allCollectableItems = new ArrayList<>();
    private boolean isLose = false;
    private final boolean isTimerEnd = false;
    private VBox gamePane;
    private int currentLevel;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private AnimationTimer gameTimer;
    private Player currentPlayer;
    private StackPane currentPlayerStack;
    private Board currentBoard;
    private Board newBoard;
    private Door door;
    private Gate goldenGate;
    private Gate silverGate;
    private GameOverViewManager gameOver;
    private Ninja chosenNinja;
    private int level;
    private Time time;
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
    public void createNewGame(Stage stage, Ninja chosenNinja, int level) {
        this.level = level;
        this.chosenNinja = chosenNinja;
        this.menuStage = stage;
        this.menuStage.hide();
        gameOver = new GameOverViewManager();
        createBackground();
        createBoard();
        time = new Time(currentBoard.getSeconds());
        createStuff();
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

    private void createStuff() {
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

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (time.getCurrentTime() == 0 && !isLose) {
                    gameOver.createGameOver(gameStage, currentPlayer);
                    time.isKilled();
                    isLose = true;
                }
                for (FlyingAssassin allAssassin : allAssassins) {
                    if (allAssassin.collidedPlayer(currentPlayer.getPlayerCoords(),
                            currentPlayerStack, gamePlayPane, gameStage)) {
                        gamePlayPane.getChildren().clear();
                        time.isKilled();
                        gameOver.createGameOver(gameStage, currentPlayer);
                        allAssassin.setLose();
                    }
                }
                updateTopRow();
                ArrayList<Coin> coinsToRemove = new ArrayList<>();
                ArrayList<Clock> clockToRemove = new ArrayList<>();
                ArrayList<Lever> leverToRemove = new ArrayList<>();
                ArrayList<Gate> gateToRemove = new ArrayList<>();
                for (Coin allCoin : allCoins) {
                    if (allCoin.isCollisionPlayer(currentPlayer.getPlayerCoords())) {
                        gamePlayPane.getChildren().remove(allCoin.getCoinStackPane());
                        coinsToRemove.add(allCoin);
                        currentPlayer.setScore(currentPlayer.getScore() + allCoin.getCoinScore());
                    }
                    for (int i = 0; i < allThieves.size(); i++) {
                        if (allCoin.isCollisionNPC(allThieves.get(i).getCoords())) {
                            gamePlayPane.getChildren().remove(allCoin.getCoinStackPane());
                            coinsToRemove.add(allCoin);
                        }
                    }
                }
                for (Coin coin : coinsToRemove) {
                    allCoins.remove(coin);
                }
                coinsToRemove.clear();

                for (Clock allClocks : allClock) {

                    if (allClocks.isCollectedByPlayer(currentPlayer.getPlayerCoords())) {

                        time.currentTime += ADDED_TIME;
                        gamePlayPane.getChildren().remove(allClocks.getClockPane());
                        clockToRemove.add(allClocks);
                    }
                    for (int i = 0; i < allThieves.size(); i++) {
                        if (allClocks.isClockCollisionNPC(allThieves.get(i).getCoords())) {
                            time.currentTime -= ADDED_TIME;
                            gamePlayPane.getChildren().remove(allClocks.getClockPane());
                            clockToRemove.add(allClocks);
                        }
                    }
                }
                for (Clock clock : clockToRemove) {
                    allClock.remove(clock);
                }
                clockToRemove.clear();


                if (door.isCollectedByPlayer(currentPlayer.getPlayerCoords())) {
                    gamePlayPane.getChildren().remove(door.getDoorPane());
                    currentLevel += 1;
                    gamePlayPane.getChildren().clear();
                    time.isKilled();
                    WinScreenViewManager winScreen = new WinScreenViewManager();
                    winScreen.createGameOver(gameStage, currentPlayer, chosenNinja, level);
                }

                for (Gate goldenGate : allGates) {
                    if (goldenGate.getCanPass() &&
                            goldenGate.isCollisionPlayer(currentPlayer.getPlayerCoords())) {
                        currentPlayer.canMove = false;
                        System.out.println("Error");
                    }
                }

                for (Lever currentLever : allLever) {
                    for (int i=0; i<allGates.size(); i++) {
                        if (Objects.equals(currentLever.getLeverColour(), "GOLD") && (Objects.equals(allGates.get(i).getColour(), "GOLD"))
                                && currentLever.isCollectedByPlayer(currentPlayer.getPlayerCoords())) {
                            gamePlayPane.getChildren().remove(currentLever.getStackPane());
                            gamePlayPane.getChildren().remove(goldenGate.getGatePane());
                            leverToRemove.add(currentLever);
                            gateToRemove.add(goldenGate);

                        }
                        if (Objects.equals(currentLever.getLeverColour(), "SILVER") && (Objects.equals(allGates.get(i).getColour(), "SILVER"))
                                && currentLever.isCollectedByPlayer(currentPlayer.getPlayerCoords())) {
                            gamePlayPane.getChildren().remove(currentLever.getStackPane());
                            gamePlayPane.getChildren().remove(silverGate.getGatePane());
                            leverToRemove.add(currentLever);
                            gateToRemove.add(silverGate);
                        }
                    }
                }

                for (Gate silverGate : gateToRemove) {
                    allGates.remove(silverGate);
                }
                gateToRemove.clear();

                for (Lever silverLever : leverToRemove) {
                    allLever.remove(silverLever);
                }
                leverToRemove.clear();

                for (Bomb allBombs : allBomb) {
                    if (allBombs.isCollisionPlayer(currentPlayer.getPlayerCoords())) {
                        int[] currentBomb = allBombs.getCoords();
                        for (Item allitems : allCollectableItems) {
                            int[] currentItem = allitems.getCoords();
                            if (currentItem[0] == currentBomb[0] ||
                                    currentItem[1] == currentBomb[1]) {
                                System.out.println("test");
                            }
                        }

                    }

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
            allThieves.add(currentSmartThief);
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
            allThieves.add(currentThief);
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
        for (int i = 0; i < coinColor.size(); i += 1) {
            int[] currentCoinCoords = {coords.get(i * 2), coords.get((i * 2) + 1)};
            Coin currentCoin = new Coin(coinColor.get(i), currentBoard, currentCoinCoords);
            allCoins.add(currentCoin);
            allCollectableItems.add(currentCoin);
            gamePlayPane.getChildren().add(currentCoin.getCoinStackPane());
        }
    }

    private void createLever() {
        ArrayList<String> colours = currentBoard.getLeverColours();
        ArrayList<Integer> positionCoords = currentBoard.getLeverCoords();
        for (int i = 0; i < colours.size(); i += 1) {
            int[] currentLeverCoords = {positionCoords.get(i * 2), positionCoords.get(i * 2 + 1)};
            Lever currentLever = new Lever(currentBoard, currentLeverCoords, colours.get(i));
            allLever.add(currentLever);
            allCollectableItems.add(currentLever);
            gamePlayPane.getChildren().add(currentLever.getStackPane());
        }
    }


    private void createDoor() {
        int[] positionCoords = currentBoard.getDoorCoords();
        door = new Door(currentBoard, positionCoords);
        //allCollectableItems.add(door);
        gamePlayPane.getChildren().add(door.getDoorPane());
    }

    private void createClock() {
        ArrayList<Integer> positionCoords = currentBoard.getClockCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            Clock clock = new Clock(currentBoard, positionCoords2);
            allClock.add(clock);
            allCollectableItems.add(clock);
            gamePlayPane.getChildren().add(clock.getClockPane());
        }
    }

    private void createGoldenGate() {
        ArrayList<Integer> positionCoords = currentBoard.getGate1Coords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            goldenGate = new Gate("GOLD", currentBoard, positionCoords2);
            allGates.add(goldenGate);
            gamePlayPane.getChildren().add(goldenGate.getGatePane());
        }
    }

    private void createSilverGate() {
        ArrayList<Integer> positionCoords = currentBoard.getGate2Coords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            silverGate = new Gate("SILVER", currentBoard, positionCoords2);
            allGates.add(silverGate);

            gamePlayPane.getChildren().add(silverGate.getGatePane());
        }
    }

    private void createBomb() {
        ArrayList<Integer> positionCoords = currentBoard.getBombCoords();
        for (int i = 0; i < positionCoords.size(); i += 2) {
            int[] positionCoords2 = {positionCoords.get(i), positionCoords.get(i + 1)};
            Bomb bomb = new Bomb(currentBoard, positionCoords2);
            allBomb.add(bomb);

            gamePlayPane.getChildren().add(bomb.getBombPane());
        }
    }

    private void createBoard() {
        currentLevel = 0;
        currentBoard = new Board("Level0" +level+ ".txt", GAME_WIDTH);
        gamePlayPane.setLeft(currentBoard.getBoardPane());
    }

    /**
     * Creates top row of game window, which contains time left.
     */
    private void updateTopRow() {
        topRow.setPadding(new Insets(20));
        topRow.setSpacing(20);
        topRow.getChildren().clear();
        Text timeCounter = new Text("Time Left: " + time.getCurrentTime());
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
