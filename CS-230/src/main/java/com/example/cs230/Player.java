package com.example.cs230;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Player {
    private boolean isLeftKeyPressed = false;
    private boolean isRightKeyPressed = false;
    private boolean isUpKeyPressed = false;
    private boolean isDownKeyPressed = false;
    private StackPane playerStackPane = new StackPane();
    private ImageView player;

    public Player(Scene gameScene, Ninja chosenNinja){
        createKeyListeners(gameScene);
        createPlayer(chosenNinja);
    }

    private void createPlayer(Ninja chosenNinja){
        player = new ImageView(chosenNinja.getUrlNinja());
        player.setFitWidth(100);
        player.setFitHeight(100);
        playerStackPane.getChildren().add(player);
    }

    private void createKeyListeners(Scene gameScene){
        gameScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.LEFT){
                isLeftKeyPressed = true;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = true;
            } else if (keyEvent.getCode() == KeyCode.UP) {
                isUpKeyPressed = true;
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                isDownKeyPressed = true;
            }
            movePlayer();
        });

        gameScene.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.LEFT){
                isLeftKeyPressed = false;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = false;
            } else if (keyEvent.getCode() == KeyCode.UP) {
                isUpKeyPressed = false;
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                isDownKeyPressed = false;
            }
        });
    }

    private void movePlayer() {
        if (isLeftKeyPressed && (!isRightKeyPressed && !isDownKeyPressed && !isUpKeyPressed)) {
            playerStackPane.setLayoutX(playerStackPane.getLayoutX() - 200);
        }

        if (isRightKeyPressed && (!isLeftKeyPressed && !isDownKeyPressed && !isUpKeyPressed)) {
            playerStackPane.setLayoutX(playerStackPane.getLayoutX() + 200);
        }

        if (isUpKeyPressed && (!isLeftKeyPressed && !isDownKeyPressed && !isRightKeyPressed)) {
            playerStackPane.setLayoutY(playerStackPane.getLayoutY() - 200);
        }

        if (isDownKeyPressed && (!isLeftKeyPressed && !isRightKeyPressed && !isUpKeyPressed)) {
            playerStackPane.setLayoutY(playerStackPane.getLayoutY() + 200);
        }

        if (isRightKeyPressed && (isLeftKeyPressed || isDownKeyPressed || isUpKeyPressed)) {
            playerStackPane.setLayoutX(playerStackPane.getLayoutX() + 0);
        }

        if (isLeftKeyPressed && (isRightKeyPressed || isDownKeyPressed || isUpKeyPressed)) {
            playerStackPane.setLayoutX(playerStackPane.getLayoutX() + 0);
        }

        if (isDownKeyPressed && (isRightKeyPressed || isLeftKeyPressed || isUpKeyPressed)) {
            playerStackPane.setLayoutY(playerStackPane.getLayoutY() + 0);
        }

        if (isLeftKeyPressed && (isDownKeyPressed || isRightKeyPressed || isUpKeyPressed)) {
            playerStackPane.setLayoutY(playerStackPane.getLayoutY() + 0);
        }
        if (isUpKeyPressed && (isRightKeyPressed || isLeftKeyPressed || isDownKeyPressed)) {
            playerStackPane.setLayoutY(playerStackPane.getLayoutY() + 0);
        }
        if (isUpKeyPressed && (isDownKeyPressed || isLeftKeyPressed || isRightKeyPressed)) {
            playerStackPane.setLayoutY(playerStackPane.getLayoutY() + 0);
        }
    }


    public StackPane getPlayerStack(){
        return playerStackPane;
    }
}
