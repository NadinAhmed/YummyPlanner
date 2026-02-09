package com.nadin.yummy_planner.presentation.explore.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.presentation.explore.view.SearchView;


public class SearchPresenterImpl implements SearchPresenter {
    SearchView searchView;
    MealRepo mealRepo;

    public SearchPresenterImpl(SearchView searchView, Context context) {
        this.searchView = searchView;
        mealRepo = new MealRepo(context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByName(String name) {
        mealRepo.searchMealsByName(name).subscribe(
                meals -> {
                     searchView.displayMeals(meals);
                }, error -> {
                    searchView.displayError(error.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByIngredient(String ingredient) {
        mealRepo.searchMealsByIngredient(ingredient).subscribe(
                meals -> {
                    searchView.displayMeals(meals);
                }, error -> {
                    searchView.displayError(error.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByCategory(String category) {
        mealRepo.searchMealsByCategory(category).subscribe(
                meals -> {
                    searchView.displayMeals(meals);
                }, error -> {
                    searchView.displayError(error.getMessage());
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMealsByCountry(String area) {
        mealRepo.searchMealsByCountry(area).subscribe(
                meals -> {
                    searchView.displayMeals(meals);
                }, error -> {
                    searchView.displayError(error.getMessage());
                });
    }
}

