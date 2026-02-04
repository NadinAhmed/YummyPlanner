package com.nadin.yummy_planner.presentation.home.view;

import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

public interface HomeView {
    void showLoading();
    void hideLoading();
    void showError(String error);
    void setRandomMeal(Meal meal);
    void setPopularMeals(List<Meal> meals);
    void onMealAddToFavSuccess();
}
