package com.nadin.yummy_planner.presentation.auth.presenter;

import android.content.Intent;

public interface AuthContract {
    interface View{
        void showLoading();
        void launchGoogleSignIn(Intent signInIntent);
        void onSuccess();
        void onError(String message);
    }

    interface Presenter{
        void login(String email, String password);
        void register(String name, String email, String password);
        void loginWithGoogle();
        void completeGoogleLogin(Intent data);
        void continueAsGuest();
        void clear();
    }
}
