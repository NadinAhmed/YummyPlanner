package com.nadin.yummy_planner.data.meal;

import android.content.Context;

import com.nadin.yummy_planner.data.meal.datasource.local.MealLocalDatasource;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealRemoteDatasource;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepo {
    MealRemoteDatasource mealRemoteDatasource;
    MealLocalDatasource mealLocalDatasource;

    public MealRepo(Context context) {
        mealRemoteDatasource = new MealRemoteDatasource();
        mealLocalDatasource = new MealLocalDatasource(context);
    }

    public Single<Meal> getRandomMeal() {
        return mealRemoteDatasource.getRandomMeal()
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Meal>> getPopularMeals() {
        return mealRemoteDatasource.getPopularMeals()
                .subscribeOn(Schedulers.io());
    }

    public Completable addToFavourite(Meal meal) {
        return mealLocalDatasource.addToFavourite(meal)
                .subscribeOn(Schedulers.io());
    }

    public Completable deleteFromFavourite(Meal meal) {
        return mealLocalDatasource.removeFromFavourite(meal)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Meal>> getAllFavMeals() {
        return mealLocalDatasource.getAllFavMeals();
    }

    public Completable addMealToPlanner(Meal meal, long date) {
        return mealLocalDatasource.addMealToPlanner(meal, date)
                .subscribeOn(Schedulers.io());
    }

    public Completable removeMealFromPlanner(PlannerMeal meal) {
        return mealLocalDatasource.removeMealFromPlanner(meal)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<PlannerMeal>> getMealByDate(long date) {
        return mealLocalDatasource.getMealByDate(date);
    }

    public Single<List<Meal>> getAllFavMealsOnce() {
        return mealLocalDatasource.getAllFavMealsOnce()
                .subscribeOn(Schedulers.io());
    }

    public Single<List<PlannerMeal>> getAllPlannerMealsOnce() {
        return mealLocalDatasource.getAllPlannerMealsOnce()
                .subscribeOn(Schedulers.io());
    }

    public Completable replaceAllLocalData(List<Meal> favourites, List<PlannerMeal> plannerMeals) {
        return mealLocalDatasource.replaceAllLocalData(favourites, plannerMeals)
                .subscribeOn(Schedulers.io());
    }

    public Completable clearAllLocalData() {
        return mealLocalDatasource.clearAllLocalData()
                .subscribeOn(Schedulers.io());
    }

    public Single<Meal> getMealById(String id) {
        return mealRemoteDatasource.getMealById(id)
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Meal>> searchMealsByName(String name) {
        return mealRemoteDatasource.searchMealsByName(name)
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Meal>> searchMealsByIngredient(String ingredient) {
        return mealRemoteDatasource.searchMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Meal>> searchMealsByCategory(String category) {
        return mealRemoteDatasource.searchMealsByCategory(category)
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Meal>> searchMealsByCountry(String area) {
        return mealRemoteDatasource.searchMealsByCountry(area)
                .subscribeOn(Schedulers.io());
    }
}
