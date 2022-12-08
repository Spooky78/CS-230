package com.example.cs230;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;

public class SmartThief extends NPC {
    //no smart thief at the moment so just borrow one from flying assassin
    //can use reptile for smart thief now.
    private static final int MILLS_DELAY_TILE = 1000;
    private static final String SMARTTHIEF_DOWN_PATH = "/Reptile/ReptileDown.png";
    private static final String SMARTTHIEF_UP_PATH = "/Reptile/ReptileUp.png";
    private static final String SMARTTHIEF_LEFT_PATH = "/Reptile/ReptileLeft.png";
    private static final String SMARTTHIEF_RIGHT_PATH = "/Reptile/ReptileRight.png";
    private ImageView sThief;
    private int[] coords;
    private StackPane sThiefStackPane;
    private Board gameBoard;
    private GameViewManager manager;
    private Timer timer = new Timer();
    ArrayList<int[]> coordPath = new ArrayList<>();
    ArrayList<String> directions = new ArrayList<>();

    public SmartThief(Board board, int[] startCoords, StackPane stackPane, GameViewManager manager) {
        this.manager = manager;
        gameBoard = board;
        coords = startCoords;
        sThiefStackPane = stackPane;
        createNPC();
        move();
    }
    @Override
    public void stopTimer(){
        timer.cancel();
        timer.purge();
    }

