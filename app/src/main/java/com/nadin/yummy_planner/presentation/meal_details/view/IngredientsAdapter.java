package com.nadin.yummy_planner.presentation.meal_details.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredient> ingredients;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.name.setText(ingredient.getName());
        holder.quantity.setText(ingredient.getMeasure());
        Glide.with(holder.itemView.getContext())
                .load("https://www.themealdb.com/images/ingredients/" + ingredient.getName().trim().replace("\\s+", "-") + ".png")
                .placeholder(R.drawable.logo)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ingredient_image);
            name = itemView.findViewById(R.id.ingredient_name);
            quantity = itemView.findViewById(R.id.ingredient_measure);
        }
    }
}
