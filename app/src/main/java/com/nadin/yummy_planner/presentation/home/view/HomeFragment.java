package com.nadin.yummy_planner.presentation.home.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.auth.datasource.AuthDataSource;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentHomeBinding;
import com.nadin.yummy_planner.presentation.home.presenter.HomePresenter;
import com.nadin.yummy_planner.presentation.home.presenter.HomePresenterImpl;
import com.nadin.yummy_planner.utils.AuthRequiredDialog;
import com.nadin.yummy_planner.utils.SuccessDialog;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private RecyclerView popularMealsRecyclerView;
    private PopularMealsAdapter popularMealsAdapter;
    private HomePresenter presenter;
    private FragmentHomeBinding binding;
    private ImageView mealOfDayIV;
    private TextView mealOfDayTittle;
    private TextView mealOfDayDescription;
    private Button viewRecipe;
    private ConstraintLayout loadingLayout;
    private ConstraintLayout errorLayout;
    private MaterialCardView favButton;
    private SuccessDialog successDialog;
    private Meal currentMeal;
    private AuthDataSource authDataSource;
    private AuthRequiredDialog authRequiredDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealOfDayIV = binding.imageMealOfTheDay;
        mealOfDayTittle = binding.titleMeal;
        mealOfDayDescription = binding.descriptionMeal;
        loadingLayout = binding.loadingLayout;
        errorLayout = binding.errorLayout;
        viewRecipe = binding.buttonViewRecipe;
        popularMealsRecyclerView = binding.recyclerPopularMeals;
        popularMealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        favButton = view.findViewById(R.id.favorite_card_view);

        viewRecipe.setOnClickListener(v -> {
            Bundle bundle =  new Bundle();
            bundle.putSerializable("meal", currentMeal);
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_mealDetailsFragment, bundle);
        });

        successDialog = new SuccessDialog();
        authRequiredDialog = new AuthRequiredDialog();
        authDataSource = new AuthDataSource(requireContext());
        presenter = new HomePresenterImpl(this, getContext());

        popularMealsAdapter = new PopularMealsAdapter();
        popularMealsRecyclerView.setAdapter(popularMealsAdapter);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authDataSource.isGuestUser()) {
                    authRequiredDialog.show(requireContext(), () -> androidx.navigation.fragment.NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.authScreen));
                    return;
                }
                presenter.addMealToFav(currentMeal);
            }
        });

        presenter.getRandomMeal();
        presenter.getPopularMeals();
    }

    @Override
    public void showLoading() {
        loadingLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setVisibility(GONE);
    }

    @Override
    public void showError(String error) {
        errorLayout.setVisibility(VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setRandomMeal(Meal meal) {
        this.currentMeal = meal;
        mealOfDayTittle.setText(meal.getName());
        mealOfDayDescription.setText(meal.getCategory() + " | " + meal.getCountry());
        Glide.with(this)
                .load(meal.getImageUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(mealOfDayIV);
    }

    @Override
    public void setPopularMeals(List<Meal> meals) {
        popularMealsAdapter.setPopularMealList(meals);
    }

    @Override
    public void onMealAddToFavSuccess() {
        successDialog.show(requireContext(), getString(R.string.add_to_fav_success_msg));
    }
}
