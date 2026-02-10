package com.nadin.yummy_planner.presentation.planner.presenter;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface PlannerPresenter {
     Completable insertMealToPlanner(Meal meal, long date);
     Completable deleteMealFromPlanner(PlannerMeal meal);
     Flowable<List<PlannerMeal>> getMealsByDate(long date);

}
