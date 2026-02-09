package com.nadin.yummy_planner.data.meal.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

@Dao
public interface PlannerMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMeal(PlannerMeal meal);

    @Delete
    public void deleteMeal(PlannerMeal meal);

    @Query("SELECT * FROM PlannerMeals WHERE date = :date")
    public LiveData<List<PlannerMeal>> getMealByDate(long date);
}
