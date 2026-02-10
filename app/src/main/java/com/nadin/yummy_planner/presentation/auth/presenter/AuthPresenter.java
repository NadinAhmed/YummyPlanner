package com.nadin.yummy_planner.presentation.auth.presenter;

import android.content.Context;
import android.content.Intent;

import com.nadin.yummy_planner.data.auth.datasource.AuthDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AuthPresenter implements AuthContract.Presenter{

    private final AuthContract.View view;
    private final AuthDataSource authDataSource;
    private final CompositeDisposable compositeDisposable;

    public AuthPresenter(AuthContract.View view, Context context){
        this.view = view;
        this.authDataSource = new AuthDataSource(context);
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String email, String password) {
        view.showLoading();
        compositeDisposable.add(authDataSource.login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> view.onSuccess(), throwable -> view.onError(throwable.getMessage())));
    }

    @Override
    public void register(String name, String email, String password) {
        view.showLoading();
        compositeDisposable.add(authDataSource.register(name, email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> view.onSuccess(), throwable -> view.onError(throwable.getMessage())));
    }

    @Override
    public void loginWithGoogle() {
        view.launchGoogleSignIn(authDataSource.getGoogleSignInIntent());
    }

    @Override
    public void completeGoogleLogin(Intent data) {
        view.showLoading();
        compositeDisposable.add(authDataSource.loginWithGoogle(data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> view.onSuccess(), throwable -> view.onError(throwable.getMessage())));
    }

    @Override
    public void continueAsGuest() {
        authDataSource.continueAsGuest();
        view.onSuccess();
    }

    @Override
    public void clear() {
        compositeDisposable.clear();
    }
}
