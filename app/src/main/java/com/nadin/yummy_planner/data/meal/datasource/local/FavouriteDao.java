package com.nadin.yummy_planner.data.meal.datasource.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

    @Query("SELECT * FROM favmeals")
    Flowable<List<Meal>> getAllFavMeals();

    @Query("SELECT * FROM FavMeals")
    Single<List<Meal>> getAllFavMealsOnce();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeals(List<Meal> meals);

    @Query("DELETE FROM FavMeals")
    Completable clearAll();
}
