package com.nadin.yummy_planner.presentation.favourite.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.presentation.favourite.view.FavouriteVew;

import java.util.List;

public class FavouritePresenterImpl implements FavouritePresenter{
    FavouriteVew favView;
    MealRepo repo;

    public FavouritePresenterImpl(Context context, FavouriteVew favView){
        this.favView = favView;
        repo = new MealRepo(context);
    }

    @Override
    public LiveData<List<Meal>> getAllFavMeals() {
        return repo.getAllFavMeals();
    }

    @Override
    public void deleteFromFav(Meal meal) {
        repo.deleteFromFavourite(meal);
        favView.onDeleteMeal();
    }
}
