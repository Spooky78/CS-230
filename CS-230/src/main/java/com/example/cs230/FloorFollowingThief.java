package com.example.cs230;

import java.util.Objects;
import java.util.Timer;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * responsible for the floor following thief.
 * @author Vic, Rex, Omar
 */
public class FloorFollowingThief extends NPC {
    private static final String FFTHIEF_DOWN_PATH = "/Skeleton/SkeletonDown.png";
    private static final String FFTHIEF_UP_PATH = "/Skeleton/SkeletonUp.png";
    private static final String FFTHIEF_LEFT_PATH = "/Skeleton/SkeletonLeft.png";
    private static final String FFTHIEF_RIGHT_PATH = "/Skeleton/SkeletonRight.png";
    private static final int MILLS_DELAY_TILE = 500;
    private static final int IMAGE_SIZE = 50;
    private final Board gameBoard;
    private final StackPane ffThiefStackPane;
    private final int[] coords;
    private final int[] animationCoords;
    private final GameViewManager manager;
    private final String tileColour;
    private ImageView ffThief;
    private int[] coordsFinal = new int[2];
    private Timer timer;
    private String nextDirection;
    private boolean isFinishDirection = false;
    private int counter = 0;
    private int counterAnimation = 0;
    private int jumps = 0;

    /**
     * Constructor for the floor following thief.
     * @param board the board .
     * @param startCoords the start coordinates.
     * @param stackPane the stack pane to use.
     * @param indexID the index id related to board.
     * @param manager the game manager.
     */
    public FloorFollowingThief(Board board, int[] startCoords, StackPane stackPane, int indexID,
                               GameViewManager manager) {
        gameBoard = board;
        coords = startCoords;
        animationCoords = startCoords;
        this.tileColour = board.getFloorFollowingThiefColours().get(indexID);
        coordsFinal = new int[]{board.getFloorFollowingThiefStartCoords().get(indexID * 2),
                board.getFloorFollowingThiefStartCoords().get(indexID * 2 + 1)};
        ffThiefStackPane = stackPane;
        this.manager = manager;
        createNPC();
        nextDirection = gameBoard.getFloorFollowingThiefDirectionStart().get(indexID);
        move();
    }

