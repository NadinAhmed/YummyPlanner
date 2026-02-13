package com.nadin.yummy_planner.presentation.explore.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.presentation.explore.view.SearchView;

import java.net.UnknownHostException;


public class SearchPresenterImpl implements SearchPresenter {
    SearchView searchView;
    MealRepo mealRepo;
    Context context;

    public SearchPresenterImpl(SearchView searchView, Context context) {
        this.searchView = searchView;
        this.context = context;
        mealRepo = new MealRepo(context);
    }

    private void handleSearchError(Throwable error) {
        if (error instanceof UnknownHostException) {
            searchView.displayError("No internet connection.\nPlease check your network.");
        } else {
            searchView.displayError(error.getMessage());
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByName(String name) {
        mealRepo.searchMealsByName(name).subscribe(
                searchView::displayMeals,
                this::handleSearchError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByIngredient(String ingredient) {
        mealRepo.searchMealsByIngredient(ingredient).subscribe(
                searchView::displayMeals,
                this::handleSearchError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByCategory(String category) {
        mealRepo.searchMealsByCategory(category).subscribe(
                searchView::displayMeals,
                this::handleSearchError);

    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByCountry(String area) {
        mealRepo.searchMealsByCountry(area).subscribe(
                searchView::displayMeals,
                this::handleSearchError);
    }
}

