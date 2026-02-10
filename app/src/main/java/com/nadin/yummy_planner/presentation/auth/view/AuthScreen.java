package com.nadin.yummy_planner.presentation.auth.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.auth.mode.AuthMode;
import com.nadin.yummy_planner.databinding.FragmentAuthBinding;
import com.nadin.yummy_planner.presentation.auth.presenter.AuthContract;
import com.nadin.yummy_planner.presentation.auth.presenter.AuthPresenter;
import com.nadin.yummy_planner.utils.ViewStateController;

import java.util.Objects;


public class AuthScreen extends Fragment implements AuthContract.View {
    private AuthMode currentMode = AuthMode.LOGIN;
    private AuthPresenter presenter;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    private FragmentAuthBinding binding;

    ViewStateController viewStateController;

    public AuthScreen() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AuthPresenter(this, requireContext());
        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        presenter.completeGoogleLogin(result.getData());
                    } else {
                        onError(getString(R.string.google_sign_in_cancelled));
                    }
                }
        );
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
                requireContext(),
                binding.fragmentAuth
        );
        viewStateController.showContent();

        binding.btnFinishRegister.setOnClickListener(v -> {
            performAuthAction();
        });

        binding.btnGoogle.setOnClickListener(v -> presenter.loginWithGoogle());
        binding.btnGuest.setOnClickListener(v -> presenter.continueAsGuest());

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
            binding.authImageView.setImageResource(R.drawable.login);
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
    public void launchGoogleSignIn(Intent signInIntent) {
        googleSignInLauncher.launch(signInIntent);
    }

    @Override
    public void onSuccess() {
        Log.d("AuthScreen", "onSuccess: ");
        //navigate to main screen
        androidx.navigation.fragment.NavHostFragment.findNavController(this).navigate(R.id.action_authScreen_to_homeFragment);
    }

    @Override
    public void onError(String message) {
        Log.d("AuthScreen", "onError: " + message);
        viewStateController.showError(message);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        presenter.clear();
        super.onDestroy();
    }
}
