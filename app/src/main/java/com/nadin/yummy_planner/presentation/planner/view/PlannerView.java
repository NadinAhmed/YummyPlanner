package com.nadin.yummy_planner.presentation.planner.view;

public interface PlannerView {
        void showMealsByDate(long date);
        void showEmptyState();
        void showDeleteDialog(String message);
}
