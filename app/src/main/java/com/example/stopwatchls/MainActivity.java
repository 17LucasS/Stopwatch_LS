package com.example.stopwatchls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    private static final String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializationOfVariables(savedInstanceState);


    }

    private void initializationOfVariables(Bundle saveinstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        if (saveinstanceState == null) {
            fragment = new StopwatchFragment();
        } else {
            fragment = getSupportFragmentManager().getFragment(saveinstanceState, CURRENT_FRAGMENT);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    public void floatingOnClick(View view) {
        fragment = new SaveFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
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


}