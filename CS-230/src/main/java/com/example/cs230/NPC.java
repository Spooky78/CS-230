package com.example.cs230;

import javafx.scene.layout.StackPane;

/**
 * All NPCs inherit from this class. It helps them find their coordinates, move,
 * be drawn to the screen, and interact with the timer.
 *
 * @author Vic
 */
public abstract class NPC {
    protected abstract void createNPC();

    protected abstract void move();

    protected abstract StackPane getStackPane();

    protected abstract int[] getCoords();

    protected abstract void stopTimer();
}
