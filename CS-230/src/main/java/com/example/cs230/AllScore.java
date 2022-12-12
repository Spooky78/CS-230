package com.example.cs230;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * all player score had been got.
 *
 * @author Rex
 */
public class AllScore {
    private static final int MAX_HIGH_SCORE_SIZE = 10;
    private static ArrayList<PlayerScore> allScores = new ArrayList<>();
    private static ArrayList<Integer> scoreList = new ArrayList<>();
    private static int highScoreSize;
    private static final String SCORE_FILE_PATH = "CS-230/src/main/resources/Levels/Score.txt";
    private static final ArrayList<Integer> ALL_PROFILE_INDEX = new ArrayList<>();

    /**
     * loads the scores from the file.
     */
    public static void loadScore() {
        allScores.clear();
        try {
            File playerScoreFile = new File(SCORE_FILE_PATH);
            Scanner playerScoreReader = new Scanner(playerScoreFile);
            while (playerScoreReader.hasNextLine()) {
                ArrayList<Integer> currentPlayerScore = new ArrayList<>();
                String readLine = playerScoreReader.nextLine();
                String[] params = readLine.split(", ");
                String playerName = params[0];
                String playerAllScoreList = params[1];
                String[] scoreParams = playerAllScoreList.split(" ");
                for (int j = 0; j < scoreParams.length; j++) {
                    currentPlayerScore.add(Integer.parseInt(scoreParams[j]));
                }
                allScores.add(new PlayerScore(playerName, currentPlayerScore));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * deletes all the scores of the current player profile.
     *
     * @param name the profile name
     */
    public static void deleteAllScoreCurrentName(String name) {
        for (int i = 0; i < allScores.size(); i++) {
            if (name.equals(allScores.get(i).getPlayerName())) {
                allScores.remove(i);
                writeScoreInTxt();
            }
        }
    }

    /**
     * updates the score of the player for that level.
     *
     * @param name       the profile name
     * @param score      the new score
     * @param levelIndex the index of the level
     */
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

    /**
     * adds a default score for the next level.
     *
     * @param playerName the profile name
     */
    public static void addDefaultScoreNextLevel(String playerName) {
        for (int i = 0; i < allScores.size(); i++) {
            if (playerName.equals(allScores.get(i).getPlayerName())) {
                allScores.get(i).addALevelScore();
                writeScoreInTxt();
            }
        }
    }

    /**
     * adds the score to the score storage.
     *
     * @param playerName the profile name
     */
    public static void addScore(String playerName) {
        ArrayList<Integer> defaultPlayerScore = new ArrayList<>();
        defaultPlayerScore.add(0);
        allScores.add(new PlayerScore(playerName, defaultPlayerScore));
        writeScoreInTxt();
    }

    /**
     * write the score to the text save file.
     */
    public static void writeScoreInTxt() {
        try {
            FileWriter playerScores = new FileWriter(SCORE_FILE_PATH);
            for (int i = 0; i < allScores.size(); i++) {
                playerScores.write(allScores.get(i).getPlayerName() + ", ");
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

    /**
     * creates and sorts the score list in descending order.
     *
     * @param levelIndex the index of the level
     */
    public static void createAndSortScoreList(int levelIndex) {
        scoreList.clear();
        for (int i = 0; i < allScores.size(); i++) {
            scoreList.add(allScores.get(i).getPlayerScoreAtLevel(levelIndex));
        }
        Collections.sort(scoreList, Collections.reverseOrder());
        if (MAX_HIGH_SCORE_SIZE < scoreList.size()) {
            highScoreSize = MAX_HIGH_SCORE_SIZE;
        } else {
            highScoreSize = scoreList.size();
        }
    }

    /**
     * gets the ten highest scores.
     *
     * @return the ten highest scores
     */
    public static int[] getTenHighScore() {
        int[] tenHighScore = new int[highScoreSize];
        for (int i = 0; i < highScoreSize; i++) {
            tenHighScore[i] = scoreList.get(i);
        }
        return tenHighScore;
    }

    /**
     * get the names of the profiles with the ten highest scores.
     *
     * @param levelIndex the index of the level
     * @return the names of the profiles with the ten highest scores
     */
    public static String[] getTenHighScoreName(int levelIndex) {
        ALL_PROFILE_INDEX.clear();
        String[] tenHighScoreName = new String[highScoreSize];
        for (int i = 0; (i < highScoreSize); i++) {
            for (int j = 0; j < allScores.size(); j++) {
                if (scoreList.get(i) == allScores.get(j).getPlayerScoreAtLevel(levelIndex)
                        && !ALL_PROFILE_INDEX.contains(j)) {
                    ALL_PROFILE_INDEX.add(j);
                }
            }
        }
        for (int i = 0; i < ALL_PROFILE_INDEX.size(); i++) {
            tenHighScoreName[i] = allScores.get(ALL_PROFILE_INDEX.get(i)).getPlayerName();
        }
        return tenHighScoreName;
    }
}



