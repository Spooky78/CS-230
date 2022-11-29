package com.example.cs230;

import javafx.scene.image.ImageView;

public class Coin{
    private static final String BRONZE_COIN_PATH = "CoinBronze.png";
    private static final  int COIN_SIZE = 30;
    private ImageView bronzeCoin = new ImageView();


    public Coin(String cointype) {
        createItem(cointype)
        ;
    }
    protected void createItem(String cointype) {
        switch (cointype) {
            case "BRONZE":
                bronzeCoin = new ImageView(BRONZE_COIN_PATH);
                bronzeCoin.setFitWidth(COIN_SIZE);
                bronzeCoin.setFitHeight(COIN_SIZE);
                break;
        }
    }

    public ImageView getBronzeCoin() {
        return bronzeCoin;
    }


}
