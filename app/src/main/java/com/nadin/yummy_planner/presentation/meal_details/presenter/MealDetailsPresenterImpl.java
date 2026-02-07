package com.nadin.yummy_planner.presentation.meal_details.presenter;

import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.presentation.meal_details.view.MealDetailsView;

public class MealDetailsPresenterImpl implements MealDetailsPresenter{
    MealDetailsView mealDetailsView;
    MealRepo repo;

    public MealDetailsPresenterImpl(Context context, MealDetailsView view){
        this.mealDetailsView = view;
        repo = new MealRepo(context);
    }
    @Override
    public void addMealToFav(Meal meal) {
        repo.addToFavourite(meal);
        mealDetailsView.onMealAddToFavSuccess();
    }
}
