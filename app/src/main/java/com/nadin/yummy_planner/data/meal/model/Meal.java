package com.nadin.yummy_planner.data.meal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meal {
    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strMealThumb")
    private String imageUrl;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String country;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strYoutube")
    private String youtubeUrl;
    private List<String> ingredients;

    public Meal() {
    }

    public Meal(String id, String name, String imageUrl, String category, String country,
                String instructions, String youtubeUrl, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.country = country;
        this.instructions = instructions;
        this.youtubeUrl = youtubeUrl;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
