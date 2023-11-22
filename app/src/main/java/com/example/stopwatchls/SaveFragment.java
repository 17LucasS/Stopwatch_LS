package com.example.stopwatchls;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SaveFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, pressedCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("SaveFragment", "Onstop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("SaveFragment", "OnDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pressedCallback.remove();
    }

    OnBackPressedCallback pressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            Fragment fragment = new StopwatchFragment();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    };
}