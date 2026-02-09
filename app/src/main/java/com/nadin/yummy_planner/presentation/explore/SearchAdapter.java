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
import com.nadin.yummy_planner.data.explore.Category;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categories;

    public SearchAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        holder.recipesCount.setText(category.getRecipeCount());
        Glide.with(context)
                .load(category.getImageUrl())
                .into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categories.size();
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
