package com.nadin.yummy_planner.data.meal.datasource.remote;

import android.util.Log;

import com.nadin.yummy_planner.data.meal.model.MealResponse;
import com.nadin.yummy_planner.data.network.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDatasource {
    MealService api;

    public MealRemoteDatasource(){
        api = Network.getInstance().getMealService();
    }

    public void getRandomMeal(MealNetworkResponse callback){
        api.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body().getMeals());
                    Log.d("MealRemoteDatasource: ", "onResponse success: " + response.body().getMeals().get(0).getName());
                }else{
                    callback.onError("Error Code: " + response.code());
                    Log.d("MealRemoteDatasource: ", "onResponse: Error Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                if(t instanceof java.io.IOException){
                    callback.onError("Network failure, please try again");
                    Log.d("MealRemoteDatasource: ", "onFailure: Network failure, please try again");
                } else {
                    callback.onError(t.getMessage());
                    Log.d("MealRemoteDatasource: ", "onFailure: " + t.getMessage());
                }
            }
        });
    }
}
