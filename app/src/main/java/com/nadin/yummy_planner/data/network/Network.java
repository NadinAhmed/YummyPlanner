package com.nadin.yummy_planner.data.network;

import com.nadin.yummy_planner.data.meal.datasource.remote.MealService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Network instance;
    private static Retrofit retrofit;

    private Network() {
    }

    public static Network getInstance() {
        if (instance == null) {
            instance = new Network();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    public MealService getMealService(){
        return retrofit.create(MealService.class);
    }
}
