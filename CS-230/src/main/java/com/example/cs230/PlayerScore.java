package com.example.cs230;

import java.util.ArrayList;

public class PlayerScore {
    private final int MAX_LEVEL = 5;
    private String playerName;
    private final int DEFAULT_SCORE = 0;
    private ArrayList<Integer> playerScore = new ArrayList<>();
    public PlayerScore(String name, ArrayList<Integer> currentPlayerScore) {
        playerName = name;
        playerScore = currentPlayerScore;
        //this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScoreSize() {
        return playerScore.size();
    }
    public int getPlayerScoreAtLevel(int levelIndex) {
        return playerScore.get(levelIndex);
    }

    public void setPlayerScore(int score, int levelIndex) {
        playerScore.set(levelIndex,score);
    }
}
