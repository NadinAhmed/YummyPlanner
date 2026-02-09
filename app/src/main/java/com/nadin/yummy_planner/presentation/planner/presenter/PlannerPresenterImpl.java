package com.nadin.yummy_planner.presentation.planner.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

public class PlannerPresenterImpl implements PlannerPresenter {
    MealRepo repo;

    public PlannerPresenterImpl(Context context){
        repo = new MealRepo(context);
    }
    @Override
    public void insertMealToPlanner(Meal meal, long date) {
        repo.addMealToPlanner(meal, date);
    }

    @Override
    public void deleteMealFromPlanner(PlannerMeal meal) {
        repo.removeMealFromPlanner(meal);
    }

    @Override
    public LiveData<List<PlannerMeal>> getMealsByDate(long date) {
        return repo.getMealByDate(date);
    }
}
