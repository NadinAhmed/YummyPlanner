package com.nadin.yummy_planner.data.explore;

public class Category {
    private String name;
    private String recipeCount;
    private String imageUrl;

    public Category(String name, String recipeCount, String imageUrl) {
        this.name = name;
        this.recipeCount = recipeCount;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getRecipeCount() {
        return recipeCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
