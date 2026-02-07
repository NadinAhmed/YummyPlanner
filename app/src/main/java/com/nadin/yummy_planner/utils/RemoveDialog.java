package com.nadin.yummy_planner.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.presentation.favourite.view.OnDeleteClickListener;

public class RemoveDialog {
    private Dialog dialog;
    private TextView dialogMessage;
    private Button confirmBtn;
    private Button cancelBtn;
    private OnConfirmDelete onConfirmDelete;

    public void show(Context context, String message,  OnConfirmDelete listener) {
        this.onConfirmDelete = listener;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.remove_dialog);
        dialogMessage = dialog.findViewById(R.id.remove_dialog_msg);
        confirmBtn = dialog.findViewById(R.id.confirm_btn);
        cancelBtn = dialog.findViewById(R.id.cancel_btn);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.error_image_anim;

        dialogMessage.setText(message);
        confirmBtn.setOnClickListener(v -> {
            onConfirmDelete.onConfirm();
            dialog.dismiss();
        });

        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}

