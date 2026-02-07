package com.nadin.yummy_planner.data.meal.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "FavMeals")
@TypeConverters(IngredientsTypeConverter.class)
public class Meal implements Serializable {
    @SerializedName("idMeal")
    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private String id;

    @SerializedName("strMeal")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("strMealThumb")
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @SerializedName("strCategory")
    @ColumnInfo(name = "category")
    private String category;

    @SerializedName("strArea")
    @ColumnInfo(name = "country")
    private String country;

    @SerializedName("strInstructions")
    @ColumnInfo(name = "instructions")
    private String instructions;
    @SerializedName("strYoutube")
    @ColumnInfo(name = "youtubeUrl")
    private String youtubeUrl;
    @Ignore
    @SerializedName("strIngredient1")
    String strIngredient1;
    @Ignore
    @SerializedName("strIngredient2")
    String strIngredient2;
    @Ignore
    @SerializedName("strIngredient3")
    String strIngredient3;
    @Ignore
    @SerializedName("strIngredient4")
    String strIngredient4;
    @Ignore
    @SerializedName("strIngredient5")
    String strIngredient5;
    @Ignore
    @SerializedName("strIngredient6")
    String strIngredient6;
    @Ignore
    @SerializedName("strIngredient7")
    String strIngredient7;
    @Ignore
    @SerializedName("strIngredient8")
    String strIngredient8;
    @Ignore
    @SerializedName("strIngredient9")
    String strIngredient9;
    @Ignore
    @SerializedName("strIngredient10")
    String strIngredient10;
    @Ignore
    @SerializedName("strIngredient11")
    String strIngredient11;
    @Ignore
    @SerializedName("strIngredient12")
    String strIngredient12;
    @Ignore
    @SerializedName("strIngredient13")
    String strIngredient13;
    @Ignore
    @SerializedName("strIngredient14")
    String strIngredient14;
    @Ignore
    @SerializedName("strIngredient15")
    String strIngredient15;
    @Ignore
    @SerializedName("strIngredient16")
    String strIngredient16;
    @Ignore
    @SerializedName("strIngredient17")
    String strIngredient17;
    @Ignore
    @SerializedName("strIngredient18")
    String strIngredient18;
    @Ignore
    @SerializedName("strIngredient19")
    String strIngredient19;
    @Ignore
    @SerializedName("strIngredient20")
    String strIngredient20;
    @Ignore
    @SerializedName("strMeasure1")
    String strMeasure1;
    @Ignore
    @SerializedName("strMeasure2")
    String strMeasure2;
    @Ignore
    @SerializedName("strMeasure3")
    String strMeasure3;
    @Ignore
    @SerializedName("strMeasure4")
    String strMeasure4;
    @Ignore
    @SerializedName("strMeasure5")
    String strMeasure5;
    @Ignore
    @SerializedName("strMeasure6")
    String strMeasure6;
    @Ignore
    @SerializedName("strMeasure7")
    String strMeasure7;
    @Ignore
    @SerializedName("strMeasure8")
    String strMeasure8;
    @Ignore
    @SerializedName("strMeasure9")
    String strMeasure9;
    @Ignore
    @SerializedName("strMeasure10")
    String strMeasure10;
    @Ignore
    @SerializedName("strMeasure11")
    String strMeasure11;
    @Ignore
    @SerializedName("strMeasure12")
    String strMeasure12;
    @Ignore
    @SerializedName("strMeasure13")
    String strMeasure13;
    @Ignore
    @SerializedName("strMeasure14")
    String strMeasure14;
    @Ignore
    @SerializedName("strMeasure15")
    String strMeasure15;
    @Ignore
    @SerializedName("strMeasure16")
    String strMeasure16;
    @Ignore
    @SerializedName("strMeasure17")
    String strMeasure17;
    @Ignore
    @SerializedName("strMeasure18")
    String strMeasure18;
    @Ignore
    @SerializedName("strMeasure19")
    String strMeasure19;
    @Ignore
    @SerializedName("strMeasure20")
    String strMeasure20;

    @ColumnInfo(name = "ingredients")
    private List<Ingredient> ingredients;

    public Meal() {
    }

    public Meal(String id, String name, String imageUrl, String category, String country,
                String instructions, String youtubeUrl, List<Ingredient> ingredients) {
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

    public List<Ingredient> getIngredients() {
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

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
