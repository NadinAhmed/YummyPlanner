package com.nadin.yummy_planner.presentation.home.presenter;

import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.presentation.home.view.HomeView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class HomePresenterImpl implements HomePresenter{
    HomeView homeView;
    MealRepo mealRepo;

    public HomePresenterImpl(HomeView homeView, Context context){
        this.homeView = homeView;
        mealRepo = new MealRepo(context);
    }

    @Override
    public void getRandomMeal() {
        mealRepo.getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    homeView.hideLoading();
                    homeView.setRandomMeal(meal);
                }, error -> {
                    homeView.hideLoading();
                    homeView.showError(error.getMessage());
                });
    }

    @Override
    public void addMealToFav(Meal meal) {
        mealRepo.addToFavourite(meal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeView::onMealAddToFavSuccess,
                        error -> homeView.showError(error.getMessage()));
    }

    @Override
    public void getPopularMeals() {
        mealRepo.getPopularMeals()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    homeView.hideLoading();
                    homeView.setPopularMeals(meals);
                }, error -> {
                    homeView.hideLoading();
                    homeView.showError(error.getMessage());
                });
    }
}
