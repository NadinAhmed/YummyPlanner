package com.nadin.yummy_planner.presentation.planner.presenter;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

public interface PlannerPresenter {
     void insertMealToPlanner(Meal meal, long date);
     void deleteMealFromPlanner(PlannerMeal meal);
     LiveData<List<PlannerMeal>> getMealsByDate(long date);

}