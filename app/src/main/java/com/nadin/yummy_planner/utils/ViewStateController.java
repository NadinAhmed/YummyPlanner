package com.nadin.yummy_planner.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.nadin.yummy_planner.R;



import com.nadin.yummy_planner.databinding.FragmentAuthBinding;

public class ViewStateController {

    private final FragmentAuthBinding binding;
    private final Context context;
    private final FrameLayout contentViewId;

    public ViewStateController(FragmentAuthBinding binding, Context context, FrameLayout contentViewId) {
        this.binding = binding;
        this.context = context;
        this.contentViewId = contentViewId;
    }

    public void showError(String message) {
        binding.viewState.loadingLayout.setVisibility(View.GONE);
        contentViewId.setVisibility(View.GONE);
        binding.viewState.errorLayout.setVisibility(View.VISIBLE);

        binding.viewState.errorMessageTv.setText(message);

        Animation anim = AnimationUtils.loadAnimation(
                context, R.anim.error_image_anim);
        binding.viewState.errorImage.startAnimation(anim);
    }

    public void showLoading() {
        binding.viewState.loadingLayout.setVisibility(View.VISIBLE);
        binding.viewState.errorLayout.setVisibility(View.GONE);
        contentViewId.setVisibility(View.GONE);
    }
    public void showContent() {
        binding.viewState.loadingLayout.setVisibility(View.GONE);
        binding.viewState.errorLayout.setVisibility(View.GONE);
        contentViewId.setVisibility(View.VISIBLE);
    }
}