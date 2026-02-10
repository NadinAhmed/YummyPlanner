package com.nadin.yummy_planner.presentation.profile.presenter;

import android.content.Context;

import com.nadin.yummy_planner.data.auth.datasource.AuthDataSource;
import com.nadin.yummy_planner.data.settings.datasource.AppSettingsDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfilePresenter implements ProfileContract.Presenter {
    private final ProfileContract.View view;
    private final AuthDataSource authDataSource;
    private final AppSettingsDataSource appSettingsDataSource;
    private final CompositeDisposable compositeDisposable;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        this.view = view;
        this.authDataSource = new AuthDataSource(context);
        this.appSettingsDataSource = new AppSettingsDataSource(context);
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadProfileState() {
        view.setDarkThemeEnabled(appSettingsDataSource.isDarkThemeEnabledSync());
        view.setSelectedLanguage(appSettingsDataSource.getLanguageCodeSync());

        if (authDataSource.isGuestUser() || !authDataSource.isUserLoggedIn()) {
            view.showGuestState();
            return;
        }

        compositeDisposable.add(authDataSource.getSignedInUserName()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showSignedInState, throwable -> view.showSignedInState("User")));
    }

    @Override
    public void onLogoutClicked() {
        compositeDisposable.add(authDataSource.logoutCompletable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showGuestState, throwable -> view.showError(throwable.getMessage())));
    }

    @Override
    public void onSignInClicked() {
        view.navigateToAuth();
    }

    @Override
    public void onDarkThemeChanged(boolean isEnabled) {
        appSettingsDataSource.setDarkThemeEnabled(isEnabled);
    }

    @Override
    public void onLanguageSelected(String languageCode) {
        appSettingsDataSource.setLanguageCode(languageCode);
    }

    @Override
    public void clear() {
        compositeDisposable.clear();
    }
}
