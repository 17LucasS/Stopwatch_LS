package com.example.stopwatchls;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

public class StopwachRunnable implements Runnable {
    private Long startMillisecond;
    private Long endMillisecond;
    private boolean doRunning;

    private WeakReference<DataFromStopwatchInterface> dataFromStopwatchInterface;
    private Handler handler;

    public StopwachRunnable(WeakReference<DataFromStopwatchInterface> dataFromStopwatchInterface) {
        this.dataFromStopwatchInterface = dataFromStopwatchInterface;
        this.handler = new android.os.Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        while (doRunning) {
            startMillisecond = System.currentTimeMillis();
            long tMillisecond = startMillisecond - endMillisecond;
            int hours = (int) (tMillisecond / 3600000);
            int minutes = (int) ((tMillisecond % 3600000) / 60000);
            int seconds = (int) ((tMillisecond % 60000) / 1000);
            int milliseconds = (int) (tMillisecond % 1000)/10;

            if (dataFromStopwatchInterface.get() != null) {
                handler.post(() -> dataFromStopwatchInterface.get().getValueStopwatch(hours, minutes,seconds, milliseconds));
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setEndMillisecond(Long endMillisecond) {
        this.endMillisecond = endMillisecond;
    }

    public void setDoRunning(boolean doRunning) {
        this.doRunning = doRunning;
        if (!this.doRunning) {
            handler.removeCallbacksAndMessages(null);
        }
    }

}
