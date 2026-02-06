package com.nadin.yummy_planner.data.meal.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Serializable {
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
    @SerializedName("strIngredient1")
    String strIngredient1;
    @SerializedName("strIngredient2")
    String strIngredient2;
    @SerializedName("strIngredient3")
    String strIngredient3;
    @SerializedName("strIngredient4")
    String strIngredient4;
    @SerializedName("strIngredient5")
    String strIngredient5;
    @SerializedName("strIngredient6")
    String strIngredient6;
    @SerializedName("strIngredient7")
    String strIngredient7;
    @SerializedName("strIngredient8")
    String strIngredient8;
    @SerializedName("strIngredient9")
    String strIngredient9;
    @SerializedName("strIngredient10")
    String strIngredient10;
    @SerializedName("strIngredient11")
    String strIngredient11;
    @SerializedName("strIngredient12")
    String strIngredient12;
    @SerializedName("strIngredient13")
    String strIngredient13;
    @SerializedName("strIngredient14")
    String strIngredient14;
    @SerializedName("strIngredient15")
    String strIngredient15;
    @SerializedName("strIngredient16")
    String strIngredient16;
    @SerializedName("strIngredient17")
    String strIngredient17;
    @SerializedName("strIngredient18")
    String strIngredient18;
    @SerializedName("strIngredient19")
    String strIngredient19;
    @SerializedName("strIngredient20")
    String strIngredient20;

    @SerializedName("strMeasure1")
    String strMeasure1;
    @SerializedName("strMeasure2")
    String strMeasure2;
    @SerializedName("strMeasure3")
    String strMeasure3;
    @SerializedName("strMeasure4")
    String strMeasure4;
    @SerializedName("strMeasure5")
    String strMeasure5;
    @SerializedName("strMeasure6")
    String strMeasure6;
    @SerializedName("strMeasure7")
    String strMeasure7;
    @SerializedName("strMeasure8")
    String strMeasure8;
    @SerializedName("strMeasure9")
    String strMeasure9;
    @SerializedName("strMeasure10")
    String strMeasure10;
    @SerializedName("strMeasure11")
    String strMeasure11;
    @SerializedName("strMeasure12")
    String strMeasure12;
    @SerializedName("strMeasure13")
    String strMeasure13;
    @SerializedName("strMeasure14")
    String strMeasure14;
    @SerializedName("strMeasure15")
    String strMeasure15;
    @SerializedName("strMeasure16")
    String strMeasure16;
    @SerializedName("strMeasure17")
    String strMeasure17;
    @SerializedName("strMeasure18")
    String strMeasure18;
    @SerializedName("strMeasure19")
    String strMeasure19;
    @SerializedName("strMeasure20")
    String strMeasure20;

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

    public List<Ingredient> getIngredientsList() {

        List<Ingredient> ingredients = new ArrayList<>();

        String[] ingredientArray = {
                strIngredient1, strIngredient2, strIngredient3, strIngredient4,
                strIngredient5, strIngredient6, strIngredient7, strIngredient8,
                strIngredient9, strIngredient10, strIngredient11, strIngredient12,
                strIngredient13, strIngredient14, strIngredient15, strIngredient16,
                strIngredient17, strIngredient18, strIngredient19, strIngredient20
        };

        String[] measureArray = {
                strMeasure1, strMeasure2, strMeasure3, strMeasure4,
                strMeasure5, strMeasure6, strMeasure7, strMeasure8,
                strMeasure9, strMeasure10, strMeasure11, strMeasure12,
                strMeasure13, strMeasure14, strMeasure15, strMeasure16,
                strMeasure17, strMeasure18, strMeasure19, strMeasure20
        };

        for (int i = 0; i < ingredientArray.length; i++) {

            if (ingredientArray[i] != null
                    && !ingredientArray[i].trim().isEmpty()) {

                ingredients.add(
                        new Ingredient(
                                ingredientArray[i],
                                measureArray[i]
                        )
                );
            }
        }

        return ingredients;
    }
}
