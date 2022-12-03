package com.example.cs230;

public class CountDown {
    private final Object lock = new Object();
    private int countdown;
    private boolean pause = false;

    public CountDown(int times) {
        countdown = times;
    }

    public void runTime() {
        while (countdown > 0) {
            System.out.println(countdown);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countdown--;
        }
        System.out.println(countdown);
        System.out.println("You've lost");
        System.exit(0);
    }

    public void pause() {
        pause = true;
        synchronized (lock) {
            try {
                lock.wait();
            } catch (Exception ignored) {
            }
        }
    }

    public void resume() {
        pause = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    public String toString() {
        return "" + countdown + "s";
    }
}

class CountDownTester {
    public static void main(String[] args) {
        CountDown countDown = new CountDown(10);
        countDown.runTime();
    }
}
