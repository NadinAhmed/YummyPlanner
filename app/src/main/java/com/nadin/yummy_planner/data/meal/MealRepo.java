package com.nadin.yummy_planner.data.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.meal.datasource.local.MealLocalDatasource;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealNetworkResponse;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealRemoteDatasource;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

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

    public void addMealToPlanner(Meal meal, long date) {
        mealLocalDatasource.addMealToPlanner(meal, date);
    }

    public void removeMealFromPlanner(PlannerMeal meal) {
        mealLocalDatasource.removeMealFromPlanner(meal);
    }

    public LiveData<List<PlannerMeal>> getMealByDate(long date) {
        return mealLocalDatasource.getMealByDate(date);
    }
}
