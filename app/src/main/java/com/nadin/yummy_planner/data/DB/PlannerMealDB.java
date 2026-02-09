package com.nadin.yummy_planner.data.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nadin.yummy_planner.data.meal.datasource.local.PlannerMealDao;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

@Database(entities = {PlannerMeal.class}, version = 2, exportSchema = false)
public abstract class PlannerMealDB extends RoomDatabase {

    public abstract PlannerMealDao plannerMealDao();

    public static PlannerMealDB INSTANCE;

    public static PlannerMealDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PlannerMealDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PlannerMealDB.class, "PlannerMeal.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
