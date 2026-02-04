package com.nadin.yummy_planner.presentation.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView popularMealsRecyclerView;
    private PopularMealsAdapter popularMealsAdapter;
    private List<Meal> popularMeals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        popularMealsRecyclerView = view.findViewById(R.id.recycler_popular_meals);
        popularMealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Dummy data
        popularMeals = new ArrayList<>();
        popularMeals.add(new Meal(1, "Avocado Toast", "https://images.unsplash.com/photo-1484723051597-62b8a788a6ac?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Breakfast", "International", "Instructions here", "", new ArrayList<>()));
        popularMeals.add(new Meal(2, "Berry Smoothie", "https://images.unsplash.com/photo-1542884748-2b87b36c6b90?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Breakfast", "International", "Instructions here", "", new ArrayList<>()));
        popularMeals.add(new Meal(3, "Pancakes", "https://images.unsplash.com/photo-1528207776546-365bb710ee93?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Breakfast", "International", "Instructions here", "", new ArrayList<>()));


        popularMealsAdapter = new PopularMealsAdapter(popularMeals);
        popularMealsRecyclerView.setAdapter(popularMealsAdapter);
    }
}
