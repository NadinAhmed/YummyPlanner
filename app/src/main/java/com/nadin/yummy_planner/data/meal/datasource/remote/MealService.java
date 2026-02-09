package com.nadin.yummy_planner.data.meal.datasource.remote;

import com.nadin.yummy_planner.data.meal.model.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("search.php?f=n")
    Call<MealResponse> getPopularMeals();

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") String id);

    @GET("search.php")
    Single<MealResponse> searchMealsByName(@Query("s") String name);

    @GET("filter.php")
    Single<MealResponse> searchMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<MealResponse> searchMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealResponse> searchMealsByCountry(@Query("a") String area);
}