    @Override
    protected void createNPC() {
        Image assassinImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(SMARTTHIEF_DOWN_PATH)));
        sThief = new ImageView(assassinImage);
        sThief.setFitWidth(50);
        sThief.setFitHeight(50);
        int tileSize = gameBoard.getTileSize();
        sThiefStackPane.setLayoutX((coords[0] * tileSize) - (tileSize / 2));
        sThiefStackPane.setLayoutY((coords[1] * tileSize) - (tileSize / 2));
    }

    ArrayList<String> searchedTile = new ArrayList<>();
    int[] targetCoords = null;
    boolean isFound = false;
    protected void move() {
        int shortestIndex = findShortestDistance(manager.getAllCollectableItems());
        targetCoords = manager.getAllCollectableItems().get(shortestIndex).getCoords();
        Node<int[]> root = new Node<>(coords);
        searchedTile.add(root.getData()[0]+""+root.getData()[1]);
        //System.out.println(root.getData()[0] + " "+root.getData()[1]);
        makeTree(root, root.getData());
        if (!isFound) {
            System.out.println("COUNLD NOT FIND ITEM");
        } else {
            System.out.println("FOUND ITEM");
            ArrayList<String> directions = findDirections();
            for (int i=0; i<coordPath.size(); i++) {
                System.out.println(coordPath.get(i)[0]+" "+coordPath.get(i)[1]);
                //System.out.println(directions.get(i));
            }
            transitions(reverseArrayList(directions));
        }
        //transitions(directions);
        //print(root, " ");
    }

    public ArrayList<String> reverseArrayList(ArrayList<String> alist) {
        // Arraylist for storing reversed elements
        ArrayList<String> revArrayList = new ArrayList<String>();
        for (int i = alist.size() - 1; i >= 0; i--) {
            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }
        // Return the reversed arraylist
        return revArrayList;
    }

    private int findShortestDistance(ArrayList<Item> items) {
        double shortestDistance = 1000;
        int shortestIndex = -1;
        for (int i=0; i<items.size(); i++) {
            int distanceX = coords[0] - items.get(i).getCoords()[0];
            int distanceY = coords[1] - items.get(i).getCoords()[1];
            double distanceTotal = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY, 2));
            if (shortestDistance > distanceTotal) {
                shortestDistance = distanceTotal;
                shortestIndex = i;
            }
        }
        //System.out.println(items.get(shortestIndex).getCoords()[0]+" "+ items.get(shortestIndex).getCoords()[1]);
        //System.out.println(shortestDistance);
        return shortestIndex;
    }

    private void makeTree(Node<int[]> parent, int[] parentCoords) {
        int[] offsetParents = new int[] {parentCoords[0]-1, parentCoords[1]-1};
            Node<int[]> rightChild = null;
            int[] rightChildCoords = new int[]{parentCoords[0] + 1, parentCoords[1]};
            int newXRight = parentCoords[0] + 1;
            String searchRight = newXRight+""+parentCoords[1];
            int[] offsetrightCoords = new int[]{parentCoords[0], parentCoords[1] - 1};
            if (!searchedTile.contains(searchRight) && gameBoard.canMove(offsetParents, offsetrightCoords)) {
                //System.out.println(parentCoords[0] + " " + parentCoords[1] + "   " + rightChildCoords[0] + " " + rightChildCoords[1]);
                rightChild = parent.addChild(new Node<>(rightChildCoords));
                searchedTile.add(searchRight);
                if (targetCoords[0] == rightChildCoords[0] && targetCoords[1] == rightChildCoords[1]) {
                    isFound = true;
                    findPathCoords(rightChild);
                    //System.out.println("FOND AT RIGHT" + rightChildCoords[0] + " " + rightChildCoords[1]);
                }
            }

            Node<int[]> leftChild = null;
            int[] leftChildCoords = new int[]{parentCoords[0] - 1, parentCoords[1]};
            int newXLeft = parentCoords[0] - 1;
            String searchLeft = newXLeft+""+parentCoords[1];
            int[] offsetLeftCoords = new int[]{parentCoords[0] - 2, parentCoords[1] - 1};
            if (!searchedTile.contains(searchLeft) && gameBoard.canMove(offsetParents, offsetLeftCoords)) {
                //System.out.println(parentCoords[0] + " " + parentCoords[1] + "   " + leftChildCoords[0] + " " + leftChildCoords[1]);
                leftChild = parent.addChild(new Node<>(leftChildCoords));
                searchedTile.add(searchLeft);
                if (targetCoords[0] == leftChildCoords[0] && targetCoords[1] == leftChildCoords[1]) {
                    isFound = true;
                    findPathCoords(leftChild);
                    //System.out.println("FOND AT LEFT" + leftChildCoords[0] + " " + leftChildCoords[1]);
                }
            }

            Node<int[]> upChild = null;
            int[] upChildCoords = new int[]{parentCoords[0], parentCoords[1] - 1};
            int newYUp = parentCoords[1] - 1;
            String searchUp = parentCoords[0]+""+newYUp;
            int[] offsetUpCoords = new int[]{parentCoords[0] - 1, parentCoords[1] - 2};
            if (!searchedTile.contains(searchUp) && gameBoard.canMove(offsetParents, offsetUpCoords)) {
                //System.out.println(parentCoords[0] + " " + parentCoords[1] + "   " + upChildCoords[0] + " " + upChildCoords[1]);
                upChild = parent.addChild(new Node<>(upChildCoords));
                searchedTile.add(searchUp);
                if (targetCoords[0] == upChildCoords[0] && targetCoords[1] == upChildCoords[1]) {
                    isFound = true;
                    findPathCoords(upChild);
                    //System.out.println("FOND AT UP" + upChildCoords[0] + " " + upChildCoords[1]);
                }
            }

            Node<int[]> downChild = null;
            int[] downChildCoords = new int[]{parentCoords[0], parentCoords[1] + 1};
            int newYDown = parentCoords[1] + 1;
            String searchDown = parentCoords[0]+""+newYDown;
            int[] offsetDownCoods = new int[]{parentCoords[0] - 1, parentCoords[1]};
            if (!searchedTile.contains(searchDown) && gameBoard.canMove(offsetParents, offsetDownCoods)) {
                //System.out.println(parentCoords[0] + " " + parentCoords[1] + "   " + downChildCoords[0] + " " + downChildCoords[1]);
                downChild = parent.addChild(new Node<>(downChildCoords));
                searchedTile.add(searchDown);
                if (targetCoords[0] == downChildCoords[0] && targetCoords[1] == downChildCoords[1]) {
                    isFound = true;
                    findPathCoords(downChild);
                    //System.out.println("FOND AT DOWN" + downChildCoords[0] + " " + downChildCoords[1]);
                }
            }
            boolean outsideRight = false;
        boolean outsideLeft = false;
        boolean outsideUp = false;
        boolean outsideDown = false;
        if (parentCoords[0] <= 1) {
            outsideLeft = true;
            //System.out.println("OUTSIDE GRID LEFT");
        }
        if (parentCoords[0] >=9) {
            outsideRight = true;
            //System.out.println("OUTSIDE GRID RIGHT");
        }
        if (parentCoords[1] <= 2) {
            outsideUp = true;
            //System.out.println("OUTSIDE GRID UP");
        }
        if (parentCoords[1] >= 8) {
            outsideDown = true;
            //System.out.println("OUTSIDE GRID DOWN");
        }

        if (!isFound) {
            if (upChild != null && !outsideUp) {
                makeTree(upChild, upChildCoords);
            }
            if (downChild != null && !outsideDown) {
                makeTree(downChild, downChildCoords);
            }
            if (rightChild != null && !outsideRight) {
                makeTree(rightChild, rightChildCoords);
            }
            if (leftChild != null && !outsideLeft) {
                makeTree(leftChild, leftChildCoords);
            }
        }
    }
    private void print(Node<int[]> node, String appender) {
        System.out.println(appender + node.getData()[0] + " " + node.getData()[1]);
        node.getChildren().forEach(each -> print(each, appender+appender));
    }
    private void findPathCoords(Node<int[]> foundNode) {
        Node<int[]> currentNode = foundNode;
        while (!(currentNode.getData()[0] == coords[0]) || !(currentNode.getData()[1] == coords[1])) {
            coordPath.add(currentNode.getData());
            currentNode = currentNode.getParent();
        }
        coordPath.add(new int[] {5, 5});
    }

    private ArrayList<String> findDirections() {
        ArrayList<String> directions = new ArrayList<>();
        for(int i=0; i<coordPath.size()-1; i++) {
            if (coordPath.get(i)[0] != coordPath.get(i+1)[0]) {
                int sub = coordPath.get(i)[0] - coordPath.get(i+1)[0];
                if (sub < 0) {
                    directions.add("LEFT");
                } else if (sub > 0) {
                    directions.add("RIGHT");
                }
            } else if (coordPath.get(i)[1] != coordPath.get(i+1)[1]) {
                int sub = coordPath.get(i)[1] - coordPath.get(i+1)[1];
                if (sub < 0) {
                    directions.add("UP");
                } else if (sub > 0) {
                    directions.add("DOWN");
                }
            }
        }
        return directions;
    }

    private void transitions(ArrayList<String> directions) {
        SequentialTransition transition = new SequentialTransition();
        System.out.println(directions.size());
        for(int i=0; i<directions.size(); i++) {
            System.out.println(directions.get(i));
            //transition.getChildren().add(moveDownTile());
            if (directions.get(i).equals("RIGHT")) {
                transition.getChildren().add(moveRightTile());
            }
            if (directions.get(i).equals("LEFT")) {
                transition.getChildren().add(moveLeftTile());
            }
            if (directions.get(i).equals("UP")) {
                transition.getChildren().add(moveUpTile());
            }
            if (directions.get(i).equals("DOWN")) {
                transition.getChildren().add(moveDownTile());
            }
        }
        transition.play();
    }

    private TranslateTransition moveRightTile() {
        TranslateTransition moveRight = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveRight.setNode(sThief);
        moveRight.setByX(gameBoard.getTileSize());
        //animationCoords[0] += 1;
        return moveRight;
    }

    private TranslateTransition moveLeftTile() {
        TranslateTransition moveLeft = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveLeft.setNode(sThief);
        moveLeft.setByX(-gameBoard.getTileSize());
        //animationCoords[0] -= 1;
        return moveLeft;
    }

    private TranslateTransition moveDownTile() {
        TranslateTransition moveDown = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveDown.setNode(sThief);
        moveDown.setByY(gameBoard.getTileSize());
        //animationCoords[1] += 1;
        return moveDown;
    }

    private TranslateTransition moveUpTile() {
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(MILLS_DELAY_TILE));
        moveUp.setNode(sThief);
        moveUp.setByY(-gameBoard.getTileSize());
        //animationCoords[1] -= 1;
        return moveUp;
    }

    @Override
    protected StackPane getStackPane() {
        return sThiefStackPane;
    }

    @Override
    public int[] getCoords() {
        return coords;
    }

    public ImageView getSmartThief() {
        return sThief;
    }
}
