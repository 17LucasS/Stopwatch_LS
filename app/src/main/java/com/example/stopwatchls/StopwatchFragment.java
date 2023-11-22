package com.example.stopwatchls;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
    Button catchButton;
    TextView displayStopperWatch;
    private ExecutorService service;
    private StopwachRunnable stopwachRunnable;
    private long milliSecond;
    private String time;
    private boolean stopWatchIsActive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        startButton = layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        catchButton = layout.findViewById(R.id.catch_button);
        catchButton.setOnClickListener(this);
        catchButton.setEnabled(false);
        stopWatchIsActive = false;
        displayStopperWatch = layout.findViewById(R.id.stopwatch_display);
        WeakReference<DataFromStopwatchInterface> weakActivity = new WeakReference<>(this);
        service = Executors.newSingleThreadExecutor();
        stopwachRunnable = new StopwachRunnable(weakActivity);


        Log.v("Fragment", "OnCreate");
        return layout;
    }

    public void startStopwatch() {
        if (stopWatchIsActive) {
            stopwachRunnable.setEndMillisecond(milliSecond);
            Log.v("Start if: " + milliSecond, String.valueOf(stopWatchIsActive));
        } else {
            stopwachRunnable.setEndMillisecond(System.currentTimeMillis());
            Log.v("Start else: " + milliSecond, String.valueOf(stopWatchIsActive));
        }
        stopwachRunnable.setDoRunning(true);
        service.execute(stopwachRunnable);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(startButton)) {
            if (startButton.getText().equals(getString(R.string.start_string))) {
                startButton.setText(getString(R.string.stop_string));
                catchButton.setEnabled(true);
                startStopwatch();
                stopWatchIsActive = true;

            } else if (startButton.getText().equals(getString(R.string.stop_string))) {
                startButton.setText(getString(R.string.resume_string));
                catchButton.setText(getString(R.string.reset_string));
                milliSecond = stopwachRunnable.gettMillisecond();
                stopwachRunnable.setDoRunning(false);


            } else if (startButton.getText().equals(getString(R.string.resume_string))) {
                Toast.makeText(getActivity(), "Wznów", Toast.LENGTH_SHORT).show();
                catchButton.setText(getString(R.string.catch_string));
                startButton.setText(getString(R.string.stop_string));
                milliSecond = System.currentTimeMillis() - milliSecond;
                startStopwatch();
            }
        }

        if (v.equals(catchButton)) {
            if (catchButton.getText().equals(getString(R.string.catch_string))) {
                Toast.makeText(getActivity(), "Pomiar", Toast.LENGTH_SHORT).show();
            } else if (catchButton.getText().equals(getString(R.string.reset_string))) {
                displayStopperWatch.setText(getString(R.string.default_settings_text_stopwatch));
                stopWatchIsActive = false;
                startButton.setText(R.string.start_string);
                catchButton.setText(R.string.catch_string);
            }
        }
    }

    @Override
    public void getValueStopwatch(int hour, int minutes, int second, int milliseconds) {
        time = String.format(getString(R.string.string_time), hour, minutes, second, milliseconds);
        displayStopperWatch.setText(time);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("StopWatchFragment", "ONStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("StopWatchFragment", "OnDestroy");
        if (service != null) {
            service.shutdown();
            try {
                if (!service.awaitTermination(50, TimeUnit.MILLISECONDS)) {
                    service.shutdownNow();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if (time != null) {
            displayStopperWatch.setText(time);
        }
        Log.v("Fragment", "OnResume");
    }


}