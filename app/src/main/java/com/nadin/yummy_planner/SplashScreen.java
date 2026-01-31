package com.nadin.yummy_planner;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends Fragment {

    //edit it from firebase
    boolean isLoggedIn = false;
    int destination = isLoggedIn
            ? R.id.action_splashScreen_to_authScreen
            : R.id.action_splashScreen_to_authScreen;
    NavController navController;

    public SplashScreen() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            navController.navigate(destination);
        }, 3000);
    }
}