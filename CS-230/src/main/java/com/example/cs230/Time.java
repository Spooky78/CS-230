package com.example.cs230;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Countdown for game.
 * @author Omar
 */
public class Time {
    int currentTime;
    Timer t;
    boolean isKilled = false;

    /**
     * Creates a new timer that starts counting down.
     *
     * @param seconds " s"
     */
    public Time(int seconds) {
        int del = 1000;
        int per = 1000;
        t = new Timer();
        currentTime = (seconds);
        //System.out.println();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                seti();
                //System.out.println(seti());
            }
        }, del, per);
    }

    /**
     * Updates the time left by counting down.
     *
     * @return current time left
     */
    private int seti() {
        if (currentTime == 1 || isKilled) {
            t.cancel();
        }
        return --currentTime;
    }

    /**
     * determines if the player is killed.
     */
    public void isKilled() {
        isKilled = true;
    }

    /**
     * get current time.
     *
     * @return current time
     */
    public int getCurrentTime() {
        return currentTime;
    }
}
