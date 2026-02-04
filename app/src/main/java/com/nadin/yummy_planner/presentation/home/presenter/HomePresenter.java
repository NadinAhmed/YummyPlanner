package com.nadin.yummy_planner.presentation.home.presenter;

import com.nadin.yummy_planner.data.meal.model.Meal;

public interface HomePresenter {
    void getRandomMeal();
    void addMealToFav(Meal meal);
    void getPopularMeals();
}
