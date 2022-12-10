package com.example.cs230;

import java.util.ArrayList;

public class SaveFile {
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
    private ArrayList<Tile> board; //Array of board tiles, use board dimessions to read in separate lines

    public SaveFile(Player player, ArrayList<FlyingAssassin> assassins, ArrayList<FloorFollowingThief> ffThieves,
                    ArrayList<SmartThief> sThieves, ArrayList<Coin> coins, ArrayList<Lever> levers,
                    ArrayList<Gate> gates, ArrayList<Bomb> bombs, ArrayList<Clock> clocks,
                    Door door, int time, int score, Board boardClass){

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


    }
}
