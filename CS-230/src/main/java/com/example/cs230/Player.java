package com.example.cs230;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

/**
 * A class that handles the player and all its collisions with items and NPCs,
 * its movements, and its score.
 * @author Everyone
 */
public class Player {
    private static final int PLAYER_WIDTH = 50;
    private static final int PLAYER_HEIGHT = 50;
    private static final int MOVEMENT_OFFSET = 10;
    private boolean isLeftKeyPressed = false;
    private boolean isRightKeyPressed = false;
    private boolean isUpKeyPressed = false;
    private boolean isDownKeyPressed = false;
    private boolean isSaveKeyPressed = false;
    private int movementOffset;
    private StackPane playerStackPane = new StackPane();
    private ImageView player;
    private Board board;
    private Ninja chosenNinja;
    private int[] playerCoords = new int[2];
    private int score = 0;
    private int time = 0;
    boolean canMove;
    GameViewManager manager;

    /**
     * Creates a player.
     *
     * @param gameScene    the game scene
     * @param ninja        the player character
     * @param currentBoard the current level
     * @param manager      the game view manager
     */
    public Player(Scene gameScene, Ninja ninja, Board currentBoard, GameViewManager manager) {
        this.manager = manager;
        this.movementOffset = MOVEMENT_OFFSET;
        board = currentBoard;
        this.chosenNinja = ninja;
        createKeyListeners(gameScene);
        createPlayer(chosenNinja);
    }

