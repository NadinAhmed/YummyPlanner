package com.nadin.yummy_planner.features.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.databinding.FragmentAuthBinding;
import com.nadin.yummy_planner.utils.ViewStateController;

import java.util.Objects;


public class AuthScreen extends Fragment implements AuthContract.View {
    private AuthMode currentMode = AuthMode.LOGIN;
    private AuthPresenter presenter;

    private FragmentAuthBinding binding;

    ViewStateController viewStateController;

    public AuthScreen() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AuthPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewStateController = new ViewStateController(
                binding,
                requireContext()
        );
        viewStateController.showContent();

        binding.btnFinishRegister.setOnClickListener(v -> {
            performAuthAction();
        });

        binding.viewState.btnRetry.setOnClickListener(v -> {
            performAuthAction();
        });


        binding.toggleBtnGroup.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
                if (position == 0) {
                    switchMode(AuthMode.LOGIN);
                } else {
                    switchMode(AuthMode.REGISTER);
                }
            }
        });
    }

    private void performAuthAction() {
        String email = Objects.requireNonNull(binding.emailTextInput.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passTextInput.getText()).toString().trim();
        if (currentMode == AuthMode.LOGIN) {
            presenter.login(email, password);
        } else {
            String name = Objects.requireNonNull(binding.nameTextInput.getText()).toString().trim();
            presenter.register(name, email, password);
        }
    }

    private void switchMode(AuthMode mode) {
        currentMode = mode;

        if (mode == AuthMode.LOGIN) {
            binding.authImageView.setImageResource(R.drawable.login);
            binding.authLabelTv.setText(R.string.lets_get_cooking);
            binding.nameTextInput.setVisibility(View.GONE);
            binding.nameTv.setVisibility(View.GONE);
            binding.nameTextLayout.setVisibility(View.GONE);
            binding.btnFinishRegister.setText(R.string.log_in);
        } else {
            binding.authImageView.setImageResource(R.drawable.signin_image);
            binding.authLabelTv.setText(R.string.join_our_family);
            binding.nameTextInput.setVisibility(View.VISIBLE);
            binding.nameTv.setVisibility(View.VISIBLE);
            binding.nameTextLayout.setVisibility(View.VISIBLE);
            binding.btnFinishRegister.setText(R.string.sign_up);
        }
    }

    @Override
    public void showLoading() {
        Log.d("AuthScreen", "onLoading: " );
        viewStateController.showLoading();
    }

    @Override
    public void onSuccess() {
        Log.d("AuthScreen", "onSuccess: ");
        //navigate to main screen

    }

    @Override
    public void onError(String message) {
        Log.d("AuthScreen", "onError: " + message);
        viewStateController.showError(message);
    }
}