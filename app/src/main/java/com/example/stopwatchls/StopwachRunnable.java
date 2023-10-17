package com.example.stopwatchls;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class StopwachRunnable implements Runnable{
    private Long startMillisecond;
    private Long endMillisecond;
    private String hours;
    private String minutes;
    private String seconds;
    private String milliseconds;
    private boolean doRunning;

    private DataFromStopwatchInterface dataFromStopwatchInterface;
    private WeakReference<Activity> weekActivity;

    public StopwachRunnable(WeakReference<Activity> weekContext, DataFromStopwatchInterface dataFromStopwatchInterface) {
        this.dataFromStopwatchInterface = dataFromStopwatchInterface;
        this.weekActivity = weekContext;
    }

    @Override
    public void run() {

        startMillisecond = System.currentTimeMillis();
        long tMillisecond = startMillisecond- endMillisecond;
        int hoursInt = (int) (tMillisecond / 3600000);
        int minutesInt = (int) ((tMillisecond % 3600000) / 60000);
        int secondsInt = (int) ((tMillisecond % 60000) / 1000);
        int millisecondInt = (int) (tMillisecond % 1000);

        hours = String.valueOf(hoursInt);
        minutes = String.valueOf(minutesInt);
        seconds = String.valueOf(secondsInt);
        milliseconds = String.valueOf(millisecondInt);

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        weekActivity.get().runOnUiThread(() -> dataFromStopwatchInterface.getValueStopwatch(hours,minutes,seconds,milliseconds));

        if (doRunning){
            run();
        }


    }


    public Long getStartMillisecond() {
        return startMillisecond;
    }

    public Long getEndMillisecond() {
        return endMillisecond;
    }

    public String getHours() {
        return hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getSeconds() {
        return seconds;
    }

    public String getMilliseconds() {
        return milliseconds;
    }

    public void setEndMillisecond(Long endMillisecond) {
        this.endMillisecond = endMillisecond;
    }

    public void setDoRunning(boolean doRunning) {
        this.doRunning = doRunning;
    }
}
