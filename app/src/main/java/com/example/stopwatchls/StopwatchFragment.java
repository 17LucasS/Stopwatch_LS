package com.example.stopwatchls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StopwatchFragment extends Fragment implements View.OnClickListener, DataFromStopwatchInterface {
    Button startButton;
    Button stopButton;
    ExecutorService service;
    StopwachRunnable stopwachRunnable;
    TextView displayStoperwatch;
    private WeakReference<Activity> weakActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        weakActivity = new WeakReference<>(getActivity());
        startButton = layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        stopButton = layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        displayStoperwatch = layout.findViewById(R.id.stopwatch_display);
        return layout;
    }

    public void startStopwatch() {
        if (service == null) {
            service = Executors.newSingleThreadExecutor();
            stopwachRunnable = new StopwachRunnable(new WeakReference<>(this));
            stopwachRunnable.setEndMillisecond(System.currentTimeMillis());
        }
        stopwachRunnable.setDoRunning(true);
        service.execute(stopwachRunnable);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(startButton)) {
            if (startButton.getText().equals(getString(R.string._start_button))) {
                startButton.setText(getString(R.string.stop_button));
                startStopwatch();

            } else if (startButton.getText().equals(getString(R.string.stop_button))) {
                startButton.setText(getString(R.string._start_button));
                stopwachRunnable.setDoRunning(false);
            }
        }
        if (v.equals(stopButton)) {

        }
    }

    @Override
    public void getValueStopwatch(int houer, int minutes, int second, int milliseconds) {
        String time = String.format(getString(R.string.string_time), houer, minutes, second, milliseconds);
        displayStoperwatch.setText(time);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (service != null) {
            service.shutdown();
            stopwachRunnable.setDoRunning(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (service != null) {
            try {
                if (!service.awaitTermination(40, TimeUnit.MILLISECONDS)) ;
                service.shutdownNow();
            } catch (InterruptedException e) {
                service.shutdownNow();
            }
        }

    }
}