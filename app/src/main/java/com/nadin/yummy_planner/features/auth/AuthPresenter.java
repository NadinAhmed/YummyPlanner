package com.nadin.yummy_planner.features.auth;

import com.nadin.yummy_planner.models.User;
import com.nadin.yummy_planner.utils.AuthCallback;

public class AuthPresenter implements AuthContract.Presenter{

    private AuthContract.View view;
    private AuthDataSource authDataSource;

    private final AuthCallback authCallback = new AuthCallback() {
        @Override
        public void onSuccess(User user) {
            view.onSuccess();
        }

        @Override
        public void onError(String message) {
            view.onError(message);
        }
    };

    public AuthPresenter(AuthContract.View view){
        this.view = view;
        authDataSource = new AuthDataSource();
    }
    @Override
    public void login(String email, String password) {
        view.showLoading();
        authDataSource.login(email, password, authCallback);
    }

    @Override
    public void register(String name, String email, String password) {
        view.showLoading();
        authDataSource.register(name, email, password, authCallback);
    }
}
