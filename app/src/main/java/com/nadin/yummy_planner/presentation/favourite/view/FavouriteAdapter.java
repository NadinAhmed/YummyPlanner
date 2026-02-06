package com.nadin.yummy_planner.presentation.favourite.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private List<Meal> meals;
    private OnDeleteClickListener listener;

    public FavouriteAdapter(OnDeleteClickListener listener) {
        this.meals = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_meal, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealNameTextView.setText(meal.getName());
        holder.mealInfoTextView.setText(meal.getCategory() + " | " + meal.getCountry());
        Glide.with(holder.itemView.getContext())
                .load(meal.getImageUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(holder.mealImageView);
        holder.removeButton.setOnClickListener(v -> listener.onDeleteClicked(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImageView;
        TextView mealNameTextView;
        TextView mealInfoTextView;
        MaterialButton removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.mealImageView);
            mealNameTextView = itemView.findViewById(R.id.mealNameTextView);
            mealInfoTextView = itemView.findViewById(R.id.mealInfoTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}