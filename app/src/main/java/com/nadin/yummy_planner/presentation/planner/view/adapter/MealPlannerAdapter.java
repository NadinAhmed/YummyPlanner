package com.nadin.yummy_planner.presentation.planner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;
import com.nadin.yummy_planner.presentation.planner.view.OnClickListener;

import java.util.List;

public class MealPlannerAdapter extends RecyclerView.Adapter<MealPlannerAdapter.ViewHolder> {

    private List<PlannerMeal> meals;
    private OnClickListener onClickListener;

    public MealPlannerAdapter(List<PlannerMeal> meals, OnClickListener onClickListener) {
        this.meals = meals;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_planner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlannerMeal plannerMeal = meals.get(position);
        Meal meal = plannerMeal.getMeal();
        holder.title.setText(meal.getName());
        if (meal.getCategory() != null && meal.getCountry() != null) {
            String description = meal.getCategory() + " | " + meal.getCountry();
            holder.description.setText(description);
        } else {
            holder.description.setText("");
        }


        Glide.with(holder.itemView.getContext())
                .load(meal.getImageUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(holder.image);

        holder.deleteIcon.setOnClickListener(v -> onClickListener.onRemoveMealClick(plannerMeal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        ImageView deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.meal_planner_image);
            title = itemView.findViewById(R.id.meal_tittle_tv);
            description = itemView.findViewById(R.id.meal_description_tv);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
        }
    }
}