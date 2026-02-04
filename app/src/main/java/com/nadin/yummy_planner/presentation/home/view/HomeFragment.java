package com.nadin.yummy_planner.presentation.home.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentHomeBinding;
import com.nadin.yummy_planner.presentation.home.presenter.HomePresenter;
import com.nadin.yummy_planner.presentation.home.presenter.HomePresenterImpl;
import com.nadin.yummy_planner.utils.ViewStateController;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private RecyclerView popularMealsRecyclerView;
    private PopularMealsAdapter popularMealsAdapter;
    private HomePresenter presenter;
    private FragmentHomeBinding binding;
    private ImageView mealOfDayIV;
    private TextView mealOfDayTittle;
    private TextView mealOfDayDescription;
    private ConstraintLayout loadingLayout;
    private ConstraintLayout errorLayout;
    private List<Meal> popularMeals;


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
        popularMealsRecyclerView = binding.recyclerPopularMeals;
        popularMealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        presenter = new HomePresenterImpl(this);


        // Dummy data
        popularMeals = new ArrayList<>();
        popularMeals.add(new Meal("1", "Avocado Toast", "https://images.unsplash.com/photo-1484723051597-62b8a788a6ac?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Breakfast", "International", "Instructions here", "", new ArrayList<>()));
        popularMeals.add(new Meal("2", "Berry Smoothie", "https://images.unsplash.com/photo-1542884748-2b87b36c6b90?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Breakfast", "International", "Instructions here", "", new ArrayList<>()));
        popularMeals.add(new Meal("3", "Pancakes", "https://images.unsplash.com/photo-1528207776546-365bb710ee93?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Breakfast", "International", "Instructions here", "", new ArrayList<>()));


        popularMealsAdapter = new PopularMealsAdapter();
        popularMealsRecyclerView.setAdapter(popularMealsAdapter);

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
        mealOfDayTittle.setText(meal.getName());
        mealOfDayDescription.setText(meal.getCategory() + " | " + meal.getCountry());
        Glide.with(this)
                .load(meal.getImageUrl())
                .placeholder(R.drawable.logo_with_background)
                .error(R.drawable.logo_with_background)
                .into(mealOfDayIV);
    }

    @Override
    public void setPopularMeals(List<Meal> meals) {
        popularMealsAdapter.setPopularMealList(meals);
    }

    @Override
    public void onMealAddToFavSuccess() {

    }
}
