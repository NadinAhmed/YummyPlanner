package com.nadin.yummy_planner.data.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.meal.datasource.local.MealLocalDatasource;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealNetworkResponse;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealRemoteDatasource;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepo {
    MealRemoteDatasource mealRemoteDatasource;
    MealLocalDatasource mealLocalDatasource;

    public MealRepo(Context context) {
        mealRemoteDatasource = new MealRemoteDatasource();
        mealLocalDatasource = new MealLocalDatasource(context);
    }

    public void getRandomMeal(MealNetworkResponse response) {
        mealRemoteDatasource.getRandomMeal(response);
    }

    public void getPopularMeals(MealNetworkResponse response) {
        mealRemoteDatasource.getPopularMeals(response);
    }

    public void addToFavourite(Meal meal) {
        mealLocalDatasource.addToFavourite(meal);
    }

    public void deleteFromFavourite(Meal meal) {
        mealLocalDatasource.removeFromFavourite(meal);
    }

    public LiveData<List<Meal>> getAllFavMeals() {
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

    public List<Meal> getAllFavMealsSync() {
        return mealLocalDatasource.getAllFavMealsSync();
    }

    public List<PlannerMeal> getAllPlannerMealsSync() {
        return mealLocalDatasource.getAllPlannerMealsSync();
    }

    public void replaceAllLocalData(List<Meal> favourites, List<PlannerMeal> plannerMeals) {
        mealLocalDatasource.replaceAllLocalData(favourites, plannerMeals);
    }

    public void clearAllLocalData() {
        mealLocalDatasource.clearAllLocalData();
    }

    public Single<Meal> getMealById(String id) {
        return mealRemoteDatasource.getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<List<Meal>> searchMealsByName(String name) {
        return mealRemoteDatasource.searchMealsByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Meal>> searchMealsByIngredient(String ingredient) {
        return mealRemoteDatasource.searchMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Meal>> searchMealsByCategory(String category) {
        return mealRemoteDatasource.searchMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Meal>> searchMealsByCountry(String area) {
        return mealRemoteDatasource.searchMealsByCountry(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
