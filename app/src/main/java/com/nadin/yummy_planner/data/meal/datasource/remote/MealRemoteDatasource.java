package com.nadin.yummy_planner.data.meal.datasource.remote;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.network.Network;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MealRemoteDatasource {
    MealService api;

    public MealRemoteDatasource() {
        api = Network.getInstance().getMealService();
    }

    public Single<Meal> getRandomMeal() {
        return api.getRandomMeal()
                .map(response -> getFirstMeal(response.getMeals()));
    }

    public Single<List<Meal>> getPopularMeals() {
        return api.getPopularMeals()
                .map(response -> getMealsOrEmpty(response.getMeals()));
    }

    public Single<Meal> getMealById(String id) {
        return api.getMealById(id)
                .map(response -> getFirstMeal(response.getMeals()));
    }

    public Single<List<Meal>> searchMealsByName(String name) {
        return api.searchMealsByName(name)
                .map(response -> getMealsOrEmpty(response.getMeals()));
    }

    public Single<List<Meal>> searchMealsByIngredient(String ingredient) {
        return api.searchMealsByIngredient(ingredient)
                .map(response -> getMealsOrEmpty(response.getMeals()));
    }

    public Single<List<Meal>> searchMealsByCategory(String category) {
        return api.searchMealsByCategory(category)
                .map(response -> getMealsOrEmpty(response.getMeals()));
    }

    public Single<List<Meal>> searchMealsByCountry(String country) {
        return api.searchMealsByCountry(country)
                .map(response -> getMealsOrEmpty(response.getMeals()));
    }

    private Meal getFirstMeal(List<Meal> meals) throws Exception {
        if (meals == null || meals.isEmpty()) {
            throw new Exception("Meal not found");
        }
        return meals.get(0);
    }

    private List<Meal> getMealsOrEmpty(List<Meal> meals) {
        if (meals == null) {
            return Collections.emptyList();
        }
        return meals;
    }
}
