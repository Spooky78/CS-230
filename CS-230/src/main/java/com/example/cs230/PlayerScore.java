package com.example.cs230;

public class PlayerScore {
    private String playerName;
    private int playerScore;
    public PlayerScore(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }
}
