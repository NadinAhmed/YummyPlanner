package com.nadin.yummy_planner.presentation.planner.view;

import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

public interface OnClickListener {
    void onDayClick(long date);

    void onRemoveMealClick(PlannerMeal plannerMeal);
}
