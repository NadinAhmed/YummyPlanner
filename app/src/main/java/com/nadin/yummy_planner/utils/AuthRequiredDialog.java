package com.nadin.yummy_planner.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.nadin.yummy_planner.R;

public class AuthRequiredDialog {

    public void show(Context context, Runnable onAuthAction) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.login_required_title)
                .setMessage(R.string.login_required_dialog_message)
                .setPositiveButton(R.string.login_or_signup, (dialog, which) -> {
                    if (onAuthAction != null) {
                        onAuthAction.run();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .show();
    }
}
