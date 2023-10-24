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

public class StopwatchFragment extends Fragment implements View.OnClickListener,DataFromStopwatchInterface {
    Button startButton;
    Button stopButton;
    Button resumeButton;
    ExecutorService service;
    StopwachRunnable stopwachRunnable;
    TextView displayStoperwatch;
    private WeakReference<Activity> weakActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_stopwatch, container, false);
        weakActivity = new WeakReference<>(getActivity());
        startButton = layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        stopButton = layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        resumeButton = layout.findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(this);
        displayStoperwatch = layout.findViewById(R.id.stopwatch_display);
        return layout;
    }
    public void startStopwatch(){
        if (service==null){
            service = Executors.newSingleThreadExecutor();
            stopwachRunnable = new StopwachRunnable(weakActivity,this);
            stopwachRunnable.setEndMillisecond(System.currentTimeMillis());
        }
        stopwachRunnable.setDoRunning(true);
        service.execute(stopwachRunnable);
    }
    @Override
    public void onClick(View v) {
        if (v.equals(startButton)){
            startStopwatch();
            Toast.makeText(getContext(),"START",Toast.LENGTH_SHORT).show();

        }
        if (v.equals(stopButton)){
            Toast.makeText(getContext(),"STOP",Toast.LENGTH_SHORT).show();
            stopwachRunnable.setDoRunning(false);
        }
        if (v.equals(resumeButton)) Toast.makeText(getContext(),"RESUME",Toast.LENGTH_SHORT).show();

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void getValueStopwatch(String houer, String minutes, String second, String milliseconds){
        displayStoperwatch.setText(houer+":"+minutes+":"+second+":"+milliseconds);

    }

}