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

    private static final String BACKGROUND_IMAGE = "paperPanel.png";
    private static final int PREF_WIDTH = 450;
    private static final int PREF_HEIGHT = 400;
    private static final int POSITION_X = 800;
    private static final int POSITION_Y = 150;
    private boolean isHidden;
    private StackPane parentPane;

    /**
     * Creates a main menu sub scene off-screen to the right.
     */
    public MainMenuSubScene(StackPane pane) {
        super(new AnchorPane(), PREF_WIDTH, PREF_HEIGHT);
        parentPane = pane;
        //prefWidth(PREF_WIDTH);
        //prefHeight(PREF_HEIGHT);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, PREF_WIDTH,
            PREF_HEIGHT, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));


        isHidden = true;
        setLayoutX(-500);
        setLayoutY(-500);
    }

    /**
     * Moves sub scene to new position. If off-screen move to on-screen. If on-screen moves
     * off-screen.
     */
    public void moveSubScene() {

        if (isHidden) {
            parentPane.getChildren().add(this);
            isHidden = false;
        } else {
            parentPane.getChildren().remove(this);
            isHidden = true;
        }
    }

    /**
     * Gets the pane.
     * @return Returns the pane.
     */
    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}

















