package com.nadin.yummy_planner.presentation.profile.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.databinding.FragmentProfileBinding;
import com.nadin.yummy_planner.presentation.profile.presenter.ProfileContract;
import com.nadin.yummy_planner.presentation.profile.presenter.ProfilePresenter;
import com.nadin.yummy_planner.utils.RemoveDialog;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private static final String LANGUAGE_ENGLISH = "en";
    private static final String LANGUAGE_ARABIC = "ar";

    private FragmentProfileBinding binding;
    private ProfilePresenter presenter;
    private RemoveDialog removeDialog;
    private boolean ignoreDarkThemeCallback = false;
    private boolean ignoreLanguageCallback = false;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProfilePresenter(this, requireContext());
        removeDialog = new RemoveDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupLanguageSelector();

        binding.btnLogout.setOnClickListener(v -> removeDialog.show(
                requireContext(),
                getString(R.string.logout_confirmation_message),
                () -> presenter.onLogoutClicked()
        ));
        binding.btnSignIn.setOnClickListener(v -> presenter.onSignInClicked());
        binding.darkThemeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (ignoreDarkThemeCallback) {
                return;
            }
            presenter.onDarkThemeChanged(isChecked);
        });

        presenter.loadProfileState();
    }

    private void setupLanguageSelector() {
        String[] languageLabels = new String[]{
                getString(R.string.english),
                getString(R.string.arabic)
        };

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                languageLabels
        );

        binding.languageSelector.setAdapter(languageAdapter);
        binding.languageSelector.setThreshold(0);
        binding.languageSelector.setOnClickListener(v -> binding.languageSelector.showDropDown());
        binding.languageSelectorLayout.setEndIconOnClickListener(v -> binding.languageSelector.showDropDown());
        binding.languageSelector.setOnItemClickListener((parent, view, position, id) -> {
            if (ignoreLanguageCallback) {
                return;
            }
            if (position == 1) {
                presenter.onLanguageSelected(LANGUAGE_ARABIC);
            } else {
                presenter.onLanguageSelected(LANGUAGE_ENGLISH);
            }
        });
    }

    @Override
    public void showSignedInState(String userName) {
        binding.guestStateLayout.setVisibility(View.GONE);
        binding.signedInStateLayout.setVisibility(View.VISIBLE);
        binding.userNameValue.setText(userName);
    }

    @Override
    public void showGuestState() {
        binding.signedInStateLayout.setVisibility(View.GONE);
        binding.guestStateLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDarkThemeEnabled(boolean isEnabled) {
        ignoreDarkThemeCallback = true;
        binding.darkThemeSwitch.setChecked(isEnabled);
        ignoreDarkThemeCallback = false;
    }

    @Override
    public void setSelectedLanguage(String languageCode) {
        ignoreLanguageCallback = true;
        if (LANGUAGE_ARABIC.equals(languageCode)) {
            binding.languageSelector.setText(getString(R.string.arabic), false);
        } else {
            binding.languageSelector.setText(getString(R.string.english), false);
        }
        ignoreLanguageCallback = false;
    }

    @Override
    public void navigateToAuth() {
        NavHostFragment.findNavController(this).navigate(R.id.authScreen);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        presenter.clear();
        super.onDestroy();
    }
}
