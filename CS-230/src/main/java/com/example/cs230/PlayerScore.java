package com.example.cs230;

import java.util.ArrayList;

/**
 * Class that manages player scores by associating them with the correct player.
 *
 * @author Rex
 */
public class PlayerScore {
    private String playerName;
    private final int DEFAULT_SCORE = 0;
    private ArrayList<Integer> playerScore = new ArrayList<>();

    /**
     * Associates player name with the player's score.
     *
     * @param name               player name
     * @param currentPlayerScore player's current score
     */
    public PlayerScore(String name, ArrayList<Integer> currentPlayerScore) {
        playerName = name;
        playerScore = currentPlayerScore;
    }

    /**
     * gets player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * get player score.
     *
     * @return player score
     */
    public ArrayList<Integer> getPlayerScore() {
        return playerScore;
    }

    /**
     * gets the size of the player's score.
     *
     * @return the size of the player's score
     */
    public int getPlayerScoreSize() {
        return playerScore.size();
    }

    /**
     * gets the player's score of a specific level.
     *
     * @param levelIndex the index of the level
     * @return the player's score of the specified level.
     */
    public int getPlayerScoreAtLevel(int levelIndex) {
        return playerScore.get(levelIndex);
    }

    /**
     * adds the level score to the storage of level scores.
     */
    public void addALevelScore() {
        playerScore.add(DEFAULT_SCORE);
    }

    /**
     * sets the player's score of a specific level.
     *
     * @param levelIndex the index of the level
     * @param score      the player's score of the specified level
     */
    public void setPlayerScore(int levelIndex, int score) {
        playerScore.set(levelIndex, score);
    }
}
