package com.example.stopwatchls;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class StopwatchFragment extends Fragment implements View.OnClickListener {
    Button startButton;
    Button stopButton;
    Button resumeButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout =  inflater.inflate(R.layout.fragment_stopwatch, container, false);

        startButton = layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        stopButton = layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        resumeButton = layout.findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(startButton)) Toast.makeText(getContext(),"START",Toast.LENGTH_SHORT).show();
        if (v.equals(stopButton)) Toast.makeText(getContext(),"STOP",Toast.LENGTH_SHORT).show();
        if (v.equals(resumeButton)) Toast.makeText(getContext(),"RESUME",Toast.LENGTH_SHORT).show();

    }
}