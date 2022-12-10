package com.example.cs230;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AllScore {
    private static final int MAX_HIGH_SCORE_SIZE = 10;
    private int[] tenHighScore = new int[MAX_HIGH_SCORE_SIZE];
    private String[] tenHighScoreName = new String[MAX_HIGH_SCORE_SIZE];
    private static ArrayList<PlayerScore> allScores = new ArrayList<>();
    private static ArrayList<Integer> scoreList = new ArrayList<>();
    private static int level = 0;
    private static final int MAX_POSSIBLE_LEVEL = 4;
    private static String SCORE_FILE_PATH = "CS-230/src/main/resources/Levels/Score.txt";
    private static ArrayList<Integer>currentPlayerScore = new ArrayList<>();
    private ArrayList<Integer> allProfileIndex = new ArrayList<>();

    public static void loadScore() {
        allScores.clear();
        for (int i = 0; i < MAX_POSSIBLE_LEVEL; i++) {
            try {
                level = i;
                File playerScoreFile = new File(SCORE_FILE_PATH);
                Scanner playerScoreReader = new Scanner(playerScoreFile);
                while (playerScoreReader.hasNextLine()) {
                    String readLine = playerScoreReader.nextLine();
                    String[] params = readLine.split(" ");
                    String playerName = params[0];
                    for(int j = 1; j < params.length; j++) {
                        currentPlayerScore.add(Integer.parseInt(params[j]));
                    }
                    allScores.add(new PlayerScore(playerName, currentPlayerScore));
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void deleteAllScoreCurrentName(String name) {
        for (int i = 0; i < allScores.size(); i++) {
            if (name.equals(allScores.get(i).getPlayerName())) {
                allScores.remove(i);
            }
        }
    }

    public static void updateScore(String name, int score, int levelIndex, int timeLeft) {
        for (int i = 0; i < allScores.size(); i++) {
            if (name.equals(allScores.get(i).getPlayerName())) {
                for (int j = 0; j < allScores.get(i).getPlayerScoreSize(); j++) {
                    if (score > allScores.get(i).getPlayerScoreAtLevel(levelIndex)) {
                        allScores.get(i).setPlayerScore(levelIndex,score);
                    }
                }
            }
        }
    }

    public void addScore(String playerName) {
        ArrayList<Integer> defaultPlayerScore = new ArrayList<>();
        defaultPlayerScore.add(0);
        allScores.add(new PlayerScore(playerName, defaultPlayerScore));
        writeScoreInTxt();
    }

    public static void writeScoreInTxt() {
        try {
            FileWriter playerScores = new FileWriter(SCORE_FILE_PATH);
            for (int i = 0; i < allScores.size(); i++) {
                playerScores.write(allScores.get(i).getPlayerName()+ " ");
                for (int j = 0; j < allScores.get(i).getPlayerScoreSize(); j++) {
                    playerScores.write(allScores.get(i).getPlayerScoreAtLevel(j) + " ");
                }
                playerScores.write("\n");
            }
            playerScores.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAndSortScoreList(int levelIndex) {
        scoreList.clear();
        for(int i = 0; i < allScores.size(); i++) {
            scoreList.add(allScores.get(i).getPlayerScoreAtLevel(levelIndex));
        }
            Collections.sort(scoreList, Collections.reverseOrder());
        }

        public int[] getTenHighScore() {
            for (int i = 0; i < scoreList.size() && i < MAX_HIGH_SCORE_SIZE; i++) {
                tenHighScore[i] = scoreList.get(i);
            }
            return tenHighScore;
        }

        public String[] getTenHighScoreName(int levelIndex) {
            for (int i = 0; i < scoreList.size() && i < MAX_HIGH_SCORE_SIZE; i++) {
                for(int j = 0; j < allScores.size(); j++) {
                    if (scoreList.get(i) == allScores.get(j).getPlayerScoreAtLevel(levelIndex)
                            && allProfileIndex.contains(j)) {
                        tenHighScoreName[i] = allScores.get(j).getPlayerName();
                        scoreList.add(j);
                    }
                }
            }
            return tenHighScoreName;
        }
        /*
        To get all high score tables.
        Do
        AllScore.createAndSortScoreList;
        Then
        AllScore.getTenHighScore; <- This will be the int[]
        AllScore.getTenHighScoreName; <- This will be the String[]
         */
}



