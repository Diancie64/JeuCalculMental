package com.example.jeucalculmental;
import android.os.Handler;
import android.widget.ProgressBar;

public class GameTimer {
    private int totalTime;
    private int timeLeft;
    private ProgressBar progressBar;
    private Handler handler = new Handler();
    private Runnable runnable;

    public interface TimerListener {
        void onTick(int timeLeft);
        void onFinish();
    }

    public GameTimer(int totalTime, ProgressBar progressBar, TimerListener listener) {
        this.totalTime = totalTime;
        this.timeLeft = totalTime;
        this.progressBar = progressBar;

        progressBar.setMax(totalTime);
        progressBar.setProgress(totalTime);

        runnable = new Runnable() {
            @Override
            public void run() {
                timeLeft--;

                progressBar.setProgress(timeLeft);
                listener.onTick(timeLeft);

                if (timeLeft > 0) {
                    handler.postDelayed(this, 1000);
                } else {
                    listener.onFinish();
                }
            }
        };
    }

    public void start() {
        handler.post(runnable);
    }

    public void reset() {
        handler.removeCallbacks(runnable);
        timeLeft = totalTime;
        progressBar.setProgress(totalTime);
    }

    public void stop() {
        handler.removeCallbacks(runnable);
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
