package com.example.cs230;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Responsible for the main menu sub scene.
 */
public class MainMenuSubScene extends SubScene {

    private static final String BACKGROUND_IMAGE = "yellow_panel.png";
    private static final int PREF_WIDTH = 450;
    private static final int PREF_HEIGHT = 400;
    private static final int POSITION_X = 800;
    private static final int POSITION_Y = 150;
    private static final int MOVE_X_OFFSCREEN = -500;
    private static final double TRANSITION_TIME = 0.3;
    private boolean isHidden;

    /**
     * Creates a main menu sub scene off-screen to the right.
     */
    public MainMenuSubScene() {
        super(new AnchorPane(), PREF_WIDTH, PREF_HEIGHT);
        prefWidth(PREF_WIDTH);
        prefHeight(PREF_HEIGHT);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, PREF_WIDTH,
            PREF_HEIGHT, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true;
        setLayoutX(POSITION_X);
        setLayoutY(POSITION_Y);
    }

    /**
     * Moves sub scene to new position. If off-screen move to on-screen. If on-screen moves
     * off-screen.
     */
    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(TRANSITION_TIME));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(MOVE_X_OFFSCREEN);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }
        transition.play();
    }

    /**
     * Gets the pane.
     * @return Returns the pane.
     */
    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}

















