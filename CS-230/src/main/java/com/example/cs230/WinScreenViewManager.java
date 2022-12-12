package com.example.cs230;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class WinScreenViewManager {
    private static final int MAX_LEVEL = 4;
    private static final int TIME_FACTOR = 3;
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 350;
    private static final int FONT_SIZE = 50;
    private final String FONT_PATH;

    {
        try {
            FONT_PATH = String.valueOf(new File(ClassLoader.getSystemResource(
                    "kenvector_future.ttf").toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private VBox gameOverPane;
    private Scene gameOverScene;
    private Stage gameOverStage;
    private Stage menuOverStage;
    private Ninja ninja;
    private String newLevel;
    private String profileName;
    private int score;
    private int timeLeft;

    /**
     * Creates a GameOverViewManager.
     */
    public WinScreenViewManager() {
        initializeStage();
    }

    /**
     * Initializes the stage, scene, and pane.
     */
    private void initializeStage() {
        gameOverPane = new VBox();
        gameOverScene = new Scene(gameOverPane, GAME_WIDTH, GAME_HEIGHT);
        gameOverStage = new Stage();
        gameOverStage.setScene(gameOverScene);
    }

    /**
     * Creates a new game.
     *
     * @param stage        The previous stage (usually menuStage).
     * @param player       player
     * @param chosenNinja  ninja already chosen
     * @param name         profile name
     * @param score        scoreboard
     * @param currentLevel level already reached
     * @param timeRemain   time remains
     */
    public void createGameOver(Stage stage, Player player, Ninja chosenNinja, String currentLevel,
                               String name, int score, int timeRemain) {
        ninja = chosenNinja;
        profileName = name;
        this.score = score;
        this.timeLeft = timeRemain;
        char levelChar = currentLevel.charAt(14);
        int level = Integer.parseInt(String.valueOf(levelChar));
        int nextLevel = level + 1;
        newLevel = "/Levels/Level0" + nextLevel + ".txt";

        AllProfile.loadProfile();
        AllProfile.updateLevel(name, level);
        AllScore.loadScore();
        AllScore.updateScore(name, score * timeLeft * TIME_FACTOR, level);

        this.menuOverStage = stage;
        this.menuOverStage.hide();
        createBackground();
        createText();
        HBox buttonsPane = new HBox();
        buttonsPane.setSpacing(20);
        buttonsPane.setAlignment(Pos.CENTER);
        createMainMenuButton(buttonsPane);
        if (level < MAX_LEVEL) {
            createNextLevelButton(buttonsPane);
        }
        createExitButton(buttonsPane);

        gameOverPane.getChildren().add(buttonsPane);
        gameOverPane.setAlignment(Pos.CENTER);
        gameOverPane.setSpacing(20);
        gameOverStage.show();
    }

    /**
     * create text file.
     */
    private void createText() {
        Text gameOverText = new Text("COMPLETED!");
        try {
            gameOverText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_SIZE));
        } catch (FileNotFoundException e) {
            gameOverText.setFont(Font.font("Verdana", FONT_SIZE));
        }
        gameOverPane.getChildren().add(gameOverText);
        Text profilePlayerName = new Text("Your Name:" + profileName);
        Text scoreText = new Text("Your Score:" + score);
        Text timeRemaining = new Text("Remaining time:" + timeLeft);
        Text totalScore = new Text("Your Score:" + score * timeLeft * TIME_FACTOR);

        try {
            profilePlayerName.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
            timeRemaining.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
            totalScore.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
        } catch (FileNotFoundException e) {
            profilePlayerName.setFont(Font.font("Verdana", 20));
            scoreText.setFont(Font.font("Verdana", 20));
            timeRemaining.setFont(Font.font("Verdana", 20));
            totalScore.setFont(Font.font("Verdana", 20));
        }
        gameOverPane.getChildren().add(profilePlayerName);
        gameOverPane.getChildren().add(scoreText);
        gameOverPane.getChildren().add(timeRemaining);
        gameOverPane.getChildren().add(totalScore);
    }

    /**
     * create main menu.
     *
     * @param buttonsPane
     */
    private void createMainMenuButton(HBox buttonsPane) {
        MainMenuButton mainMenuButton = new MainMenuButton("Menu");
        mainMenuButton.setOnAction(actionEvent -> {
            try {
                ViewManager mainMenu = new ViewManager();
                mainMenu.createNewMenu(gameOverStage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        buttonsPane.getChildren().add(mainMenuButton);
    }

    /**
     * create next level button.
     *
     * @param buttonsPane button
     */
    private void createNextLevelButton(HBox buttonsPane) {
        MainMenuButton mainMenuButton = new MainMenuButton("Next");
        mainMenuButton.setOnAction(actionEvent -> {
            GameViewManager nextLevel = new GameViewManager();
            nextLevel.createNewGame(gameOverStage, ninja, newLevel, profileName);
        });
        buttonsPane.getChildren().add(mainMenuButton);
    }

    /**
     * Creates the exit button, that exits the application.
     */
    private void createExitButton(HBox buttonsPane) {
        MainMenuButton exitButton = new MainMenuButton("Exit");
        exitButton.setOnAction(actionEvent -> gameOverStage.close());
        buttonsPane.getChildren().add(exitButton);
    }

    /**
     * Creates the background.
     */
    private void createBackground() {
        Background background = new Background(new BackgroundFill(
                Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY));
        gameOverPane.setBackground(background);
    }
}
