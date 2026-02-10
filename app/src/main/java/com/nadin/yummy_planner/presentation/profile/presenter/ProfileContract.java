package com.nadin.yummy_planner.presentation.profile.presenter;

public interface ProfileContract {
    interface View {
        void showSignedInState(String userName);
        void showGuestState();
        void setDarkThemeEnabled(boolean isEnabled);
        void setSelectedLanguage(String languageCode);
        void navigateToAuth();
        void showError(String message);
    }

    interface Presenter {
        void loadProfileState();
        void onLogoutClicked();
        void onSignInClicked();
        void onDarkThemeChanged(boolean isEnabled);
        void onLanguageSelected(String languageCode);
        void clear();
    }
}
