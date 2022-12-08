package com.example.cs230;

import javafx.scene.layout.StackPane;

public abstract class NPC {
    protected abstract void createNPC();

    protected abstract void move();
    protected abstract StackPane getStackPane();
    protected abstract int[] getCoords();
    protected abstract void stopTimer();
}
