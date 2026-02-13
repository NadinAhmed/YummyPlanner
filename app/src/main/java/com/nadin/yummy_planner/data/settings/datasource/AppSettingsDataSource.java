package com.nadin.yummy_planner.data.settings.datasource;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.LocaleList;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

public class AppSettingsDataSource {
    private static final String PREFS_APP_SETTINGS = "app_settings";
    private static final String KEY_DARK_THEME_ENABLED = "dark_theme_enabled";
    private static final String KEY_LANGUAGE_CODE = "language_code";
    private static final String LANGUAGE_ARABIC = "ar";
    private static final String LANGUAGE_ENGLISH = "en";

    private final SharedPreferences sharedPreferences;

    public AppSettingsDataSource(Context context) {
        Context applicationContext = context.getApplicationContext();
        sharedPreferences = applicationContext.getSharedPreferences(PREFS_APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public void setDarkThemeEnabled(boolean isEnabled) {
        sharedPreferences.edit().putBoolean(KEY_DARK_THEME_ENABLED, isEnabled).apply();
        AppCompatDelegate.setDefaultNightMode(
                isEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    public boolean isDarkThemeEnabledSync() {
        return sharedPreferences.getBoolean(KEY_DARK_THEME_ENABLED, false);
    }

    public void setLanguageCode(String languageCode) {
        String normalizedLanguageCode = normalizeLanguageCode(languageCode);
        sharedPreferences.edit().putString(KEY_LANGUAGE_CODE, normalizedLanguageCode).apply();
        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(normalizedLanguageCode)
        );
    }

    public String getLanguageCodeSync() {
        String persistedLanguage = sharedPreferences.getString(KEY_LANGUAGE_CODE, null);
        if (persistedLanguage != null) {
            return normalizeLanguageCode(persistedLanguage);
        }

        LocaleListCompat appLocales = AppCompatDelegate.getApplicationLocales();
        String appLanguageTag = appLocales.toLanguageTags();
        if (appLanguageTag != null && !appLanguageTag.isEmpty()) {
            return normalizeLanguageCode(appLanguageTag.split(",")[0]);
        }

        Locale systemLocale = LocaleList.getDefault().get(0);
        return normalizeLanguageCode(systemLocale.getLanguage());
    }

    private String normalizeLanguageCode(String languageCode) {
        if (languageCode == null) {
            return LANGUAGE_ENGLISH;
        }
        return LANGUAGE_ARABIC.equalsIgnoreCase(languageCode) ? LANGUAGE_ARABIC : LANGUAGE_ENGLISH;
    }
}