    /**
     * creates a player with the chosen ninja image.
     *
     * @param chosenNinja the chosen ninja from the select menu.
     */
    private void createPlayer(Ninja chosenNinja) {
        Image playerImage = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(chosenNinja.getUrlNinjaDown())));
        player = new ImageView(playerImage);
        player.setFitWidth(PLAYER_WIDTH);
        player.setFitHeight(PLAYER_HEIGHT);

        playerCoords = board.getPlayerStartCoords();

        playerStackPane.getChildren().add(player);
    }

    /**
     * creates listeners for arrow key presses.
     *
     * @param gameScene the game scene
     */
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
            } else if (keyEvent.getCode() == KeyCode.S) {
                isSaveKeyPressed = true;
            }
            movePlayer();
            try {
                manager.saveGave();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            } else if (keyEvent.getCode() == KeyCode.S) {
                isSaveKeyPressed = false;
            }
        });
    }

    /**
     * checks if the save key is pressed.
     *
     * @return true if the save key is pressed.
     */
    public boolean isSaveKeyPressed() {
        return isSaveKeyPressed;
    }

    /**
     * moves the player in the direction of the arrow key that was pressed
     * by first checking if the player can move to the next tile.
     */
    private void movePlayer() {
        if (isLeftKeyPressed && (!isRightKeyPressed && !isDownKeyPressed && !isUpKeyPressed)) {
            try {
                setImage("LEFT");
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0] - 1, playerCoords[1]});
                boolean nonSteppableTile = !manager.checkNonSteppableTile(new int[]{playerCoords[0], playerCoords[1] +1});
                int currentOffset = 1;
                while (!canMove || !nonSteppableTile) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords, new int[]{playerCoords[0] - currentOffset, playerCoords[1]});
                    nonSteppableTile = !manager.checkNonSteppableTile(new int[] {playerCoords[0] , playerCoords[1] +1 + currentOffset});

                }
                manager.checkNonSteppableTile(playerCoords);
                if (canMove && nonSteppableTile) {
                    playerStackPane.setLayoutX(playerStackPane.getLayoutX() - (movementOffset * currentOffset));
                    playerCoords[0] = playerCoords[0] - currentOffset;
                }
            } catch (Exception e) {
                System.out.println("Can't move there.");
            }
        }

        if (isRightKeyPressed && (!isLeftKeyPressed && !isDownKeyPressed && !isUpKeyPressed)) {
            try {
                setImage("RIGHT");
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0] + 1, playerCoords[1]});
                boolean nonSteppableTile = !manager.checkNonSteppableTile(new int[]{playerCoords[0]+2, playerCoords[1] +1});

                int currentOffset = 1;
                while (!canMove ||!nonSteppableTile) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords,new int[]{playerCoords[0] + currentOffset, playerCoords[1]});
                    nonSteppableTile = !manager.checkNonSteppableTile(new int[] {playerCoords[0] +2, playerCoords[1]+1 + currentOffset});

                }


                if (canMove && nonSteppableTile) {
                    playerStackPane.setLayoutX(playerStackPane.getLayoutX() + (movementOffset * currentOffset));
                    playerCoords[0] = playerCoords[0] + currentOffset;
                }

            } catch (Exception e) {
                System.out.println("Can't move there.");
            }
        }

        if (isUpKeyPressed && (!isLeftKeyPressed && !isDownKeyPressed && !isRightKeyPressed)) {
            try {
                setImage("UP");
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] - 1});
                boolean nonSteppableTile = !manager.checkNonSteppableTile(new int[]{playerCoords[0]+1, playerCoords[1]});
                int currentOffset = 1;
                while (!canMove || !nonSteppableTile) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] - currentOffset});
                    nonSteppableTile = !manager.checkNonSteppableTile(new int[] {playerCoords[0] +1, playerCoords[1] + currentOffset});
                }
                if (canMove && nonSteppableTile) {
                    playerStackPane.setLayoutY(playerStackPane.getLayoutY() - (movementOffset * currentOffset));
                    playerCoords[1] = playerCoords[1] - currentOffset;
                }
            } catch (Exception e) {
                System.out.println("Can't move there.");
            }
        }

        if (isDownKeyPressed && (!isLeftKeyPressed && !isRightKeyPressed && !isUpKeyPressed)) {
            try {
                setImage("DOWN");
                boolean canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] + 1});
                boolean nonSteppableTile = !manager.checkNonSteppableTile(new int[]{playerCoords[0]+1, playerCoords[1] + 2});
                int currentOffset = 1;
                while (!canMove || !nonSteppableTile) {
                    currentOffset++;
                    canMove = board.canMove(playerCoords, new int[]{playerCoords[0], playerCoords[1] + currentOffset});
                    nonSteppableTile = !manager.checkNonSteppableTile(new int[] {playerCoords[0] +1, playerCoords[1] + currentOffset+1});
                }

                if (canMove && nonSteppableTile){
                    playerStackPane.setLayoutY(playerStackPane.getLayoutY() + (movementOffset * currentOffset));
                    playerCoords[1] = playerCoords[1] + currentOffset;
                }

            } catch (Exception e) {
                System.out.println("Can't move there.");
            }
        }

    }

    /**
     * sets the player's image to the one facing in the direction of the pressed key.
     *
     * @param direction
     */
    private void setImage(String direction) {
        playerStackPane.getChildren().remove(player);
        Image playerImage = switch (direction) {
            case "LEFT" -> new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(chosenNinja.getUrlNinjaLeft())));
            case "RIGHT" -> new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(chosenNinja.getUrlNinjaRight())));
            case "UP" -> new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(chosenNinja.getUrlNinjaUp())));
            default -> new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(chosenNinja.getUrlNinjaDown())));
        };
        player = new ImageView(playerImage);
        player.setFitWidth(PLAYER_WIDTH);
        player.setFitHeight(PLAYER_HEIGHT);
        playerStackPane.getChildren().add(player);
    }

    /**
     * sets the movement offset.
     *
     * @param newOffset the new offset
     */
    public void setMovementOffset(int newOffset) {
        movementOffset = newOffset;
    }

    /**
     * sets the score to the new score.
     *
     * @param newScore the new score
     */
    public void setScore(int newScore) {
        score = newScore;
    }

    /**
     * sets the time to the new time.
     *
     * @param newTime the new time
     */
    public void setTime(int newTime) {
        time = newTime;
    }

    /**
     * gets the player stack.
     *
     * @return the player stack.
     */
    public StackPane getPlayerStack() {
        return playerStackPane;
    }

    /**
     * gets the player position.
     *
     * @return the player position
     */
    public int[] getPlayerCoords() {
        return playerCoords;
    }

    /**
     * gets the current score.
     *
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * gets the current time.
     *
     * @return the current time.
     */
    public int getTime() {
        return time;
    }
}
