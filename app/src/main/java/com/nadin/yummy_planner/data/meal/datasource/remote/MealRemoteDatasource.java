package com.nadin.yummy_planner.data.meal.datasource.remote;

import android.util.Log;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.MealResponse;
import com.nadin.yummy_planner.data.network.Network;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDatasource {
    MealService api;

    public MealRemoteDatasource() {
        api = Network.getInstance().getMealService();
    }

    public void getRandomMeal(MealNetworkResponse callback) {
        api.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                    Log.d("MealRemoteDatasource: ", "onResponse success: " + response.body().getMeals().get(0).getName());
                } else {
                    callback.onError("Error Code: " + response.code());
                    Log.d("MealRemoteDatasource: ", "onResponse: Error Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                if (t instanceof java.io.IOException) {
                    callback.onError("Network failure, please try again");
                    Log.d("MealRemoteDatasource: ", "onFailure: Network failure, please try again");
                } else {
                    callback.onError(t.getMessage());
                    Log.d("MealRemoteDatasource: ", "onFailure: " + t.getMessage());
                }
            }
        });
    }

    public void getPopularMeals(MealNetworkResponse callback) {
        api.getPopularMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                    Log.d("MealRemoteDatasource: ", "onResponse success: " + response.body().getMeals().size() + " meals fetched.");
                } else {
                    callback.onError("Error Code: " + response.code());
                    Log.d("MealRemoteDatasource: ", "onResponse: Error Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                if (t instanceof java.io.IOException) {
                    callback.onError("Network failure, please try again");
                    Log.d("MealRemoteDatasource: ", "onFailure: Network failure, please try again");
                } else {
                    callback.onError(t.getMessage());
                    Log.d("MealRemoteDatasource: ", "onFailure: " + t.getMessage());
                }
            }
        });
    }

    public Single<Meal> getMealById(String id) {
        return api.getMealById(id)
                .map(response -> {
                    if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                        return response.getMeals().get(0);
                    } else {
                        throw new Exception("Meal not found");
                    }
                });
    }
    public Single<List<Meal>> searchMealsByName(String name) {
        return api.searchMealsByName(name)
                .map(response -> response.getMeals() != null ? response.getMeals() : Collections.emptyList());
    }

    public Single<List<Meal>> searchMealsByIngredient(String ingredient) {
        return api.searchMealsByIngredient(ingredient)
                .map(response -> response.getMeals() != null ? response.getMeals() : Collections.emptyList());
    }

    public Single<List<Meal>> searchMealsByCategory(String category) {
        return api.searchMealsByCategory(category)
                .map(response -> response.getMeals() != null ? response.getMeals() : Collections.emptyList());
    }

    public Single<List<Meal>> searchMealsByCountry(String country) {
        return api.searchMealsByCountry(country)
                .map(response -> response.getMeals() != null ? response.getMeals() : Collections.emptyList());
    }
}
