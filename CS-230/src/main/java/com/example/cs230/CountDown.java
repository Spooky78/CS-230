package com.example.cs230; /**
 * @author Lewis Dong
 * @date 2022/11/25 12:26
 * @version V1.0
 * <p>
 * Description: using java.util.Timer to achieve timer countdown
 * @see
 */
import java.util.*;
import java.util.concurrent.TimeUnit;
public class CountDown {
    private int curSec;
    public CountDown(int limitSec) throws InterruptedException {
        this.curSec = limitSec;
        System.out.println("" + limitSec + "s");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("" + --curSec + "s");
            }
        }, 0, 1000);
        TimeUnit.SECONDS.sleep(limitSec);
        timer.cancel();
        System.out.println("Time out!");
    }
    //Test method
    public static void main(String[] args) throws InterruptedException {
        new CountDown(10);
    }
}
