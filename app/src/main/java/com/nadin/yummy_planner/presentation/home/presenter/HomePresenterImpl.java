package com.nadin.yummy_planner.presentation.home.presenter;

import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealNetworkResponse;
import com.nadin.yummy_planner.data.meal.datasource.remote.MealRemoteDatasource;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.presentation.home.view.HomeView;

import java.util.List;

public class HomePresenterImpl implements HomePresenter{
    HomeView homeView;
    MealRepo mealRepo;

    public HomePresenterImpl(HomeView homeView){
        this.homeView = homeView;
        mealRepo = new MealRepo();
    }

    @Override
    public void getRandomMeal() {
        mealRepo.getRandomMeal(new MealNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meal) {
                homeView.hideLoading();
                homeView.setRandomMeal(meal.get(0));
            }

            @Override
            public void onError(String error) {
                homeView.hideLoading();
                homeView.showError(error);
            }
        });
    }

    @Override
    public void addMealToFav(Meal meal) {

    }

    @Override
    public void getPopularMeals() {
        mealRepo.getPopularMeals(new MealNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meal) {
                homeView.hideLoading();
                homeView.setPopularMeals(meal);
            }

            @Override
            public void onError(String error) {
                homeView.hideLoading();
                homeView.showError(error);
            }
        });
    }
}
