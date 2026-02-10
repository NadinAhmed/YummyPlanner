package com.nadin.yummy_planner.presentation.splash.presenter;

import android.content.Context;

import com.nadin.yummy_planner.data.auth.datasource.AuthDataSource;
import com.nadin.yummy_planner.presentation.splash.view.SplashView;

public class SplashPresenterImpl implements SplashPresenter {
    private final SplashView view;
    private final AuthDataSource authDataSource;

    public SplashPresenterImpl(SplashView view, Context context) {
        this.view = view;
        this.authDataSource = new AuthDataSource(context);
    }

    @Override
    public void decideStartDestination() {
        if (authDataSource.hasActiveSession()) {
            view.navigateToHome();
        } else {
            view.navigateToAuth();
        }
    }
}
