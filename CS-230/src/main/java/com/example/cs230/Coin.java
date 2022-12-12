package com.example.cs230;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * Creates coins of different values on the board that can be collected by player and thieves,
 * and can be destroyed by bomb.
 *
 * @author Omar
 */
public class Coin extends Item {
    private static final String BRONZE_COIN_PATH = "/Items/CoinBronze.png";
    private static final String SILVER_COIN_PATH = "/Items/CoinSilver.png";
    private static final String GOLD_COIN_PATH = "/Items/CoinGold.png";
    private static final String PLAT_COIN_PATH = "/Items/CoinPlat.png";

    private static final int BRONZE_COIN_SCORE = 1;
    private static final int SILVER_COIN_SCORE = 3;
    private static final int GOLD_COIN_SCORE = 5;
    private static final int PLAT_COIN_SCORE = 10;
    private static final int COIN_SIZE = 30;
    private StackPane coinStackPane = new StackPane();
    private Board gameBoard;
    private ImageView coin = new ImageView();
    private int[] coinPosition;
    private int coinScore;
    public String coinType;

    /**
     * game loading and add coin on the game screen.
     *
     * @param coinType value of the coin
     * @param board    game screen
     * @param position coin position
     */
    public Coin(String coinType, Board board, int[] position) {
        gameBoard = board;
        coinPosition = position;
        this.coinType = coinType;
        createItem(this.coinType, position);
    }

    /**
     * create coins images and put these on the board,when collision by player,
     * adding scores depends on value of coins.
     *
     * @param coinType value of the coin
     * @param position coin position
     */
    protected void createItem(String coinType, int[] position) {

        switch (coinType) {
            case "BRONZE" -> {
                Image bronzeImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(BRONZE_COIN_PATH)));
                coin = new ImageView(bronzeImage);
                coinScore = BRONZE_COIN_SCORE;
            }
            case "SILVER" -> {
                Image silverImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(SILVER_COIN_PATH)));
                coin = new ImageView(silverImage);
                coinScore = SILVER_COIN_SCORE;
            }
            case "GOLD" -> {
                Image goldImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(GOLD_COIN_PATH)));
                coin = new ImageView(goldImage);
                coinScore = GOLD_COIN_SCORE;
            }
            case "PLAT" -> {
                Image platImage = new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream(PLAT_COIN_PATH)));
                coin = new ImageView(platImage);
                coinScore = PLAT_COIN_SCORE;
            }
        }
            coin.setFitWidth(COIN_SIZE);
            coin.setFitHeight(COIN_SIZE);
            coinStackPane.getChildren().add(coin);
            int tileSize = gameBoard.getTileSize();
            coinStackPane.setLayoutX((position[0] * tileSize) - (tileSize / 2));
            coinStackPane.setLayoutY((position[1] * tileSize) - (tileSize / 2));
        }

        /**
         * judgement player collided coin or not.
         *
         * @param playerCoords player position
         * @return true when player collision with coin
         */
        public boolean isCollisionPlayer(int[] playerCoords) {
            return playerCoords[0] + 1 == coinPosition[0] && playerCoords[1] + 1 == coinPosition[1];
        }

        /**
         * judgement npc collided coin or not.
         *
         * @param npcCoords npc position
         * @return true when npc collision with coin
         */
        public boolean isCollisionNPC(int[] npcCoords) {
            return npcCoords[0] == coinPosition[0] && npcCoords[1] == coinPosition[1];
        }

        /**
         * get coin pane.
         *
         * @return coin pane
         */
        public StackPane getCoinStackPane() {
            return coinStackPane;
        }

        /**
         * get coin image.
         *
         * @return coin image
         */
        public ImageView getCoin() {
            return coin;
        }

        /**
         * get coin score for different value.
         *
         * @return coin score for different value
         */
        public int getCoinScore() {
            return coinScore;
        }

        /**
         * get stack pane.
         *
         * @return stack pane
         */
        @Override
        protected StackPane getStackPane() {
            return coinStackPane;
        }

        /**
         * get coin position.
         *
         * @return coin position
         */
        @Override
        protected int[] getCoords() {
            return coinPosition;
        }

        /**
         * get coin type.
         *
         * @return coin type
         */
        public String getCoinType() {
            return coinType;
        }
    }
