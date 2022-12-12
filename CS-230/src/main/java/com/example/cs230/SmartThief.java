package com.example.cs230;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.*;

/**
 * Responsible for smart thief.
 */
public class SmartThief extends NPC {
    //no smart thief at the moment so just borrow one from flying assassin
    //can use reptile for smart thief now.
    private static final int MILLS_DELAY_TILE = 2000;
    private static final String SMARTTHIEF_DOWN_PATH = "/Reptile/ReptileDown.png";
    private static final String SMARTTHIEF_UP_PATH = "/Reptile/ReptileUp.png";
    private static final String SMARTTHIEF_LEFT_PATH = "/Reptile/ReptileLeft.png";
    private static final String SMARTTHIEF_RIGHT_PATH = "/Reptile/ReptileRight.png";
    private static final int IMAGE_SIZE = 50;
    private static final int DEFAULT_SHORTEST_DISTANCE = 1000;

    private ImageView sThief;
    private int[] coords;
    private StackPane sThiefStackPane;
    private Board gameBoard;
    private GameViewManager manager;
    private Timer timer = new Timer();
    ArrayList<int[]> coordPath = new ArrayList<>();
    ArrayList<String> searchedTile = new ArrayList<>();
    ArrayList<Item> allCollectableItems = new ArrayList<>();
    int[] targetCoords = null;
    boolean isFound = false;
    private boolean isCollected = false;
    private boolean isReachedDoor = false;
    private List<Node<int[]>> treeQueue = new ArrayList<>();

    /**
     * Constructor for smart thief.
     * @param board the board.
     * @param startCoords the start coords.
     * @param stackPane the stackpane.
     * @param manager the game manager.
     */
    public SmartThief(Board board, int[] startCoords, StackPane stackPane,
                      GameViewManager manager) {
        this.manager = manager;
        gameBoard = board;
        coords = startCoords;
        sThiefStackPane = stackPane;
        createNPC();
        setAllCollectableItems(manager.getAllCollectableItems());
        move();
    }

