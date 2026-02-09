package com.nadin.yummy_planner.presentation.explore.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentExploreBinding;
import com.nadin.yummy_planner.presentation.explore.presenter.SearchPresenter;
import com.nadin.yummy_planner.presentation.explore.presenter.SearchPresenterImpl;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment implements SearchView {

    private FragmentExploreBinding binding;
    private RecyclerView mealsResultRecyclerView;
    private SearchAdapter searchAdapter;
    private SearchPresenter presenter;
    private EditText searchEditText;
    private TextView searchTypeView;
    private Button categoryButton;
    private Button countryButton;
    private Button ingredientButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealsResultRecyclerView = binding.searchResultRecycleView;
        mealsResultRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        searchAdapter = new SearchAdapter();
        mealsResultRecyclerView.setAdapter(searchAdapter);
        presenter = new SearchPresenterImpl(this, getContext());

        searchEditText = binding.searchInput;
        categoryButton = binding.categoriesButton;
        countryButton = binding.countriesButton;
        ingredientButton = binding.ingredientsButton;
        searchTypeView = binding.searchingTypeTv;

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchEditText.getText().toString();
                showLoadingState();
                presenter.searchMealsByName(query);
                searchTypeView.setText(getString(R.string.meals));
                return true;
            }
            return false;
        });

        categoryButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            showLoadingState();
            presenter.searchMealsByCategory(query);
            searchTypeView.setText(getString(R.string.categories));
        });

        countryButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            showLoadingState();
            presenter.searchMealsByCountry(query);
            searchTypeView.setText(getString(R.string.countries));
        });

        ingredientButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            showLoadingState();
            presenter.searchMealsByIngredient(query);
            searchTypeView.setText(getString(R.string.ingredients));
        });
    }

    private void showLoadingState() {
        binding.loadingView.setVisibility(View.VISIBLE);
        binding.messageView.setVisibility(View.GONE);
        binding.searchResultRecycleView.setVisibility(View.GONE);
    }

    @Override
    public void displayMeals(List<Meal> meals) {
        binding.loadingView.setVisibility(View.GONE);
        if (meals != null && !meals.isEmpty()) {
            binding.searchResultRecycleView.setVisibility(View.VISIBLE);
            binding.messageView.setVisibility(View.GONE);
            searchAdapter.setMeals(meals);
        } else {
            binding.searchResultRecycleView.setVisibility(View.GONE);
            binding.messageView.setVisibility(View.VISIBLE);
            binding.messageView.setText("No meals found");
            searchAdapter.setMeals(new ArrayList<>());
        }
    }

    @Override
    public void displayError(String message) {
        binding.loadingView.setVisibility(View.GONE);
        binding.searchResultRecycleView.setVisibility(View.GONE);
        binding.messageView.setVisibility(View.VISIBLE);
        binding.messageView.setText(message);
        searchAdapter.setMeals(new ArrayList<>());
    }
}
