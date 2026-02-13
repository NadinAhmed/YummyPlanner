package com.nadin.yummy_planner.data.meal.datasource.local;

import android.content.Context;

import com.nadin.yummy_planner.data.DB.FavouriteDB;
import com.nadin.yummy_planner.data.DB.PlannerMealDB;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDatasource {
    private FavouriteDao favDao;
    private PlannerMealDao plannerMealDao;

    public MealLocalDatasource(Context context) {
        FavouriteDB favDB = FavouriteDB.getInstance(context);
        PlannerMealDB plannerMealDB = PlannerMealDB.getInstance(context);
        this.plannerMealDao = plannerMealDB.plannerMealDao();
        this.favDao = favDB.favDao();
    }

    public Completable addToFavourite(Meal meal) {
        meal.setIngredients(meal.getIngredients());
        return favDao.insertMeal(meal);
    }

    public Completable removeFromFavourite(Meal meal) {
        return favDao.deleteMeal(meal);
    }

    public Flowable<List<Meal>> getAllFavMeals() {
        return favDao.getAllFavMeals();
    }

    public Completable addMealToPlanner(Meal meal, long date) {
        return Completable.defer(() -> {
            meal.setIngredients(meal.getIngredients());
            PlannerMeal plannerMeal = new PlannerMeal();
            plannerMeal.setMeal(meal);
            plannerMeal.setDate(date);
            return plannerMealDao.insertMeal(plannerMeal);
        });
    }

    public Completable removeMealFromPlanner(PlannerMeal plannedMeal) {
        return plannerMealDao.deleteMeal(plannedMeal);
    }

    public Flowable<List<PlannerMeal>> getMealByDate(long date) {
        return plannerMealDao.getMealByDate(date);
    }

    public Single<List<Meal>> getAllFavMealsOnce() {
        return favDao.getAllFavMealsOnce();
    }

    public Single<List<PlannerMeal>> getAllPlannerMealsOnce() {
        return plannerMealDao.getAllPlannerMealsOnce();
    }

    public Completable replaceAllLocalData(List<Meal> favourites, List<PlannerMeal> plannerMeals) {
        return favDao.clearAll()
                .andThen(plannerMealDao.clearAll())
                .andThen(insertFavouritesIfNeeded(favourites))
                .andThen(insertPlannerMealsIfNeeded(plannerMeals));
    }

    public Completable clearAllLocalData() {
        return favDao.clearAll().andThen(plannerMealDao.clearAll());
    }

    private Completable insertFavouritesIfNeeded(List<Meal> favourites) {
        if (favourites == null || favourites.isEmpty()) {
            return Completable.complete();
        }
        return favDao.insertMeals(favourites);
    }

    private Completable insertPlannerMealsIfNeeded(List<PlannerMeal> plannerMeals) {
        if (plannerMeals == null || plannerMeals.isEmpty()) {
            return Completable.complete();
        }
        return plannerMealDao.insertMeals(plannerMeals);
    }
}
