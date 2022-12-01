package com.example.cs230;

import javafx.scene.image.ImageView;

public class Coin {
    private static final String BRONZE_COIN_PATH = "CoinBronze.png";
    private static final String SILVER_COIN_PATH = "CoinSilver.png";
    private static final String GOLD_COIN_PATH = "CoinGold.png";
    private static final String PLAT_COIN_PATH = "CoinPlat.png";

    private static final int COIN_SIZE = 30;
    private ImageView bronzeCoin = new ImageView();
    private ImageView silverCoin = new ImageView();
    private ImageView goldCoin = new ImageView();
    private ImageView platCoin = new ImageView();


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
            case "SILVER":
                silverCoin = new ImageView(SILVER_COIN_PATH);
                silverCoin.setFitWidth(COIN_SIZE);
                silverCoin.setFitHeight(COIN_SIZE);
                break;
            case "GOLD":
                goldCoin = new ImageView(GOLD_COIN_PATH);
                goldCoin.setFitWidth(COIN_SIZE);
                goldCoin.setFitHeight(COIN_SIZE);
                break;
            case "PLAT":
                platCoin = new ImageView(PLAT_COIN_PATH);
                platCoin.setFitWidth(COIN_SIZE);
                platCoin.setFitHeight(COIN_SIZE);
                break;
        }
    }

    public ImageView getBronzeCoin() {return bronzeCoin;}
    public ImageView getSilverCoin() {return silverCoin;}
    public ImageView getGoldCoin() {return goldCoin;}
    public ImageView getPlatCoin() {return platCoin;}




}
