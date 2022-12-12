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

/**
 * create a save view manager.
 *
 * @author Vic
 */
public class SaveViewManager {
    private static final int GAME_WIDTH = 450;
    private static final int GAME_HEIGHT = 300;
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

    /**
     * Creates a GameOverViewManager.
     */
    public SaveViewManager() {
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
     * @param stage The previous stage (usually menuStage).
     */
    public void createSave(Stage stage, Player player) {
        this.menuOverStage = stage;
        this.menuOverStage.hide();
        createBackground();
        createText(player);
        HBox buttonsPane = new HBox();
        buttonsPane.setSpacing(20);
        buttonsPane.setAlignment(Pos.CENTER);
        createMainMenuButton(buttonsPane);
        createExitButton(buttonsPane);

        gameOverPane.getChildren().add(buttonsPane);
        gameOverPane.setAlignment(Pos.CENTER);
        gameOverPane.setSpacing(20);
        gameOverStage.show();
    }

    /**
     * create text.
     *
     * @param player current player
     */
    private void createText(Player player) {
        Text gameOverText = new Text("YOU HAVE SAVED GAME!");
        try {
            gameOverText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_SIZE));
        } catch (FileNotFoundException e) {
            gameOverText.setFont(Font.font("Verdana", FONT_SIZE));
        }
        gameOverPane.getChildren().add(gameOverText);
        Text scoreText = new Text("Your Score:" + player.getScore());
        try {
            scoreText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            scoreText.setFont(Font.font("Verdana", 30));
        }
        gameOverPane.getChildren().add(scoreText);
    }

    /**
     * create main menu button.
     *
     * @param buttonsPane button pane
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
