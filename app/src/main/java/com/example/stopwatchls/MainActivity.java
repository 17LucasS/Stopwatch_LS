package com.example.stopwatchls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializationOfVariables(savedInstanceState);
        Log.v("MAIN", "ONCreate");
    }

    private void initializationOfVariables(Bundle saveInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Fragment fragment;
        if (saveInstanceState == null) {
            fragment = new StopwatchFragment();
        } else {
            fragment = getSupportFragmentManager().getFragment(saveInstanceState, CURRENT_FRAGMENT);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        assert fragment != null;
        transaction.replace(R.id.fragment_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    public void floatingOnClick(View view) {
        SaveFragment saveFragment = new SaveFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, saveFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> list = manager.getFragments();
        for (Fragment fragment : list) {
            if (fragment.isVisible()) {
                getSupportFragmentManager().putFragment(outState, CURRENT_FRAGMENT, fragment);
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("Main", "OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Main", "OnDestroy");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Main", "OnResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("Main", "Restart");
    }

}