    /**
     * Stops the timer.
     */
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * creates the floor following npc.
     */
    protected void createNPC() {
        Image ffThiefImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_DOWN_PATH)));
        ffThief = new ImageView(ffThiefImage);
        ffThief.setFitWidth(IMAGE_SIZE);
        ffThief.setFitHeight(IMAGE_SIZE);
        int tileSize = gameBoard.getTileSize();
        ffThiefStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        ffThiefStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    /**
     * the move the floor following npc.
     */
    public void move() {
        isFinishDirection = false;
        Timeline animation;
        switch (nextDirection) {
            case "RIGHT":
                setImage("RIGHT");
                counterAnimation = 0;
                jumps = calcRight();
                animation = new Timeline(new KeyFrame(Duration.millis(MILLS_DELAY_TILE),
                        e -> moveRight()));
                animation.setCycleCount(jumps);
                animation.play();
                if (jumps == 0) {
                    nextDirection = "UP";
                    isFinishDirection = true;
                }
                break;
            case "LEFT":
                setImage("LEFT");
                counterAnimation = 0;
                jumps = calcLeft();
                animation = new Timeline(new KeyFrame(Duration.millis(MILLS_DELAY_TILE),
                        e -> moveLeft()));
                animation.setCycleCount(jumps);
                animation.play();
                if (jumps == 0) {
                    nextDirection = "DOWN";
                    isFinishDirection = true;
                }
                break;
            case "UP":
                setImage("UP");
                counterAnimation = 0;
                jumps = calcUp();
                animation = new Timeline(new KeyFrame(Duration.millis(MILLS_DELAY_TILE),
                        e -> moveUp()));
                animation.setCycleCount(jumps);
                animation.play();
                if (jumps == 0) {
                    nextDirection = "LEFT";
                    isFinishDirection = true;
                }
                break;
            case "DOWN":
                setImage("DOWN");
                counterAnimation = 0;
                System.out.println("GOING DOWN");
                jumps = calcDown();
                animation = new Timeline(new KeyFrame(Duration.millis(MILLS_DELAY_TILE),
                        e -> moveDown()));
                animation.setCycleCount(jumps);
                animation.play();
                if (jumps == 0) {
                    nextDirection = "RIGHT";
                    isFinishDirection = true;
                }
                break;
        }

    }

    /**
     * Calculates the number of tiles to go right.
     * @return the number of tiles to move right.
     */
    private int calcRight() {
        counter = 0;
        int[] startAnimationCoords = animationCoords;
        int[] wantToMove = new int[]{animationCoords[0] + 1, animationCoords[1]};
        while (wantToMove[0] <= gameBoard.getBoardSizeX()
                && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                && !manager.checkNonSteppableTile(wantToMove)) {
            wantToMove[0]++;
            animationCoords[0]++;
            counter++;
        }
        return counter;

    }

    /**
     * Calculates the number of tiles to go left.
     * @return the number of tiles to move left.
     */
    private int calcLeft() {
        counter = 0;
        int[] startAnimationCoords = animationCoords;
        int[] wantToMove = new int[]{animationCoords[0] - 1, animationCoords[1]};
        while (wantToMove[0] > 0 && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                && !manager.checkNonSteppableTile(wantToMove)) {
            wantToMove[0]--;
            animationCoords[0]--;
            System.out.println(counter);
            counter++;
        }
        return counter;
    }

    /**
     * Calculates the number of tiles to go up.
     * @return the number of tiles to move up.
     */
    private int calcUp() {
        counter = 0;
        int[] startAnimationCoords = animationCoords;
        int[] wantToMove = new int[]{animationCoords[0], animationCoords[1] - 1};
        while (wantToMove[1] > 0 && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                && !manager.checkNonSteppableTile(wantToMove)) {
            wantToMove[1]--;
            animationCoords[1]--;
            System.out.println(counter);
            counter++;
        }
        return counter;
    }

    /**
     * Calculates the number of tiles to go down.
     * @return the number of tiles to move down.
     */
    private int calcDown() {
        try {
            counter = 0;
            int[] startAnimationCoords = animationCoords;
            int[] wantToMove = new int[]{animationCoords[0], animationCoords[1] + 1};
            while (wantToMove[1] <= gameBoard.getBoardSizeY()
                    && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                    && !manager.checkNonSteppableTile(wantToMove)) {
                wantToMove[1]++;
                animationCoords[1]++;
                System.out.println(counter);
                counter++;
            }
            return counter;
        } catch (Exception e) {
            System.out.println("COULDN'T CALCULATE DOWN");
            return 0;
        }
    }

    /**
     * moves one tile right.
     */
    private void moveRight() {
        try {
            double moveAcross = 0;
            int[] startAnimationCoords = coordsFinal;
            int[] wantToMove = new int[]{coordsFinal[0] + 1, coordsFinal[1]};
            if (wantToMove[0] <= gameBoard.getBoardSizeX()
                    && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                    && !manager.checkNonSteppableTile(wantToMove)) {
                ffThiefStackPane.setLayoutX(ffThiefStackPane.getLayoutX()
                        + gameBoard.getTileSize());
                wantToMove[0]++;
                coordsFinal[0]++;
                counterAnimation++;
                if (counterAnimation == jumps) {
                    nextDirection = "DOWN";
                    isFinishDirection = true;
                }
            }
            ffThiefStackPane.setLayoutX(ffThiefStackPane.getLayoutX() + moveAcross);
        } catch (Exception e) {
            System.out.println("COULDN'T GO RIGHT");
        }
    }

    /**
     * moves one tile left.
     */
    private void moveLeft() {
        try {
            double moveAcross = 0;
            int[] startAnimationCoords = coordsFinal;
            int[] wantToMove = new int[]{coordsFinal[0] - 1, coordsFinal[1]};
            if (wantToMove[0] > 0 && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                    && !manager.checkNonSteppableTile(wantToMove)) {
                ffThiefStackPane.setLayoutX(-ffThiefStackPane.getLayoutX()
                        + gameBoard.getTileSize());
                wantToMove[0]--;
                coordsFinal[0]--;
                counterAnimation++;
                if (counterAnimation == jumps) {
                    nextDirection = "UP";
                    isFinishDirection = true;
                }
            }
            ffThiefStackPane.setLayoutX(-ffThiefStackPane.getLayoutX() + moveAcross);
        } catch (Exception e) {
            System.out.println("COULDN'T GO LEFT");
        }
    }

    /**
     * moves one tile up.
     */
    private void moveUp() {
        try {
            double moveAcross = 0;
            int[] startAnimationCoords = coordsFinal;
            int[] wantToMove = new int[]{coordsFinal[0], coordsFinal[1] - 1};
            if (wantToMove[1] <= gameBoard.getBoardSizeY()
                    && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                    && !manager.checkNonSteppableTile(wantToMove)) {
                ffThiefStackPane.setLayoutY(-ffThiefStackPane.getLayoutY()
                        + gameBoard.getTileSize());
                wantToMove[1]--;
                coordsFinal[1]--;
                counterAnimation++;
                if (counterAnimation == jumps) {
                    nextDirection = "RIGHT";
                    isFinishDirection = true;
                }
            }
            ffThiefStackPane.setLayoutY(-ffThiefStackPane.getLayoutY() + moveAcross);
        } catch (Exception e) {
            System.out.println("COULDN'T GO UP");
        }
    }

    /**
     * moves one tile down.
     */
    private void moveDown() {
        try {
            double moveAcross = 0;
            int[] startAnimationCoords = coordsFinal;
            int[] wantToMove = new int[]{coordsFinal[0], coordsFinal[1] + 1};
            if (wantToMove[1] <= gameBoard.getBoardSizeY()
                    && gameBoard.canMoveNPC(startAnimationCoords, wantToMove)
                    && !manager.checkNonSteppableTile(wantToMove)) {
                ffThiefStackPane.setLayoutY(ffThiefStackPane.getLayoutY()
                        + gameBoard.getTileSize());
                wantToMove[1]++;
                coordsFinal[1]++;
                counterAnimation++;
                if (counterAnimation == jumps) {
                    nextDirection = "LEFT";
                    isFinishDirection = true;
                }
            }
            ffThiefStackPane.setLayoutY(ffThiefStackPane.getLayoutY() + moveAcross);
        } catch (Exception e) {
            System.out.println("COULDN'T GO DOWN");
        }
    }

    /**
     * sets the image of the floor following thief.
     * @param direction the direction to change the image to.
     */
    private void setImage(String direction) {
        Image ffThiefImage = null;
        switch (direction) {
            case "LEFT":
                ffThiefImage = new Image(Objects.requireNonNull(getClass().
                        getResourceAsStream(FFTHIEF_LEFT_PATH)));
                break;
            case "RIGHT":
                ffThiefImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_RIGHT_PATH)));
                break;
            case "UP":
                ffThiefImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_UP_PATH)));
                break;
            default:
                ffThiefImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(FFTHIEF_DOWN_PATH)));
                break;
        };
        ffThief.setImage(ffThiefImage);
        ffThief.setFitWidth(IMAGE_SIZE);
        ffThief.setFitHeight(IMAGE_SIZE);
    }

    /**
     * is the movement in current direction finished.
     * @return is the current direction finished.
     */
    public boolean isFinishDirection() {
        return isFinishDirection;
    }

    /**
     * returns the imageView of the floor following thief.
     * @return the imageView.
     */
    public ImageView getffThief() {
        return ffThief;
    }

    /**
     * returns the tile colour the floor following thief follows.
     * @return the tile colour.
     */
    public String getTileColour() {
        return tileColour;
    }

    /**
     * returns the current direction for of the floor following thief.
     * @return the direction.
     */
    public String getStartDirection() {
        return nextDirection;
    }

    /**
     * Returns the stackPane of floor following thief.
     * @return the stackpane.
     */
    @Override
    public StackPane getStackPane() {
        return ffThiefStackPane;
    }

    /**
     * Returns the current coordinates of the floor following thief.
     * @return the current coordinates.
     */
    @Override
    public int[] getCoords() {
        return coordsFinal;
    }
}
