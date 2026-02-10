package com.nadin.yummy_planner.data.meal.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("SELECT * FROM favmeals")
    LiveData<List<Meal>> getAllFavMeals();

    @Query("SELECT * FROM FavMeals")
    List<Meal> getAllFavMealsSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Meal> meals);

    @Query("DELETE FROM FavMeals")
    void clearAll();
}
