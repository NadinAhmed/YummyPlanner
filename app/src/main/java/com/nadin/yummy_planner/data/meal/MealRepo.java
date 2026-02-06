package com.nadin.yummy_planner.data.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.meal.datasource.local.MealLocalDatasource;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealNetworkResponse;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealRemoteDatasource;
import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

public class MealRepo {
    MealRemoteDatasource mealRemoteDatasource;
    MealLocalDatasource mealLocalDatasource;

    public MealRepo(Context context){
        mealRemoteDatasource = new MealRemoteDatasource();
        mealLocalDatasource = new MealLocalDatasource(context);
    }

    public void getRandomMeal(MealNetworkResponse response){
        mealRemoteDatasource.getRandomMeal(response);
    }

    public void getPopularMeals(MealNetworkResponse response){
        mealRemoteDatasource.getPopularMeals(response);
    }

    public void addToFavourite(Meal meal){
        mealLocalDatasource.addToFavourite(meal);
    }

    public void deleteFromFavourite(Meal meal){
        mealLocalDatasource.removeFromFavourite(meal);
    }

    public LiveData<List<Meal>> getAllFavMeals(){
        return mealLocalDatasource.getAllFavMeals();
    }
}
