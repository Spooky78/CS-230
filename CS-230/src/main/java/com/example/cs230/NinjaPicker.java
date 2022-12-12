package com.example.cs230;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Responsible for the picking player character.
 *
 * @author Vic
 */
public class NinjaPicker extends VBox {
    private final ImageView boxImage;
    private static final String BOX_NOT_CHOSEN = "grey_box.png";
    private static final String BOX_CHOSEN = "checkmark.png";
    private static final int SPACING = 10;
    private final Ninja ninja;
    private boolean isBoxChosen;

    /**
     * Choose the ninja that the player clicks on his picture in the character select to become
     * the player character.
     *
     * @param ninjaCharacter the chosen ninja
     */
    public NinjaPicker(Ninja ninjaCharacter) {
        boxImage = new ImageView(BOX_NOT_CHOSEN);
        Image image = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(ninjaCharacter.getNinjaFaceset())));
        ImageView shipImage = new ImageView(image);
        this.ninja = ninjaCharacter;
        isBoxChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(SPACING);
        this.getChildren().add(boxImage);
        this.getChildren().add(shipImage);
    }

    /**
     * get ninja.
     *
     * @return ninja
     */
    public Ninja getNinja() {
        return ninja;
    }

    /**
     * check which box is clicked/whether clicked.
     *
     * @param isBoxChosenParam which checkbox was chosen
     */
    public void setIsBoxChosen(boolean isBoxChosenParam) {
        this.isBoxChosen = isBoxChosenParam;
        String imageToSet = this.isBoxChosen ? BOX_CHOSEN : BOX_NOT_CHOSEN;
        boxImage.setImage(new Image(imageToSet));
    }
}
