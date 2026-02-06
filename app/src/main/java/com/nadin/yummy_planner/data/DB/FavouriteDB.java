package com.nadin.yummy_planner.data.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nadin.yummy_planner.data.meal.datasource.local.FavouriteDao;
import com.nadin.yummy_planner.data.meal.model.Meal;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class FavouriteDB extends RoomDatabase {

    public abstract FavouriteDao favDao();

    public static FavouriteDB INSTANCE;

    public static FavouriteDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FavouriteDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavouriteDB.class, "FavMeals.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