    /**
     * stops timer.
     */
    @Override
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * creates smart thief.
     */
    @Override
    protected void createNPC() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(SMARTTHIEF_DOWN_PATH)));
        sThief = new ImageView(assassinImage);
        sThief.setFitWidth(IMAGE_SIZE);
        sThief.setFitHeight(IMAGE_SIZE);
        int tileSize = gameBoard.getTileSize();
        sThiefStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        sThiefStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    /**
     * stes all collectable items.
     * @param items all items.
     */
    public void setAllCollectableItems(ArrayList<Item> items) {
        allCollectableItems = items;
    }

    /**
     * move smart thief.
     */
    public void move() {
        isCollected = false;
        isFound = false;
        int shortestIndex = findShortestDistance(allCollectableItems);
        targetCoords = allCollectableItems.get(shortestIndex).getCoords();
        Node<int[]> root = new Node<>(coords);
        searchedTile.clear();
        searchedTile.add(root.getData()[0] + "" + root.getData()[1]);
        treeQueue.clear();
        treeQueue.add(root);
        makeTree();
        if (!isFound) {
            if (manager.getDoor().getCoords()[0] == coords[0]
                    && manager.getDoor().getCoords()[1] == coords[1]) {
                isReachedDoor = true;
            } else {
                isCollected = true;
            }
        } else {
            System.out.println("FOUND ITEM");
            ArrayList<String> directions = findDirections();
            animation(reverseArrayList(directions));
        }
    }

    /**
     * reverses an arraylist.
     * @param inputList input array.
     * @return the reversed array.
     */
    public ArrayList<String> reverseArrayList(ArrayList<String> inputList) {
        ArrayList<String> revArrayList = new ArrayList<>();
        for (int i = inputList.size() - 1; i >= 0; i--) {
            revArrayList.add(inputList.get(i));
        }
        return revArrayList;
    }

    /**
     * finds index of the closest item.
     * @param items all items
     * @return the index
     */
    private int findShortestDistance(ArrayList<Item> items) {
        double shortestDistance = DEFAULT_SHORTEST_DISTANCE;
        int shortestIndex = -1;
        if (items.size() == 1 && items.get(0).getClass() == Door.class) {
            return 0;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getClass() != Door.class) {
                int distanceX = coords[0] - items.get(i).getCoords()[0];
                int distanceY = coords[1] - items.get(i).getCoords()[1];
                double distanceTotal = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                if (shortestDistance > distanceTotal) {
                    shortestDistance = distanceTotal;
                    shortestIndex = i;
                }
            }
        }
        return shortestIndex;
    }

    /**
     * makes tree until it reaches wanted tile or searches whole grid.
     */
    private void makeTree() {
        while (treeQueue.size() > 0 && !isFound) {
            Node<int[]> parent = treeQueue.get(0);
            treeQueue.remove(0);
            int[] parentCoords = parent.getData();

            Node<int[]> rightChild = null;
            int[] rightChildCoords = new int[]{parentCoords[0] + 1, parentCoords[1]};
            int newXRight = parentCoords[0] + 1;
            String searchRight = newXRight + "" + parentCoords[1];
            if (rightChildCoords[0] < gameBoard.getBoardSizeX()) {
                if (!searchedTile.contains(searchRight)
                        && gameBoard.canMoveNPC(parentCoords, rightChildCoords)
                        && !manager.checkNonSteppableTile(rightChildCoords)) {
                    rightChild = parent.addChild(new Node<>(rightChildCoords));
                    searchedTile.add(searchRight);
                    treeQueue.add(rightChild);
                    if (targetCoords[0] == rightChildCoords[0]
                            && targetCoords[1] == rightChildCoords[1]) {
                        isFound = true;
                        findPathCoords(rightChild);
                    }
                }
            }

            Node<int[]> downChild = null;
            int[] downChildCoords = new int[]{parentCoords[0], parentCoords[1] + 1};
            int newYDown = parentCoords[1] + 1;
            String searchDown = parentCoords[0] + "" + newYDown;
            if (downChildCoords[1] < gameBoard.getBoardSizeY()) {
                if (!searchedTile.contains(searchDown)
                        && gameBoard.canMoveNPC(parentCoords, downChildCoords)
                        && !manager.checkNonSteppableTile(downChildCoords)) {
                    downChild = parent.addChild(new Node<>(downChildCoords));
                    searchedTile.add(searchDown);
                    treeQueue.add(downChild);
                    if (targetCoords[0] == downChildCoords[0]
                            && targetCoords[1] == downChildCoords[1]) {
                        isFound = true;
                        findPathCoords(downChild);
                    }
                }
            }

            Node<int[]> leftChild = null;
            int[] leftChildCoords = new int[]{parentCoords[0] - 1, parentCoords[1]};
            int newXLeft = parentCoords[0] - 1;
            String searchLeft = newXLeft + "" + parentCoords[1];
            if (leftChildCoords[0] > 1) {
                if (!searchedTile.contains(searchLeft)
                        && gameBoard.canMoveNPC(parentCoords, leftChildCoords)
                        && !manager.checkNonSteppableTile(leftChildCoords)) {
                    leftChild = parent.addChild(new Node<>(leftChildCoords));
                    searchedTile.add(searchLeft);
                    treeQueue.add(leftChild);
                    if (targetCoords[0] == leftChildCoords[0]
                            && targetCoords[1] == leftChildCoords[1]) {
                        isFound = true;
                        findPathCoords(leftChild);
                    }
                }
            }

            Node<int[]> upChild = null;
            int[] upChildCoords = new int[]{parentCoords[0], parentCoords[1] - 1};
            int newYUp = parentCoords[1] - 1;
            String searchUp = parentCoords[0] + "" + newYUp;
            if (upChildCoords[1] > 1) {
                if (!searchedTile.contains(searchUp)
                        && gameBoard.canMoveNPC(parentCoords, upChildCoords)
                        && !manager.checkNonSteppableTile(upChildCoords)) {
                    upChild = parent.addChild(new Node<>(upChildCoords));
                    searchedTile.add(searchUp);
                    treeQueue.add(upChild);
                    if (targetCoords[0] == upChildCoords[0]
                            && targetCoords[1] == upChildCoords[1]) {
                        isFound = true;
                        findPathCoords(upChild);
                    }
                }
            }

            boolean outsideRight = false;
            boolean outsideLeft = false;
            boolean outsideUp = false;
            boolean outsideDown = false;
            if (parentCoords[0] <= 1) {
                outsideLeft = true;
            }
            if (parentCoords[0] > gameBoard.getBoardSizeX() - 2) {
                outsideRight = true;
            }
            if (parentCoords[1] <= 2) {
                outsideUp = true;
            }
            if (parentCoords[1] > gameBoard.getBoardSizeY() - 2) {
                outsideDown = true;
            }

            if (!isFound) {
                if (upChild != null && !outsideUp) {
                    makeTree();
                }
                if (downChild != null && !outsideDown) {
                    makeTree();
                }
                if (rightChild != null && !outsideRight) {
                    makeTree();
                }
                if (leftChild != null && !outsideLeft) {
                    makeTree();
                }
            }
        }
    }

    /**
     * finds path from target node to root and puts in arraylist.
     * @param foundNode the target node.
     */
    private void findPathCoords(Node<int[]> foundNode) {
        coordPath.clear();
        Node<int[]> currentNode = foundNode;
        while (!(currentNode.getData()[0] == coords[0])
                || !(currentNode.getData()[1] == coords[1])) {
            coordPath.add(currentNode.getData());
            currentNode = currentNode.getParent();
        }
        coordPath.add(new int[] {coords[0], coords[1]});
    }

    /**
     * finds the directions.
     * @return the arraylist of directions.
     */
    private ArrayList<String> findDirections() {
        ArrayList<String> directions = new ArrayList<>();
        for (int i = 0; i < coordPath.size() - 1; i++) {
            if (coordPath.get(i)[0] != coordPath.get(i + 1)[0]) {
                int sub = coordPath.get(i)[0] - coordPath.get(i + 1)[0];
                if (sub < 0) {
                    directions.add("LEFT");
                } else if (sub > 0) {
                    directions.add("RIGHT");
                }
            } else if (coordPath.get(i)[1] != coordPath.get(i + 1)[1]) {
                int sub = coordPath.get(i)[1] - coordPath.get(i + 1)[1];
                if (sub < 0) {
                    directions.add("UP");
                } else if (sub > 0) {
                    directions.add("DOWN");
                }
            }
        }
        return directions;
    }

    /**
     * makes sequential transition from the directions.
     * @param directions the directions
     * @return the sequential transition.
     */
    private SequentialTransition transitions(ArrayList<String> directions) {
        SequentialTransition transition = new SequentialTransition();
        for (int i = 0; i < directions.size(); i++) {
            if (directions.get(i).equals("RIGHT")) {
                transition.getChildren().add(moveRightTile());
            } else if (directions.get(i).equals("LEFT")) {
                transition.getChildren().add(moveLeftTile());
            } else if (directions.get(i).equals("UP")) {
                transition.getChildren().add(moveUpTile());
            } else if (directions.get(i).equals("DOWN")) {
                transition.getChildren().add(moveDownTile());
            }
        }
        return transition;
    }

    /**
     * move across tile to the right.
     * @return the transition
     */
    private TranslateTransition moveRightTile() {
        TranslateTransition moveRight = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveRight.setNode(sThief);
        moveRight.setByX(gameBoard.getTileSize());
        return moveRight;
    }

    /**
     * move across tile to the left.
     * @return the transition
     */
    private TranslateTransition moveLeftTile() {
        TranslateTransition moveLeft = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveLeft.setNode(sThief);
        moveLeft.setByX(-gameBoard.getTileSize());
        return moveLeft;
    }

    /**
     * move across tile to the down.
     * @return the transition
     */
    private TranslateTransition moveDownTile() {
        TranslateTransition moveDown = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveDown.setNode(sThief);
        moveDown.setByY(gameBoard.getTileSize());
        return moveDown;
    }

    /**
     * move across tile to the up.
     * @return the transition
     */
    private TranslateTransition moveUpTile() {
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveUp.setNode(sThief);
        moveUp.setByY(-gameBoard.getTileSize());
        //animationCoords[1] -= 1;
        return moveUp;
    }

    /**
     * does the coord and images transitions.
     * @param directions the directions
     */
    private void animation(ArrayList<String> directions) {
        SequentialTransition animation = transitions(directions);
        timer = new Timer();
        int schedualCount = 1;

        for (int i = 0; i < directions.size(); i++) {
            if (directions.get(i).equals("RIGHT")) {
                moveRightTileAnimation(timer, schedualCount, animation, i, directions.size());
            } else if (directions.get(i).equals("LEFT")) {
                moveLeftTileAnimation(timer, schedualCount, animation, i, directions.size());
            } else if (directions.get(i).equals("UP")) {
                moveUpTileAnimation(timer, schedualCount, animation, i, directions.size());
            } else if (directions.get(i).equals("DOWN")) {
                moveDownTileAimation(timer, schedualCount, animation, i, directions.size());
            }
            schedualCount++;
        }
        animation.play();
    }

    /**
     * sets coords and image for right direction.
     * @param timer timer
     * @param scheduleCount schedule count
     * @param animation sequential transition.
     * @param cycle cycle count
     * @param totalCycle total cycles
     */
    private void moveRightTileAnimation(Timer timer, int scheduleCount,
                                        SequentialTransition animation,
                                        int cycle, int totalCycle) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                animation.pause();
                setImage("DOWN");
                coords[0] += 1;
                animation.playFrom(String.valueOf(MILLS_DELAY_TILE * scheduleCount));
                if (cycle == totalCycle - 1) {
                    isCollected = true;
                    stopTimer();
                }
            }
        };
        timer.schedule(task, (long) MILLS_DELAY_TILE * scheduleCount);

    }

    /**
     * sets coords and image for left direction.
     * @param timer timer
     * @param scheduleCount schedule count
     * @param animation sequential transition.
     * @param cycle cycle count
     * @param totalCycle total cycles
     */
    private void moveLeftTileAnimation(Timer timer, int scheduleCount,
                                       SequentialTransition animation,
                                       int cycle, int totalCycle) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                animation.pause();
                setImage("DOWN");
                coords[0] -= 1;
                animation.playFrom(String.valueOf(MILLS_DELAY_TILE * scheduleCount));
                if (cycle == totalCycle - 1) {
                    isCollected = true;
                    stopTimer();
                }
            }
        };
        timer.schedule(task, (long) MILLS_DELAY_TILE * scheduleCount);
    }

    /**
     * sets coords and image for up direction.
     * @param timer timer
     * @param scheduleCount schedule count
     * @param animation sequential transition.
     * @param cycle cycle count
     * @param totalCycle total cycles
     */
    private void moveUpTileAnimation(Timer timer, int scheduleCount,
                                     SequentialTransition animation,
                                     int cycle, int totalCycle) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                animation.pause();
                setImage("DOWN");
                coords[1] -= 1;
                animation.playFrom(String.valueOf(MILLS_DELAY_TILE * scheduleCount));
                if (cycle == totalCycle - 1) {
                    isCollected = true;
                    stopTimer();
                }
            }
        };
        timer.schedule(task, (long) MILLS_DELAY_TILE * scheduleCount);
    }

    /**
     * sets coords and image for down direction.
     * @param timer timer
     * @param scheduleCount schedule count
     * @param animation sequential transition.
     * @param cycle cycle count
     * @param totalCycle total cycles
     */
    private void moveDownTileAimation(Timer timer, int scheduleCount,
                                      SequentialTransition animation,
                                      int cycle, int totalCycle) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                animation.pause();
                setImage("DOWN");
                coords[1] += 1;
                animation.playFrom(String.valueOf(MILLS_DELAY_TILE * scheduleCount));
                if (cycle == totalCycle - 1) {
                    isCollected = true;
                    stopTimer();
                }
            }
        };
        timer.schedule(task, (long) MILLS_DELAY_TILE * scheduleCount);
    }

    /**
     * sets image according to direction.
     * @param direction the direction
     */
    private void setImage(String direction) {
        Image ffThiefImage = null;
        switch (direction) {
            case "LEFT":
                ffThiefImage = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(SMARTTHIEF_LEFT_PATH)));
                break;
            case "RIGHT":
                ffThiefImage = new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(SMARTTHIEF_RIGHT_PATH)));
                break;
            case "UP":
                ffThiefImage = new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(SMARTTHIEF_UP_PATH)));
                break;
            default:
                ffThiefImage = new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(SMARTTHIEF_DOWN_PATH)));
                break;
        };
        sThief.setImage(ffThiefImage);
        sThief.setFitWidth(IMAGE_SIZE);
        sThief.setFitHeight(IMAGE_SIZE);
    }

    /**
     * checks if item is collected.
     * @return if collected item.
     */
    public boolean isCollected() {
        return isCollected;
    }

    /**
     * checks if door id reached.
     * @return if reacked door.
     */
    public boolean isReachedDoor() {
        return isReachedDoor;
    }

    /**
     * gets stack pane.
     * @return the stackpane
     */
    @Override
    protected StackPane getStackPane() {
        return sThiefStackPane;
    }

    /**
     * gets coords.
     * @return the coords
     */
    @Override
    public int[] getCoords() {
        return coords;
    }

    /**
     * gets image view.
     * @return the image view
     */
    public ImageView getSmartThief() {
        return sThief;
    }
}
