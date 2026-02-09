package com.nadin.yummy_planner.presentation.explore;

import android.content.Context;
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

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CategoryViewHolder> {

    private Context context;
    private List<Meal> meals;

    public SearchAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.categoryName.setText(meal.getName());
        holder.recipesCount.setText(meal.getCategory() + " | " + meal.getCountry());
        Glide.with(context)
                .load(meal.getImageUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
        TextView recipesCount;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.meal_name_search);
            recipesCount = itemView.findViewById(R.id.description_meal_search);
        }
    }
}
