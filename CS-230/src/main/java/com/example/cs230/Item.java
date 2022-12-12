package com.example.cs230;

import javafx.scene.layout.StackPane;

/**
 * All item classes inherit from this class. They can use it to get their coordinates
 * on the board and to help draw their image to the board.
 *
 * @author Vic
 */
public abstract class Item {
    protected abstract StackPane getStackPane();

    protected abstract int[] getCoords();
}
