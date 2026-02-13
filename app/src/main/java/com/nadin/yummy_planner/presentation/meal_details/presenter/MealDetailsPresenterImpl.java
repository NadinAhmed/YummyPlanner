package com.nadin.yummy_planner.presentation.meal_details.presenter;

import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.presentation.meal_details.view.MealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter{
    MealDetailsView mealDetailsView;
    MealRepo repo;

    public MealDetailsPresenterImpl(Context context, MealDetailsView view){
        this.mealDetailsView = view;
        repo = new MealRepo(context);
    }
    @Override
    public void addMealToFav(Meal meal) {
        repo.addToFavourite(meal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealDetailsView::onMealAddToFavSuccess,
                        error -> {
                        });
    }

    @Override
    public void addMealToPlan(Meal meal, long date) {
        repo.addMealToPlanner(meal, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealDetailsView::onMealAddToPlanSuccess,
                        error -> {
                        });
    }
}
