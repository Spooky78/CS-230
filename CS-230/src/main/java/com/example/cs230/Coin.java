package com.example.cs230;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Coin {
    private static final String BRONZE_COIN_PATH = "CoinBronze.png";
    private static final String SILVER_COIN_PATH = "CoinSilver.png";
    private static final String GOLD_COIN_PATH = "CoinGold.png";
    private static final String PLAT_COIN_PATH = "CoinPlat.png";

    private static final int BRONZE_COIN_SCORE = 1;
    private static final int SILVER_COIN_SCORE = 5;
    private static final int GOLD_COIN_SCORE = 10;
    private static final int COIN_SIZE = 30;
    private StackPane coinStackPane = new StackPane();
    private Board gameBoard;
    private ImageView coin = new ImageView();

    public Coin(String cointype, Board board, int[] position) {
        gameBoard = board;
        createItem(cointype, position);
    }

    protected void createItem(String cointype, int[] position) {

        switch (cointype) {
            case "BRONZE":
                coin = new ImageView(BRONZE_COIN_PATH);
                break;
            case "SILVER":
                coin = new ImageView(SILVER_COIN_PATH);
                break;
            case "GOLD":
                coin = new ImageView(GOLD_COIN_PATH);
                break;
            case "PLAT":
                coin = new ImageView(PLAT_COIN_PATH);
                break;
        }
        coin.setFitWidth(COIN_SIZE);
        coin.setFitHeight(COIN_SIZE);
        coinStackPane.getChildren().add(coin);
        int coordX = position[0];
        int coordY = position[1];
        int tileSize = gameBoard.getTileSize();
        coinStackPane.setLayoutX((coordX * tileSize) - (tileSize / 2));
        coinStackPane.setLayoutY((coordY * tileSize) - (tileSize / 2));

    }


    public boolean isCollisionPlayer(int[] playerCoords) {

    return false;
    }

    public StackPane getCoinStackPane() {return coinStackPane;}

    public ImageView getCoin() {return coin;}

}
