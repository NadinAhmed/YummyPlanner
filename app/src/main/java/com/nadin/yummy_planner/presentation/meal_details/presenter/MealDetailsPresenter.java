package com.nadin.yummy_planner.presentation.meal_details.presenter;

import com.nadin.yummy_planner.data.meal.model.Meal;

public interface MealDetailsPresenter {
    void addMealToFav(Meal meal);
    void addMealToPlan(Meal meal, long date);
}
