package com.nadin.yummy_planner.data.meal.datasource.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlannerMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(PlannerMeal meal);

    @Delete
    Completable deleteMeal(PlannerMeal meal);

    @Query("SELECT * FROM PlannerMeals WHERE date = :date")
    Flowable<List<PlannerMeal>> getMealByDate(long date);

    @Query("SELECT * FROM PlannerMeals")
    Single<List<PlannerMeal>> getAllPlannerMealsOnce();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeals(List<PlannerMeal> meals);

    @Query("DELETE FROM PlannerMeals")
    Completable clearAll();
}
