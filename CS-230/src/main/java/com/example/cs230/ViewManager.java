package com.example.cs230;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Responsible for the main menu window.
 * @author Spooky78
 */
public class ViewManager {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final BorderPane mainPane;
    private final Scene mainScene;
    private final Stage mainStage;
    private static final String BACKGROUND_PATH = "treesBackground.png";
    private static VBox buttonPane;
    private MainMenuSubScene creditsSubScene;
    private MainMenuSubScene helpSubScene;
    private MainMenuSubScene scoreSubScene;
    private MainMenuSubScene shipChooserSubScene;
    private MainMenuSubScene sceneToHide;

    /**
     * Creates a main menu window.
     */
    public ViewManager() {
        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createBackground();
        createLogo();
        createButtons();

    }

    /**
     * If sub scene is hidden then move it.
     * @param subScene The sub scene to be moved.
     */
    private void showSubScene(MainMenuSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    /**
     * Creates the sub scenes for the main menu.
     */
    private void createSubScenes() {
        StackPane subscenePane = new StackPane();
        creditsSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(scoreSubScene);

        shipChooserSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(shipChooserSubScene);

        mainPane.setCenter(subscenePane);
    }


    /**
     * Gets main stage.
     * @return The main stage.
     */
    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Creates all main menu buttons.
     */
    private void createButtons() {
        buttonPane = new VBox();
        buttonPane.setSpacing(20);
        buttonPane.setAlignment(Pos.CENTER_LEFT);
        buttonPane.setPadding(new Insets(0,20,20,60));
        mainPane.setLeft(buttonPane);
        createStartButton();
        createScoreButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    /**
     * Creates the play button.
     */
    private void createStartButton() {
        MainMenuButton startButton = new MainMenuButton("PLAY");
        buttonPane.getChildren().add(startButton);
        startButton.setOnAction(actionEvent -> showSubScene(shipChooserSubScene));
    }

    /**
     * Creates the score button.
     */
    private void createScoreButton() {
        MainMenuButton scoreButton = new MainMenuButton("Scores");
        buttonPane.getChildren().add(scoreButton);
        scoreButton.setOnAction(actionEvent -> showSubScene(scoreSubScene));
    }

    /**
     * Creates the help button.
     */
    private void createHelpButton() {
        MainMenuButton helpButton = new MainMenuButton("Help");
        buttonPane.getChildren().add(helpButton);
        helpButton.setOnAction(actionEvent -> showSubScene(helpSubScene));
    }

    /**
     * Creates the credits button.
     */
    private void createCreditsButton() {
        MainMenuButton creditsButton = new MainMenuButton("Credits");
        buttonPane.getChildren().add(creditsButton);
        creditsButton.setOnAction(actionEvent -> showSubScene(creditsSubScene));
    }

    /**
     * Creates the exit button, that exits the application.
     */
    private void createExitButton() {
        MainMenuButton exitButton = new MainMenuButton("Exit");
        buttonPane.getChildren().add(exitButton);
        exitButton.setOnAction(actionEvent -> mainStage.close());
    }

    /**
     * Sets the background to images.
     */
    private void createBackground() {
        Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100,
            true, true, true, true));
        mainPane.setBackground(new Background(background));
    }

    /**
     * Creates the game logo.
     */
    private void createLogo() {

        ImageView logo = new ImageView("logo.png");

        logo.setOnMouseEntered(mouseEvent -> logo.setEffect(new DropShadow()));
        logo.setOnMouseExited(mouseEvent -> logo.setEffect(null));

        VBox logoPane = new VBox();
        logoPane.getChildren().add(logo);
        logoPane.setAlignment(Pos.TOP_CENTER);
        logoPane.setPadding(new Insets(30,0,0,0));
        mainPane.setTop(logoPane);
    }
}