package com.example.cs230;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Responsible for the picking player character.
 */
public class NinjaPicker extends VBox {
    private final ImageView boxImage;
    private static final String BOX_NOT_CHOSEN = "grey_box.png";
    private static final String BOX_CHOSEN = "checkmark.png";
    private static final int SPACING = 10;
    private final Ninja ninja;
    private boolean isBoxChosen;

    public NinjaPicker(Ninja ninjaCharacter) {
        boxImage = new ImageView(BOX_NOT_CHOSEN);
        ImageView shipImage = new ImageView(ninjaCharacter.getNinjaFaceset());
        this.ninja = ninjaCharacter;
        isBoxChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(SPACING);
        this.getChildren().add(boxImage);
        this.getChildren().add(shipImage);
    }

    public Ninja getNinja() {
        return ninja;
    }

    public void setIsBoxChosen(boolean isBoxChosenParam) {
        this.isBoxChosen = isBoxChosenParam;
        String imageToSet = this.isBoxChosen ? BOX_CHOSEN : BOX_NOT_CHOSEN;
        boxImage.setImage(new Image(imageToSet));
    }
}
