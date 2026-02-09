package com.nadin.yummy_planner.presentation.planner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentPlannerBinding;
import com.nadin.yummy_planner.presentation.planner.view.adapter.CalendarAdapter;
import com.nadin.yummy_planner.presentation.planner.view.adapter.MealPlannerAdapter;


import java.util.ArrayList;
import java.util.List;

public class PlannerFragment extends Fragment {

    FragmentPlannerBinding binding;

    public PlannerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCalendarRecyclerView();
        setupMealsRecyclerView();


    }

    private void setupCalendarRecyclerView() {
        RecyclerView calendarRecyclerView = binding.calendarRecyclerView;
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Dummy data for calendar
        List<String> daysOfWeek = new ArrayList<>();
        daysOfWeek.add("M");
        daysOfWeek.add("T");
        daysOfWeek.add("W");
        daysOfWeek.add("T");
        daysOfWeek.add("F");
        daysOfWeek.add("S");
        daysOfWeek.add("S");

        List<String> daysOfMonth = new ArrayList<>();
        daysOfMonth.add("23");
        daysOfMonth.add("24");
        daysOfMonth.add("25");
        daysOfMonth.add("26");
        daysOfMonth.add("27");
        daysOfMonth.add("28");
        daysOfMonth.add("29");

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysOfWeek, daysOfMonth);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void setupMealsRecyclerView() {
        RecyclerView mealsRecyclerView = binding.mealsRecyclerView;
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Meal meal = new Meal();
            meal.setName("Meal " + (i + 1));
            meal.setImageUrl("https://www.themealdb.com/images/media/meals/1529444830.jpg");
            meal.setCategory("Category " + i);
            meal.setCountry("Country " + i);
            meals.add(meal);
        }

        MealPlannerAdapter mealPlannerAdapter = new MealPlannerAdapter(meals);
        mealsRecyclerView.setAdapter(mealPlannerAdapter);
    }
}