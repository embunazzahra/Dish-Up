package com.dishupproject.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class that represents a recipe
 */
public class Recipe {
    /**
     * The recipe id.
     */
    @SerializedName("recipe_id")
    private int recipe_id;
    /**
     * The recipe name.
     */
    @SerializedName("recipe_name")
    private String recipe_name;
    /**
     * The recipe ingredients.
     */
    @SerializedName("ingredient")
    private String ingredient;
    /**
     * The recipe directions.
     */
    @SerializedName("direction")
    private String direction;
    /**
     * The recipe author id.
     */
    @SerializedName("user_id")
    private int user_id;
    /**
     * The username of recipe author.
     */
    @SerializedName("username")
    private String username;

    public Recipe(int recipe_id, String recipe_name, String ingredient, String direction, int user_id, String username) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.ingredient = ingredient;
        this.direction = direction;
        this.user_id = user_id;
        this.username = username;
    }

    public Recipe(String recipe_name, String ingredient, String direction, int user_id) {
        this.recipe_name = recipe_name;
        this.ingredient = ingredient;
        this.direction = direction;
        this.user_id = user_id;
    }

    public Recipe(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Recipe(int recipe_id, String recipe_name, String ingredient, String direction) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.ingredient = ingredient;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return recipe_name.toUpperCase()+"\n"+username;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getDirection() {
        return direction;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }
}
