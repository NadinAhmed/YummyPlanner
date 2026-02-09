package com.nadin.yummy_planner.presentation.explore.view;

import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

public interface SearchView {
        void displayMeals(List<Meal> meals);
        void displayError(String message);
}
