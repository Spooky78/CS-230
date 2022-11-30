package com.example.cs230;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class Player {
    private boolean isLeftKeyPressed = false;
    private boolean isRightKeyPressed = false;
    private boolean isUpKeyPressed = false;
    private boolean isDownKeyPressed = false;
    private int movementOffset;
    private StackPane playerStackPane = new StackPane();
    private ImageView player;
    private Board board;
    private int[] playerCoords = new int[2];

    public Player(Scene gameScene, Ninja chosenNinja, Board currentBoard) {
        this.movementOffset = 10;
        board = currentBoard;
        createKeyListeners(gameScene);
        createPlayer(chosenNinja);
    }

    private void createPlayer(Ninja chosenNinja) {
        player = new ImageView(chosenNinja.getUrlNinja());
        player.setFitWidth(50);
        player.setFitHeight(50);

        playerCoords = board.getPlayerStartCoords();

        playerStackPane.getChildren().add(player);
    }

    private void createKeyListeners(Scene gameScene) {
        gameScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
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
            if (keyEvent.getCode() == KeyCode.LEFT) {
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
            try {
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0] - 1, playerCoords[1]});
                int currentOffset = 1;
                while (!canMove) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords, new int[]{playerCoords[0] - currentOffset, playerCoords[1]});
                }
                if (canMove) {
                    playerStackPane.setLayoutX(playerStackPane.getLayoutX() - (movementOffset * currentOffset));
                    playerCoords[0] = playerCoords[0] - currentOffset;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }

        if (isRightKeyPressed && (!isLeftKeyPressed && !isDownKeyPressed && !isUpKeyPressed)) {
            try {
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0] + 1, playerCoords[1]});
                int currentOffset = 1;
                while (!canMove) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords,
                            new int[]{playerCoords[0] + currentOffset, playerCoords[1]});

                }
                if (canMove) {
                    playerStackPane.setLayoutX(playerStackPane.getLayoutX() + (movementOffset * currentOffset));
                    playerCoords[0] = playerCoords[0] + currentOffset;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }

        if (isUpKeyPressed && (!isLeftKeyPressed && !isDownKeyPressed && !isRightKeyPressed)) {
            try {
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] - 1});
                int currentOffset = 1;
                while (!canMove) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] - currentOffset});
                }
                if (canMove) {
                    playerStackPane.setLayoutY(playerStackPane.getLayoutY() - (movementOffset * currentOffset));
                    playerCoords[1] = playerCoords[1] - currentOffset;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }

        if (isDownKeyPressed && (!isLeftKeyPressed && !isRightKeyPressed && !isUpKeyPressed)) {
            try {
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] + 1});
                int currentOffset = 1;
                while (!canMove) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] + currentOffset});
                }
                if (canMove) {
                    playerStackPane.setLayoutY(playerStackPane.getLayoutY() + (movementOffset * currentOffset));
                    playerCoords[1] = playerCoords[1] + currentOffset;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }

    }

    public void setMovementOffset(int newOffset) {
        movementOffset = newOffset;
    }

    public StackPane getPlayerStack() {
        return playerStackPane;
    }
}
