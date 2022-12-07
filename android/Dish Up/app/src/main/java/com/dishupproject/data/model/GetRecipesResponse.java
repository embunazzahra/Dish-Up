package com.dishupproject.data.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;
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
