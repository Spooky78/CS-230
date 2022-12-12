package com.example.cs230;

/**
 * Player profile.
 *
 * @author Rex
 */
public class PlayerProfile {
    private String name;
    private int unlockedLevel;

    /**
     * player profile with name and level unlocked.
     *
     * @param profileName profile name
     * @param unlockedLevelProfile unlocked level
     */
    public PlayerProfile(String profileName, int unlockedLevelProfile) {
        //saveName();
        this.name = profileName;
        this.unlockedLevel = unlockedLevelProfile;

    }

    /**
     * get player name.
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * get recent level unlocked.
     *
     * @return level unlocked
     */
    public int getIsLevelUnlocked() {
        return unlockedLevel;
    }

    /**
     * set level unlocked.
     *
     * @param levelIndex index of level
     */
    public void setLevelUnlocked(int levelIndex) {
        unlockedLevel = levelIndex;
    }
}
