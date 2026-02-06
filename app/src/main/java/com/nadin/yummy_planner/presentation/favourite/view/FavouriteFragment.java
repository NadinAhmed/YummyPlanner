package com.nadin.yummy_planner.presentation.favourite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentFavouriteBinding;
import com.nadin.yummy_planner.presentation.favourite.presenter.FavouritePresenter;
import com.nadin.yummy_planner.presentation.favourite.presenter.FavouritePresenterImpl;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteVew {
    private FragmentFavouriteBinding binding;
    private RecyclerView favouriteRecyclerView;
    private FavouriteAdapter adapter;
    private FavouritePresenter presenter;

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

        favouriteRecyclerView = binding.recyclerViewFav;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        favouriteRecyclerView.setLayoutManager(layoutManager);

        presenter = new FavouritePresenterImpl(getContext(), this);
        presenter.getAllFavMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                adapter.setMeals(meals);
            }
        });

        adapter = new FavouriteAdapter(new OnDeleteClickListener() {
            @Override
            public void onDeleteClicked(Meal meal) {
                presenter.deleteFromFav(meal);
            }
        });
        favouriteRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteMeal() {
        //TODO show dialog to confirm
    }
}
