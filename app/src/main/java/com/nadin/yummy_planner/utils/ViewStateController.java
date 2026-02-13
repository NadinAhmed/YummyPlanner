package com.nadin.yummy_planner.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nadin.yummy_planner.R;

import com.nadin.yummy_planner.databinding.FragmentAuthBinding;

public class ViewStateController {

    private final FragmentAuthBinding binding;
    private final Context context;
    private final View contentView;

    public ViewStateController(FragmentAuthBinding binding, Context context, View contentView) {
        this.binding = binding;
        this.context = context;
        this.contentView = contentView;
    }

    public void showError(String message) {
        binding.viewState.getRoot().setVisibility(View.VISIBLE);
        binding.viewState.loadingLayout.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        binding.viewState.errorLayout.setVisibility(View.VISIBLE);

        binding.viewState.errorMessageTv.setText(message);

        Animation anim = AnimationUtils.loadAnimation(
                context, R.anim.error_image_anim);
        binding.viewState.errorImage.startAnimation(anim);
    }

    public void showLoading() {
        binding.viewState.getRoot().setVisibility(View.VISIBLE);
        binding.viewState.loadingLayout.setVisibility(View.VISIBLE);
        binding.viewState.errorLayout.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
    }
    public void showContent() {
        binding.viewState.getRoot().setVisibility(View.GONE);
        binding.viewState.loadingLayout.setVisibility(View.GONE);
        binding.viewState.errorLayout.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }
}
