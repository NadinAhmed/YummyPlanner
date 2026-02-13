package com.nadin.yummy_planner.presentation.splash.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.presentation.splash.presenter.SplashPresenter;
import com.nadin.yummy_planner.presentation.splash.presenter.SplashPresenterImpl;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends Fragment implements SplashView {

    private NavController navController;
    private SplashPresenter presenter;

    public SplashScreen() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenterImpl(this, requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            presenter.decideStartDestination();
        }, 3000);
    }

    @Override
    public void navigateToHome() {
        navController.navigate(R.id.action_splashScreen_to_homeFragment);
    }

    @Override
    public void navigateToAuth() {
        navController.navigate(R.id.action_splashScreen_to_authScreen);
    }
}
