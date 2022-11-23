package com.dishupproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class that represents the response in the
 * form of recipes list
 *
 * @author Dhau' Embun Azzahra
 */
public class GetRecipesResponse extends  DefaultResponse{
    @SerializedName("data")
    private List<Recipe> recipes = null;

    public GetRecipesResponse(String message, List<Recipe> recipes) {
        super(message);
        this.recipes = recipes;
    }

    public GetRecipesResponse(String message){
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
