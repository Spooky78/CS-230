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
    private static ArrayList<PlayerScore> allScores = new ArrayList<>();
    private static ArrayList<Integer> scoreList = new ArrayList<>();
    private static int HIGH_SCORE_SIZE;
    private static String SCORE_FILE_PATH = "CS-230/src/main/resources/Levels/Score.txt";
    private static ArrayList<Integer> allProfileIndex = new ArrayList<>();

    public static void loadScore() {
        allScores.clear();
            try {
                File playerScoreFile = new File(SCORE_FILE_PATH);
                Scanner playerScoreReader = new Scanner(playerScoreFile);
                while (playerScoreReader.hasNextLine()) {
                    ArrayList<Integer>currentPlayerScore = new ArrayList<>();
                    String readLine = playerScoreReader.nextLine();
                    String[] params = readLine.split(", ");
                    String playerName = params[0];
                    String playerAllScoreList = params[1];
                    String[] scoreParams = playerAllScoreList.split(" ");
                    for(int j = 0; j < scoreParams.length; j++) {
                        currentPlayerScore.add(Integer.parseInt(scoreParams[j]));
                    }
                    allScores.add(new PlayerScore(playerName, currentPlayerScore));
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    public static void deleteAllScoreCurrentName(String name) {
        for (int i = 0; i < allScores.size(); i++) {
            if (name.equals(allScores.get(i).getPlayerName())) {
                allScores.remove(i);
                writeScoreInTxt();
            }
        }
    }

    public static void updateScore(String name, int score, int levelIndex) {
        for (int i = 0; i < allScores.size(); i++) {
            if (name.equals(allScores.get(i).getPlayerName())) {
                for (int j = 0; j < allScores.get(i).getPlayerScoreSize(); j++) {
                    if (score > allScores.get(i).getPlayerScoreAtLevel(levelIndex)) {
                        allScores.get(i).setPlayerScore(levelIndex, score);
                        writeScoreInTxt();
                    }
                }
            }
        }
    }

    public static void addDefaultScoreNextLevel(String playerName) {
        for (int i = 0; i < allScores.size(); i++) {
            if (playerName.equals(allScores.get(i).getPlayerName())) {
                allScores.get(i).addALevelScore();
                writeScoreInTxt();
            }
        }
    }

    public static void addScore(String playerName) {
        ArrayList<Integer> defaultPlayerScore = new ArrayList<>();
        defaultPlayerScore.add(0);
        allScores.add(new PlayerScore(playerName, defaultPlayerScore));
        writeScoreInTxt();
    }

    public static void writeScoreInTxt() {
        try {
            FileWriter playerScores = new FileWriter(SCORE_FILE_PATH);
            for (int i = 0; i < allScores.size(); i++) {
                playerScores.write(allScores.get(i).getPlayerName()+ ", ");
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

    public static void createAndSortScoreList(int levelIndex) {
        scoreList.clear();
        for(int i = 0; i < allScores.size(); i++) {
            scoreList.add(allScores.get(i).getPlayerScoreAtLevel(levelIndex));
        }
            Collections.sort(scoreList, Collections.reverseOrder());
        if(MAX_HIGH_SCORE_SIZE < scoreList.size()) {
            HIGH_SCORE_SIZE = MAX_HIGH_SCORE_SIZE;
        } else {
            HIGH_SCORE_SIZE = scoreList.size();
        }
    }

        public static int[] getTenHighScore() {
            int[] tenHighScore = new int[HIGH_SCORE_SIZE];
            for (int i = 0; i < HIGH_SCORE_SIZE; i++) {
                tenHighScore[i] = scoreList.get(i);
            }
            return tenHighScore;
        }

        public static String[] getTenHighScoreName(int levelIndex) {
        allProfileIndex.clear();
            String[] tenHighScoreName = new String[HIGH_SCORE_SIZE];
            for (int i = 0; (i < HIGH_SCORE_SIZE); i++) {
                for(int j = 0; j < allScores.size(); j++) {
                    if ((scoreList.get(i) == allScores.get(j).getPlayerScoreAtLevel(levelIndex))
                            && !allProfileIndex.contains(j)) {
                        tenHighScoreName[i] = allScores.get(j).getPlayerName();
                        allProfileIndex.add(j);
                    }
                }
            }
            return tenHighScoreName;
        }
}



