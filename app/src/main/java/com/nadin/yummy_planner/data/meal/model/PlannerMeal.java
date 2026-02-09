package com.nadin.yummy_planner.data.meal.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlannerMeals")
public class PlannerMeal {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "planning_id")
    public int id;

    @ColumnInfo(name = "date")
    public long date; // timestamp

    @Embedded
    public Meal meal;

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}