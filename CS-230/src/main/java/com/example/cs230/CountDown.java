
public class CountDown implements Runnable {
    private final Object lock = new Object();
    private Thread thread;
    private int countdown;
    private boolean runningCountdown = true;
    private boolean pause = false;

    public CountDown(int times) {
        countdown = times;
    }

    public void run() {
        while (runningCountdown && countdown > 0) {
            if (!pause) {
                pause();
            } else {
                resume();
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
            countdown--;
            if (countdown < 0) {
                runningCountdown = false;
                System.out.println("You've lost");
                System.exit(0);
            }
        }
    }

    public void start() {
        run();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
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
}