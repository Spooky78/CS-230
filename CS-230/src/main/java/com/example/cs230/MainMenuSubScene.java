package com.example.cs230;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Responsible for the main menu sub scene.
 */
public class MainMenuSubScene {

    private static final String BACKGROUND_IMAGE = "paperPanel.png";
    private static final int PREF_WIDTH = 100;
    private static final int PREF_HEIGHT = 100;
    private boolean isHidden;
    private VBox parentPane;

    /**
     * Creates a main menu sub scene off-screen to the right.
     */
    public MainMenuSubScene() {
        parentPane = new VBox();
        parentPane.setAlignment(Pos.CENTER);

        parentPane.setPadding(new Insets(50));

        Image image = new Image(BACKGROUND_IMAGE, PREF_WIDTH,
                PREF_HEIGHT, false, false);
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, true, true));

        parentPane.setBackground(new Background(background));

        isHidden = true;
    }

    /**
     * Gets the pane.
     *
     * @return Returns the pane.
     */

    public VBox getPane() {
        return parentPane;
    }
}

















