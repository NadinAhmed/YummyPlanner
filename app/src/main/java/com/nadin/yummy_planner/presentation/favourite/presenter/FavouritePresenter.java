package com.nadin.yummy_planner.presentation.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.List;

public interface FavouritePresenter {
    LiveData<List<Meal>> getAllFavMeals();
    void deleteFromFav (Meal meal);
}
