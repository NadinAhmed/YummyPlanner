package com.nadin.yummy_planner.presentation.favourite.presenter;

import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class FavouritePresenterImpl implements FavouritePresenter{
    MealRepo repo;

    public FavouritePresenterImpl(Context context){
        repo = new MealRepo(context);
    }

    @Override
    public Flowable<List<Meal>> getAllFavMeals() {
        return repo.getAllFavMeals();
    }

    @Override
    public Completable deleteFromFav(Meal meal) {
        return repo.deleteFromFavourite(meal);
    }
}
