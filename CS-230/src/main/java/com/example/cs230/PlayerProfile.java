package com.example.cs230;

public class PlayerProfile {
    private int profileIndex;
    private String name;
    private int unlockedLevel;
    public PlayerProfile(String name, int unlockedLevel) {
        //saveName();
        this.name = name;
        this.unlockedLevel = unlockedLevel;

    }
    // the player profile now store the boolean list of levels.
    // when reading the file it reads the level as well as the player name
    // when adding the name it has default level which is first level unlocked and the others not unlocked.
    //TODO: high score tables take the level.


    public String getName() {
        return name;
    }

    public int getIsLevelUnlocked() {
        return unlockedLevel;
    }

    public void setLevelUnlocked(int levelIndex) {
        unlockedLevel = levelIndex;
    }
}
