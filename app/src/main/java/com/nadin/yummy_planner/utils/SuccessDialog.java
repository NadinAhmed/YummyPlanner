package com.nadin.yummy_planner.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nadin.yummy_planner.R;

public class SuccessDialog {
    private Dialog dialog;
    private TextView dialogMessage;

    public void show(Context context, String message) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.success_dialog);
        dialogMessage = dialog.findViewById(R.id.success_dialog_msg);
        dialogMessage.setText(message);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.error_image_anim;
        dialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            };
        }, 1500);
    }

}
