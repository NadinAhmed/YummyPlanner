package com.nadin.yummy_planner.data.meal;

import com.nadin.yummy_planner.data.meal.datasource.remote.MealNetworkResponse;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealRemoteDatasource;

public class MealRepo {
    MealRemoteDatasource mealRemoteDatasource;

    public MealRepo(){
        mealRemoteDatasource = new MealRemoteDatasource();
    }

    public void getRandomMeal(MealNetworkResponse response){
        mealRemoteDatasource.getRandomMeal(response);
    }
}
