package com.nadin.yummy_planner.presentation.planner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.auth.datasource.AuthDataSource;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;
import com.nadin.yummy_planner.databinding.FragmentPlannerBinding;
import com.nadin.yummy_planner.presentation.planner.model.Day;
import com.nadin.yummy_planner.presentation.planner.presenter.PlannerPresenter;
import com.nadin.yummy_planner.presentation.planner.presenter.PlannerPresenterImpl;
import com.nadin.yummy_planner.presentation.planner.view.adapter.CalendarAdapter;
import com.nadin.yummy_planner.presentation.planner.view.adapter.MealPlannerAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class PlannerFragment extends Fragment implements PlannerView, OnClickListener {

    private FragmentPlannerBinding binding;
    private PlannerPresenter plannerPresenter;
    private AuthDataSource authDataSource;
    private final CompositeDisposable disposables = new CompositeDisposable();


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
        authDataSource = new AuthDataSource(requireContext());

        if (authDataSource.isGuestUser()) {
            binding.plannerContentGroup.setVisibility(View.GONE);
            binding.guestPromptLayout.setVisibility(View.VISIBLE);
            binding.btnAuthFromPlanner.setOnClickListener(v -> NavHostFragment.findNavController(PlannerFragment.this).navigate(R.id.authScreen));
            return;
        }

        plannerPresenter = new PlannerPresenterImpl(getContext());
        setupCalendarRecyclerView();
        setupMealsRecyclerView();


    }

    private void setupCalendarRecyclerView() {
        RecyclerView calendarRecyclerView = binding.calendarRecyclerView;
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        CalendarAdapter calendarAdapter = new CalendarAdapter(getNext14Days(), this);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private List<Day> getNext14Days() {
        List<Day> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("d", Locale.getDefault());

        for (int i = 0; i < 14; i++) {
            String dayOfWeek = dayOfWeekFormat.format(calendar.getTime());
            String dayOfMonth = dayOfMonthFormat.format(calendar.getTime());
            days.add(new Day(dayOfWeek, dayOfMonth, calendar.getTimeInMillis()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }


    private void setupMealsRecyclerView() {
        RecyclerView mealsRecyclerView = binding.mealsRecyclerView;
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<PlannerMeal> meals = new ArrayList<>();
        MealPlannerAdapter mealPlannerAdapter = new MealPlannerAdapter(meals, this, requireContext());
        mealsRecyclerView.setAdapter(mealPlannerAdapter);
    }

    @Override
    public void showMealsByDate(long date) {
        
    }

    @Override
    public void showEmptyState() {

    }

    @Override
    public void showDeleteDialog(String message) {

    }

    @Override
    public void onDayClick(long date) {
        disposables.clear();
        disposables.add(plannerPresenter.getMealsByDate(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    if (meals.isEmpty()) {
                        binding.mealsRecyclerView.setVisibility(View.GONE);
                        binding.emptyView.setVisibility(View.VISIBLE);
                    } else {
                        binding.mealsRecyclerView.setVisibility(View.VISIBLE);
                        binding.emptyView.setVisibility(View.GONE);
                        MealPlannerAdapter mealPlannerAdapter = new MealPlannerAdapter(meals, this, requireContext());
                        binding.mealsRecyclerView.setAdapter(mealPlannerAdapter);
                    }
                }, throwable -> {
                }));
    }

    @Override
    public void onRemoveMealClick(PlannerMeal plannerMeal) {
        disposables.add(plannerPresenter.deleteMealFromPlanner(plannerMeal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }
}
