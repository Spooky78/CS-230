package com.example.cs230;

import javafx.scene.layout.StackPane;

public abstract class Item {
    protected abstract StackPane getStackPane();
    protected abstract int[] getCoords();
}
