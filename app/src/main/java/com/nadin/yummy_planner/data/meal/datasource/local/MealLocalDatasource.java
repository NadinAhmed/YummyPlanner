package com.nadin.yummy_planner.data.meal.datasource.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.DB.FavouriteDB;
import com.nadin.yummy_planner.data.DB.PlannerMealDB;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

public class MealLocalDatasource {
    private FavouriteDao favDao;
    private PlannerMealDao plannerMealDao;

    public MealLocalDatasource(Context context) {
        FavouriteDB favDB = FavouriteDB.getInstance(context);
        PlannerMealDB plannerMealDB = PlannerMealDB.getInstance(context);
        this.plannerMealDao = plannerMealDB.plannerMealDao();
        this.favDao = favDB.favDao();
    }

    public void addToFavourite(Meal meal) {
        new Thread(() -> favDao.insertMeal(meal)).start();
    }

    public void removeFromFavourite(Meal meal) {
        new Thread(() -> favDao.deleteMeal(meal)).start();
    }

    public LiveData<List<Meal>> getAllFavMeals() {
        return favDao.getAllFavMeals();
    }

    public void addMealToPlanner(Meal meal, long date) {
        PlannerMeal plannerMeal = new PlannerMeal();
        plannerMeal.setMeal(meal);
        plannerMeal.setDate(date);
        new Thread(() -> plannerMealDao.insertMeal(plannerMeal)).start();
    }

    public void removeMealFromPlanner(PlannerMeal plannedMeal) {
        new Thread(() -> plannerMealDao.deleteMeal(plannedMeal)).start();
    }

    public LiveData<List<PlannerMeal>> getMealByDate(long date) {
        return plannerMealDao.getMealByDate(date);
    }
}
