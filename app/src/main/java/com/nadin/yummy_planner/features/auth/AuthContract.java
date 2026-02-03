package com.nadin.yummy_planner.features.auth;

public interface AuthContract {
    interface View{
        void showLoading();
        void onSuccess();
        void onError(String message);
    }

    interface Presenter{
        void login(String email, String password);
        void register(String name, String email, String password);
    }
}
