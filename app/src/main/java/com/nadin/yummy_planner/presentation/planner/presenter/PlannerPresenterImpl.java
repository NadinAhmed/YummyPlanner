package com.nadin.yummy_planner.presentation.planner.presenter;

import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlannerPresenterImpl implements PlannerPresenter {
    MealRepo repo;

    public PlannerPresenterImpl(Context context){
        repo = new MealRepo(context);
    }
    @Override
    public Completable insertMealToPlanner(Meal meal, long date) {
        return repo.addMealToPlanner(meal, date);
    }

    @Override
    public Completable deleteMealFromPlanner(PlannerMeal meal) {
        return repo.removeMealFromPlanner(meal);
    }

    @Override
    public Flowable<List<PlannerMeal>> getMealsByDate(long date) {
        return repo.getMealByDate(date);
    }
}
