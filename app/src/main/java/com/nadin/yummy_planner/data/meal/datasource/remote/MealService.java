package com.nadin.yummy_planner.data.meal.datasource.remote;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("search.php?f=n")
    Call<MealResponse> getPopularMeals();
}
