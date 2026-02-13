package com.nadin.yummy_planner.presentation.favourite.presenter;

import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface FavouritePresenter {
    Flowable<List<Meal>> getAllFavMeals();
    Completable deleteFromFav (Meal meal);
}
