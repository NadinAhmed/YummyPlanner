package com.nadin.yummy_planner.presentation.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.R;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private RecyclerView categoriesRecyclerView;
    private SearchAdapter searchAdapter;
    private List<Category> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        categoriesRecyclerView = view.findViewById(R.id.search_result_recycle_view);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        categoryList = new ArrayList<>();
        // Add dummy data from the screenshot
        categoryList.add(new Category("Italian", "120+ Recipes", "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg"));
        categoryList.add(new Category("Vegan", "85 Recipes", "https://www.themealdb.com/images/media/meals/rvxxuy1468312893.jpg"));
        categoryList.add(new Category("Breakfast", "200+ Recipes", "https://www.themealdb.com/images/media/meals/1550440197.jpg"));
        categoryList.add(new Category("Desserts", "50+ Recipes", "https://www.themealdb.com/images/media/meals/wpputp1511812960.jpg"));
        categoryList.add(new Category("Quick Meals", "Under 30min", "https://www.themealdb.com/images/media/meals/wrpwuu1511786491.jpg"));
        categoryList.add(new Category("Asian", "150+ Recipes", "https://www.themealdb.com/images/media/meals/1529444830.jpg"));


        searchAdapter = new SearchAdapter(getContext(), categoryList);
        categoriesRecyclerView.setAdapter(searchAdapter);

        return view;
    }
}
