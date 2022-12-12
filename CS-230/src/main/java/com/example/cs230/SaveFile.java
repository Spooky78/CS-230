package com.example.cs230;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * create file can be saved.
 *
 * @author Everyone
 */
public class SaveFile {
    private final String SAVE_FILE_PATH = "CS-230/src/main/resources/Levels/LastSave.txt";
    private Player player;
    private ArrayList<FlyingAssassin> assassins;
    private ArrayList<FloorFollowingThief> ffThieves;
    private ArrayList<SmartThief> smartThief;
    private ArrayList<Coin> coins;
    private ArrayList<Lever> levers;
    private ArrayList<Gate> gates;
    private ArrayList<Bomb> bombs;
    private ArrayList<Clock> clocks;
    private Door door;
    private int time;
    private int score;
    private String[] board; //Array of board tiles, use board dimessions to read in separate lines
    private Board boardClass;
    private String name;
    private int levelIndex;

    FileWriter saveLevel = new FileWriter(SAVE_FILE_PATH);

    /**
     * save file.
     *
     * @param player     player
     * @param assassins  assassins
     * @param ffThieves  floor following thieves
     * @param sThieves   smart thieves
     * @param coins      coins
     * @param levers     levers
     * @param gates      gate
     * @param bombs      bomb
     * @param clocks     clock
     * @param door       door
     * @param time       time left
     * @param score      current score
     * @param boardClass game screen
     * @param name       player name
     * @param levelIndex index of level
     * @throws IOException catch exception
     */
    public SaveFile(Player player, ArrayList<FlyingAssassin> assassins, ArrayList<FloorFollowingThief> ffThieves,
                    ArrayList<SmartThief> sThieves, ArrayList<Coin> coins, ArrayList<Lever> levers,
                    ArrayList<Gate> gates, ArrayList<Bomb> bombs, ArrayList<Clock> clocks,
                    Door door, int time, int score, Board boardClass, String name, int levelIndex) throws IOException {
        this.player = player;
        this.assassins = assassins;
        this.ffThieves = ffThieves;
        this.smartThief = sThieves;
        this.coins = coins;
        this.levers = levers;
        this.gates = gates;
        this.bombs = bombs;
        this.clocks = clocks;
        this.door = door;
        this.time = time;
        this.score = score;
        this.boardClass = boardClass;
        this.name = name;
        this.levelIndex = levelIndex;
    }

    /**
     * print everything.
     *
     * @throws IOException catch
     */
    public void printAll() throws IOException {
        printPlayer();
        printLevelIndex();
        printName();
        printCurrentScore();
        printAssassin();
        printFFThief();
        printSThief();
        printAllCoin();
        printDoor();
        printGate();
        printLever();
        printBombs();
        printSeconds();
        printSize();
        printBoard();
        saveLevel.close();
    }

    /**
     * print level.
     *
     * @throws IOException catch
     */
    private void printLevelIndex() throws IOException {
        saveLevel.write("LEVEL, " + levelIndex + "\n");
    }

    /**
     * print player.
     *
     * @throws IOException catch
     */
    public void printPlayer() throws IOException {
        saveLevel.write("PLAYER, " +
                player.getPlayerCoords()[0] + ", " +
                player.getPlayerCoords()[1] + "\n");
    }

    /**
     * print name.
     *
     * @throws IOException catch
     */
    public void printName() throws IOException {
        saveLevel.write("PROFILE, " + name + "\n");
    }

    /**
     * print score.
     *
     * @throws IOException catch
     */
    public void printCurrentScore() throws IOException {
        saveLevel.write("SCORE, " +
                score + "\n");
    }

    /**
     * print assassin.
     *
     * @throws IOException catch
     */
    public void printAssassin() throws IOException {
        for (int i = 0; i < assassins.size(); i++) {
            saveLevel.write("ASSASSIN, " +
                    assassins.get(i).getStartDirection() + ", " +
                    assassins.get(i).getCoords()[0] + ", " +
                    assassins.get(i).getCoords()[1] + "\n");
        }
    }

    /**
     * print floor following thief.
     *
     * @throws IOException catch
     */
    public void printFFThief() throws IOException {
        for (int i = 0; i < ffThieves.size(); i++) {
            saveLevel.write("FFTHIEF, " +
                    ffThieves.get(i).getTileColour() + ", " +
                    ffThieves.get(i).getStartDirection() + ", " +
                    ffThieves.get(i).getCoords()[0] + ", " +
                    ffThieves.get(i).getCoords()[1] + "\n");
        }
    }

    /**
     * print smart thief.
     *
     * @throws IOException
     */
    public void printSThief() throws IOException {
        for (int i = 0; i < smartThief.size(); i++) {
            saveLevel.write("STHIEF, " +
                    smartThief.get(i).getCoords()[0] + ", " +
                    smartThief.get(i).getCoords()[1] + "\n");
        }
    }

    /**
     * print all coins.
     *
     * @throws IOException catch
     */
    public void printAllCoin() throws IOException {
        for (int i = 0; i < coins.size(); i++) {
            saveLevel.write("COIN, " +
                    coins.get(i).getCoinType() + ", " +
                    coins.get(i).getCoords()[0] + ", " +
                    coins.get(i).getCoords()[1] + "\n");
        }
    }

    /**
     * print door.
     *
     * @throws IOException catch
     */
    public void printDoor() throws IOException {
        saveLevel.write("DOOR, " +
                door.getCoords()[0] + ", " +
                door.getCoords()[1] + "\n");
    }

    /**
     * print gate.
     *
     * @throws IOException catch
     */
    public void printGate() throws IOException {
        for (int i = 0; i < gates.size(); i++) {
            saveLevel.write("GATE" + gates.get(i).getGateType() + ", " +
                    gates.get(i).getCoords()[0] + ", " +
                    gates.get(i).getCoords()[1] + "\n");
        }
    }

    /**
     * print lever.
     *
     * @throws IOException catch
     */
    public void printLever() throws IOException {
        for (int i = 0; i < levers.size(); i++) {
            saveLevel.write("LEVER, " +
                    levers.get(i).getLeverColour() + ", " +
                    levers.get(i).getCoords()[0] + ", " +
                    levers.get(i).getCoords()[1] + "\n");
        }
    }

    /**
     * print bombs.
     *
     * @throws IOException catch
     */
    public void printBombs() throws IOException {
        for (int i = 0; i < bombs.size(); i++) {
            saveLevel.write("BOMB, " +
                    bombs.get(i).getCoords()[0] + ", " +
                    bombs.get(i).getCoords()[1] + "\n");
        }
    }

    /**
     * print time remain.
     *
     * @throws IOException catch
     */
    public void printSeconds() throws IOException {
        saveLevel.write("SECONDS, " +
                time + "\n");
    }

    /**
     * print screen size.
     *
     * @throws IOException catch
     */
    public void printSize() throws IOException {
        saveLevel.write("SIZE, " +
                boardClass.getBoardSizeX() + ", " +
                boardClass.getBoardSizeY() + "\n");
    }

    /**
     * print game screen.
     *
     * @throws IOException catch
     */
    public void printBoard() throws IOException {
        saveLevel.write("BOARD\n");
        for (int i = 0; i < Board.getBoardString().size(); i++) {
            saveLevel.write(Board.getBoardString().get(i) + "\n");
        }
    }
}
