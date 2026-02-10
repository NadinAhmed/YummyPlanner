package com.nadin.yummy_planner.presentation.auth.presenter;

import android.content.Context;
import android.content.Intent;

import com.nadin.yummy_planner.data.auth.datasource.AuthDataSource;
import com.nadin.yummy_planner.data.sync.BackupRestoreRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AuthPresenter implements AuthContract.Presenter{

    private final AuthContract.View view;
    private final AuthDataSource authDataSource;
    private final BackupRestoreRepository backupRestoreRepository;
    private final CompositeDisposable compositeDisposable;

    public AuthPresenter(AuthContract.View view, Context context){
        this.view = view;
        this.authDataSource = new AuthDataSource(context);
        this.backupRestoreRepository = new BackupRestoreRepository(context);
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String email, String password) {
        view.showLoading();
        compositeDisposable.add(authDataSource.login(email, password)
                .flatMap(user -> backupRestoreRepository.restoreCurrentUserData()
                        .map(restored -> new RestoreResult(restored, null))
                        .onErrorReturn(throwable -> new RestoreResult(false, throwable.getMessage())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRestoreAndNavigate, throwable -> view.onError(throwable.getMessage())));
    }

    @Override
    public void register(String name, String email, String password) {
        view.showLoading();
        compositeDisposable.add(authDataSource.register(name, email, password)
                .flatMap(user -> backupRestoreRepository.restoreCurrentUserData()
                        .map(restored -> new RestoreResult(restored, null))
                        .onErrorReturn(throwable -> new RestoreResult(false, throwable.getMessage())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRestoreAndNavigate, throwable -> view.onError(throwable.getMessage())));
    }

    @Override
    public void loginWithGoogle() {
        view.launchGoogleSignIn(authDataSource.getGoogleSignInIntent());
    }

    @Override
    public void completeGoogleLogin(Intent data) {
        view.showLoading();
        compositeDisposable.add(authDataSource.loginWithGoogle(data)
                .flatMap(user -> backupRestoreRepository.restoreCurrentUserData()
                        .map(restored -> new RestoreResult(restored, null))
                        .onErrorReturn(throwable -> new RestoreResult(false, throwable.getMessage())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRestoreAndNavigate, throwable -> view.onError(throwable.getMessage())));
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

    private void handleRestoreAndNavigate(RestoreResult restoreResult) {
        if (restoreResult.errorMessage != null && !restoreResult.errorMessage.trim().isEmpty()) {
            view.showMessage("Signed in. Restore failed");
        } else if (restoreResult.restored) {
            view.showMessage("Data restored successfully");
        }
        view.onSuccess();
    }

    private static class RestoreResult {
        private final boolean restored;
        private final String errorMessage;

        private RestoreResult(boolean restored, String errorMessage) {
            this.restored = restored;
            this.errorMessage = errorMessage;
        }
    }
}
