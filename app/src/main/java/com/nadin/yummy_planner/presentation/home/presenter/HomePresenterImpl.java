package com.nadin.yummy_planner.presentation.home.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.presentation.home.view.HomeView;

import java.net.UnknownHostException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class HomePresenterImpl implements HomePresenter{
    HomeView homeView;
    MealRepo mealRepo;

    public HomePresenterImpl(HomeView homeView, Context context){
        this.homeView = homeView;
        mealRepo = new MealRepo(context);
    }

    private void handleSearchError(Throwable error) {
        if (error instanceof UnknownHostException) {
            homeView.showError("No internet connection.\nPlease check your network.");
        } else {
            homeView.showError(error.getMessage());
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void getRandomMeal() {
        mealRepo.getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    homeView.hideLoading();
                    homeView.setRandomMeal(meal);
                }, error -> {
                    homeView.hideLoading();
                    this.handleSearchError(error);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void addMealToFav(Meal meal) {
        mealRepo.addToFavourite(meal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeView::onMealAddToFavSuccess,
                        this::handleSearchError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPopularMeals() {
        mealRepo.getPopularMeals()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    homeView.hideLoading();
                    homeView.setPopularMeals(meals);
                }, error -> {
                    homeView.hideLoading();
                    this.handleSearchError(error);
                });
    }
}
