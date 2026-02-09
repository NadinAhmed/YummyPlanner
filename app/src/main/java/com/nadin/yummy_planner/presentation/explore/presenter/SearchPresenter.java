package com.nadin.yummy_planner.presentation.explore.presenter;

public interface SearchPresenter {
        void searchMealsByName(String name);
        void searchMealsByIngredient(String ingredient);
        void searchMealsByCategory(String category);
        void searchMealsByCountry(String area);
}
