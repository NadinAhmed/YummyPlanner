package com.nadin.yummy_planner.presentation.favourite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    private FragmentFavouriteBinding binding;
    private RecyclerView favouriteRecyclerView;
    private FavouriteAdapter adapter;

    List<Meal>  meals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Meal> meals = new ArrayList<>();

        Meal meal1 = new Meal();
        meal1.setId("53330");
        meal1.setName("Cassava Pizza");
        meal1.setCategory("Pork");
        meal1.setCountry("Venezuelan");
        meal1.setInstructions("Preheat the oven to 200ºC...");
        meal1.setImageUrl("https://www.example.com/image1.jpg");
        meal1.setYoutubeUrl("https://www.youtube.com/watch?v=abc123");
        meals.add(meal1);

        // Meal 2
        Meal meal2 = new Meal();
        meal2.setId("53331");
        meal2.setName("Chicken Salad");
        meal2.setCategory("Salad");
        meal2.setCountry("American");
        meal2.setInstructions("Mix all ingredients and serve chilled.");
        meal2.setImageUrl("https://www.example.com/image2.jpg");
        meal2.setYoutubeUrl("https://www.youtube.com/watch?v=xyz456");
        meals.add(meal2);

        // Meal 3
        Meal meal3 = new Meal();
        meal3.setId("53332");
        meal3.setName("Pancakes");
        meal3.setCategory("Breakfast");
        meal3.setCountry("French");
        meal3.setInstructions("Mix flour, milk, and eggs. Cook on a hot pan.");
        meal3.setImageUrl("https://www.example.com/image3.jpg");
        meal3.setYoutubeUrl("https://www.youtube.com/watch?v=lmn789");
        meals.add(meal3);


        favouriteRecyclerView = binding.recyclerViewFav;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        favouriteRecyclerView.setLayoutManager(layoutManager);
        adapter = new FavouriteAdapter(meals);
        adapter.setMeals(meals);
        favouriteRecyclerView.setAdapter(adapter);
    }
}
