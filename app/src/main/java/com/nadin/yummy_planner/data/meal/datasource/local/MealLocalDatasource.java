package com.nadin.yummy_planner.data.meal.datasource.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.DB.FavouriteDB;
import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

public class MealLocalDatasource {
    private FavouriteDao favDao;

    public MealLocalDatasource(Context context) {
        FavouriteDB favDB = FavouriteDB.getInstance(context);
        this.favDao = favDB.favDao();
    }

    public void addToFavourite(Meal meal) {
        new Thread(() -> favDao.insertMeal(meal)).start();
    }

    public void removeFromFavourite(Meal meal) {
        new Thread(() -> favDao.deleteMeal(meal)).start();
    }

    public LiveData<List<Meal>> getAllFavMeals(){
        return favDao.getAllFavMeals();
    }
}
