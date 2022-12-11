package com.dishupproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dishupproject.R;
import com.dishupproject.data.RetrofitInstance;
import com.dishupproject.data.model.DefaultResponse;
import com.dishupproject.data.model.GetRecipesResponse;
import com.dishupproject.data.model.Recipe;
import com.dishupproject.data.services.RetrofitServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is a Update Recipe Activity.
 * The author of the recipe can make
 * changes to the recipe.
 *
 * @author Dhau' Embun Azzahra
 */
public class UpdateRecipeActivity extends AppCompatActivity {
    RetrofitServices retrofitServices;
    /**
     * Form for user's title, ingredients, and directions
     * of the recipe.
     * Initialize with the current recipe data.
     */
    EditText etEditJudulResep,etEditBahanResep,etEditLangkahResep;
    /**
     * Button for calling update and delete request.
     */
    Button btnUpdateRecipe,btnDeleteRecipe;
    int recipe_id;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);
        etEditJudulResep = findViewById(R.id.etEditJudulResep);
        etEditBahanResep = findViewById(R.id.etEditBahanResep);
        etEditLangkahResep = findViewById(R.id.etEditLangkahResep);
        btnUpdateRecipe = findViewById(R.id.btnUpdateRecipe);
        btnDeleteRecipe = findViewById(R.id.btnDeleteRecipe);

        /**
         * Get recipe_id from RecipeDetailActivity
         */
        recipe_id = getIntent().getExtras().getInt("recipe_id");
        recipe = new Recipe(recipe_id);
        /**
         * Get recipe by recipe id
         */
        retrofitServices = RetrofitInstance.getInstance().create(RetrofitServices.class);
        Call<GetRecipesResponse> call = retrofitServices.recipebyid(recipe);
        call.enqueue(new Callback<GetRecipesResponse>() {
            @Override
            public void onResponse(Call<GetRecipesResponse> call, Response<GetRecipesResponse> response) {
                if(response.code()==200){
                    GetRecipesResponse resp = response.body();
                    if(resp.getMessage().equalsIgnoreCase("success")){
                        Recipe recipe = resp.getRecipes().get(0);
                        etEditJudulResep.setText(recipe.getRecipe_name());
                        etEditBahanResep.setText(recipe.getIngredient());
                        etEditLangkahResep.setText(recipe.getDirection());
                    }else{
                        Toast.makeText(UpdateRecipeActivity.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Toast.makeText(UpdateRecipeActivity.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRecipesResponse> call, Throwable t) {
                Toast.makeText(UpdateRecipeActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * call updateRecipe on clicked
         */
        btnUpdateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipe_name = etEditJudulResep.getText().toString();
                String ingredient = etEditBahanResep.getText().toString();
                String direction = etEditLangkahResep.getText().toString();
                Recipe updatedRecipe = new Recipe(recipe_id,recipe_name,ingredient,direction);

                Call<DefaultResponse> call = retrofitServices.updateRecipe(updatedRecipe);
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if(response.code()==200){
                            DefaultResponse resp = response.body();
                            Toast.makeText(UpdateRecipeActivity.this, resp.getMessage(),Toast.LENGTH_SHORT).show();
                            if(resp.getMessage().equalsIgnoreCase("success")){
                                Intent intent = new Intent(UpdateRecipeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(UpdateRecipeActivity.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(UpdateRecipeActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(UpdateRecipeActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /**
         * Call deleteRecipe if button is clicked
         */
        btnDeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<DefaultResponse> call = retrofitServices.deleteRecipe(recipe);
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if(response.code()==200){
                            DefaultResponse resp = response.body();
                            Toast.makeText(UpdateRecipeActivity.this, resp.getMessage(),Toast.LENGTH_SHORT).show();
                            if(resp.getMessage().equalsIgnoreCase("success")){
                                Intent intent = new Intent(UpdateRecipeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(UpdateRecipeActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(UpdateRecipeActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}