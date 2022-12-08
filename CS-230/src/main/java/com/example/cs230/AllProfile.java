package com.example.cs230;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class AllProfile {
    //score in list of levels
    private static final String PROFILE_FILE_PATH = "CS-230/src/main/resources/Levels/Profile.txt";
    private static final String SCORE_FILE_PATH = "CS-230/src/main/resources/Levels/Score.txt";
    private static ArrayList<PlayerProfile> allProfile = new ArrayList<>();
    private static ArrayList<PlayerScore> allScores = new ArrayList<>();
    private static ArrayList<String> nameList = new ArrayList<>();
    private static ArrayList<Integer> scoreList = new ArrayList<>();
    private ArrayList<Integer> sortedScoreList = new ArrayList<>();
    private ArrayList<Integer> allProfileIndex = new ArrayList<>();
    private int[] tenHighScore = new int[10];
    private String[] tenHighScoreName = new String[10];
    private final int MAX_HIGHSCORE_SIZE = 10;

    //When the game started, it loads all the profile in the file path.
    public static void loadProfile() {
        String name;
        int profileIndex = 0;
        try {
            File playerProfile = new File(PROFILE_FILE_PATH);
            Scanner playerProfileReader = new Scanner(playerProfile);
            while (playerProfileReader.hasNextLine()) {
                name = playerProfileReader.nextLine();
                allProfile.add(new PlayerProfile(profileIndex, name));
                nameList.add(name);
                profileIndex++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadScore() {
        String playerName;
        int playerScore = 0;
        try {
            File playerScoreFile = new File(SCORE_FILE_PATH);
            Scanner playerScoreReader = new Scanner(playerScoreFile);
            while (playerScoreReader.hasNextLine()) {
                playerName = playerScoreReader.nextLine();
                allScores.add(new PlayerScore(playerName, playerScore));
                scoreList.add(playerScore);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //if not existed, add a name as well as the profile index
    public static void addName(String name) {
        int profileIndex = 0;
        //check if the name is existed
        if (!nameList.contains(name)) {
            for (int i = 0; i < allProfile.size(); i++) {
                //checks if the name existed in one of the player profile
                //if it exists, does not add to the name list.
                profileIndex++;
            }
            //if not exists, add a new name.
            allProfile.add(new PlayerProfile(profileIndex, name));
            nameList.add(name);
        } else if (nameList.contains(name)) {
            System.out.println("Sorry, the name has been created by someone else.");
        } else {
            System.out.println("Sorry, there seems to be some Errors.");
        }
        writeProfileInTxt();
    }

    public void addScore(String playerName, int playerScore) {
        allScores.add(new PlayerScore(playerName, playerScore));
        scoreList.add(playerScore);
    }

    //return string array list of names to the game panel
    public static ArrayList<String> getAllNamesInProfiles() {
        return nameList;
    }

    //TODO: create a deleting profile button and push.
    public void deleteProfile(String name) {
        for (int i = 0; i < allProfile.size(); i++) {
            if (name.equals(allProfile.get(i).getName())) {
                allProfile.remove(i);
                nameList.remove(name);
            }
            writeProfileInTxt();
        }
        for (int i = 0; i < allScores.size(); i++) {
            if (name.equals(allScores.get(i).getPlayerName())) {
                allScores.remove(i);
                scoreList.remove(i);
            }
        }
    }

    // a method to rewrite every profile
    // when adding/deleting the method, writeTxt has to be run so the txt of the profile have all the profile names.
    public static void writeProfileInTxt() {
        try {
            FileWriter playerProfile = new FileWriter(PROFILE_FILE_PATH);
            for (int i = 0; i < allProfile.size(); i++) {
                playerProfile.write(allProfile.get(i).getName() + "\n");
            }
            playerProfile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeScoreInTxt() {
        try {
            FileWriter playerScores = new FileWriter(SCORE_FILE_PATH);
            for (int i = 0; i < allScores.size(); i++) {
                playerScores.write(allScores.get(i).getPlayerName()+ " ");
                playerScores.write(allScores.get(i).getPlayerScore() + "\n");
            }
            playerScores.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAndSortScoreList() {
        sortedScoreList = scoreList;
        Collections.sort(sortedScoreList, Collections.reverseOrder());
    }

    public int[] getTenHighScore() {
        for (int i = 0; i < sortedScoreList.size() && i < MAX_HIGHSCORE_SIZE; i++) {
            tenHighScore[i] = sortedScoreList.get(i);
        }
        return tenHighScore;
    }

    public String[] getTenHighScoreName() {
        for (int i = 0; i < sortedScoreList.size() && i < MAX_HIGHSCORE_SIZE; i++) {
            for(int j = 0; j < allScores.size(); j++) {
                if (sortedScoreList.get(i) == allScores.get(j).getPlayerScore() && allProfileIndex.contains(j)) {
                    tenHighScoreName[i] = allScores.get(j).getPlayerName();
                    allProfileIndex.add(j);
                }
            }
        }
        return tenHighScoreName;
    }

    public static ArrayList<String> getNameList() {
        return nameList;
    }
}
