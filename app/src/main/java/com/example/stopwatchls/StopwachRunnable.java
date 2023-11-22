package com.example.stopwatchls;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

public class StopwachRunnable implements Runnable {
    private Long tMillisecond;
    private Long endMillisecond;
    private boolean doRunning;
    private final WeakReference<DataFromStopwatchInterface> dataFromStopwatchInterface;
    private final Handler handler;

    public StopwachRunnable(WeakReference<DataFromStopwatchInterface> dataFromStopwatchInterface) {
        this.dataFromStopwatchInterface = dataFromStopwatchInterface;
        this.handler = new android.os.Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        while (doRunning) {
            long startMillisecond = System.currentTimeMillis();
            tMillisecond = startMillisecond - endMillisecond;
            int hours = (int) (tMillisecond / 3600000);
            int minutes = (int) ((tMillisecond % 3600000) / 60000);
            int seconds = (int) ((tMillisecond % 60000) / 1000);
            int milliseconds = (int) (tMillisecond % 1000) / 10;

            if (dataFromStopwatchInterface.get() != null) {
                handler.post(() -> dataFromStopwatchInterface.get().getValueStopwatch(hours, minutes, seconds, milliseconds));
            }
         handler.postDelayed(this,30);
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

    public Long gettMillisecond() {
        return tMillisecond;
    }
}
