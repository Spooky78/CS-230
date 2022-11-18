package com.example.cs230;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 * Responsible for the button used on main menu.
 */
public class MainMenuButton extends Button {
    private final String FONT_PATH;
    {
        try {
            FONT_PATH = String.valueOf(new File(ClassLoader.getSystemResource(
                "kenvector_future.ttf").toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String BUTTON_FREE_PATH = "paperButton.png";
    private static final String BUTTON_PRESSED_PATH = "paperButtonPressed.png";
    private final String BUTTON_PRESSED_STYLE =
        "-fx-background-color: transparent; -fx-background-image: url('"
            + BUTTON_PRESSED_PATH + "');";
    private final String BUTTON_FREE_STYLE =
        "-fx-background-color: transparent; -fx-background-image: url('"
            + BUTTON_FREE_PATH + "');";
    private static final int PREF_WIDTH = 182;
    private static final int PREF_HEIGHT = 45;
    private static final int PRESSED_PREF_HEIGHT = 50;
    private static final int PRESSED_HEIGHT_OFFSET = 5;
    private static final int FONT_SIZE = 23;

    /**
     * Creates a main menu button.
     * @param text the text that the button will display.
     */
    public MainMenuButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(PREF_WIDTH);
        setPrefHeight(PREF_HEIGHT);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListener();
    }

    /**
     * Sets the button text font & size.
     */
    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_SIZE));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", FONT_SIZE));
        }
    }

    /**
     * Sets button pressed style.
     */
    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(PRESSED_PREF_HEIGHT);
        setLayoutY(getLayoutY() + PRESSED_HEIGHT_OFFSET);
    }

    /**
     * Sets button released style.
     */
    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(PREF_HEIGHT);
        setLayoutY(getLayoutY() - PRESSED_HEIGHT_OFFSET);
    }

    /**
     * Initializes button listeners. Listens for mouse events for button to be
     * pressed and released.
     */
    private void initializeButtonListener() {

        setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(mouseEvent -> setEffect(new DropShadow()));

        setOnMouseExited(mouseEvent -> setEffect(null));
    }
}
