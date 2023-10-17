package com.example.stopwatchls;

public class StopwachRunnable implements Runnable{
    private Long startMillisecond;
    private Long endMillisecond;
    private String hours;
    private String minutes;
    private String seconds;
    private String milliseconds;
    private boolean doRunning;

    private DataFromStopwatchInterface dataFromStopwatchInterface;



    @Override
    public void run() {
        if (endMillisecond==null){
            endMillisecond = 0L;
        }
        startMillisecond = System.currentTimeMillis();
        long tMillisecond = startMillisecond - endMillisecond;
        int hoursInt = (int) (tMillisecond / 3600000);
        int minutesInt = (int) ((tMillisecond % 3600000) / 60000);
        int secondsInt = (int) ((tMillisecond % 60000) / 1000);
        int millisecondInt = (int) (tMillisecond % 1000);

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
