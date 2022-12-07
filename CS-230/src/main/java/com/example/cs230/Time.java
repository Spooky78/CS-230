package com.example.cs230;
import java.util.Timer;
import java.util.TimerTask;
public class Time {
    int currentTime;
    Timer t;
    boolean isKilled = false;
    public Time(int seconds) {
        int del = 1000;
        int per = 1000;
        t = new Timer();
        currentTime =(seconds);
        //System.out.println();
        t.scheduleAtFixedRate(new TimerTask()
        {
            public void run() {
                seti();
                //System.out.println(seti());
            }
        }, del, per);
    }
    private int seti() {
        if (currentTime == 1 || isKilled) {
            t.cancel();
        }
        return --currentTime;
    }

    public void isKilled() {
        isKilled = true;
    }
    public int getCurrentTime() {
        return currentTime;
     }
}