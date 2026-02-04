package com.nadin.yummy_planner.data.meal.datasource.remote;

import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

public interface MealNetworkResponse {
    public void onSuccess(List<Meal> meal);
    public void onError(String error);
}
