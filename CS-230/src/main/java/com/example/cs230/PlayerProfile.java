package com.example.cs230;

public class PlayerProfile {
    private int profileIndex;
    private String name;
    private boolean[]isLevelUnlocked;
    public PlayerProfile(String name, boolean[]isLevelUnlocked) {
        //saveName();
        this.name = name;
        this.isLevelUnlocked = isLevelUnlocked;

    }
    // the player profile now store the boolean list of levels.
    // when reading the file it reads the level as well as the player name
    // when adding the name it has default level which is first level unlocked and the others not unlocked.
    //TODO: high score tables take the level.


    public String getName() {
        return name;
    }

    public boolean[] getIsLevelUnlocked() {
        return isLevelUnlocked;
    }

    public void changeLevelIndex(int levelIndex) {
        isLevelUnlocked[levelIndex] = true;
    }
}
