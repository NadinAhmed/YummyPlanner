package com.nadin.yummy_planner.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.nadin.yummy_planner.R;



import com.nadin.yummy_planner.databinding.FragmentAuthBinding;

public class ViewStateController {

    private final FragmentAuthBinding binding;
    private final Context context;

    public ViewStateController(FragmentAuthBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    public void showError(String message) {
        binding.viewState.loadingLayout.setVisibility(View.GONE);
        binding.fragmentAuthContent.setVisibility(View.GONE);
        binding.viewState.errorLayout.setVisibility(View.VISIBLE);

        binding.viewState.errorMessageTv.setText(message);

        Animation anim = AnimationUtils.loadAnimation(
                context, R.anim.error_image_anim);
        binding.viewState.errorImage.startAnimation(anim);
    }

    public void showLoading() {
        binding.viewState.loadingLayout.setVisibility(View.VISIBLE);
        binding.viewState.errorLayout.setVisibility(View.GONE);
        binding.fragmentAuthContent.setVisibility(View.GONE);
    }
    public void showContent() {
        binding.viewState.loadingLayout.setVisibility(View.GONE);
        binding.viewState.errorLayout.setVisibility(View.GONE);
        binding.fragmentAuthContent.setVisibility(View.VISIBLE);
    }
}