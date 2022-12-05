package com.dishupproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dishupproject.R;
import com.dishupproject.data.RetrofitInstance;
import com.dishupproject.data.model.DefaultResponse;
import com.dishupproject.data.model.GetRecipesResponse;
import com.dishupproject.data.model.Recipe;
import com.dishupproject.data.services.RetrofitServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is class for recipe detail activity.
 * All user can bookmark the recipe or delete the bookmark.
 * The recipe author can update or delete the recipe.
 *
 * @author Dhau' Embun Azzahra
 */
public class RecipeDetailActivity extends AppCompatActivity {
    private RetrofitServices retrofitServices;
    TextView tvRecipeName, tvrecipeWriter,tvIngredient,tvDirection;
    Button btnBookmark, btnBookmarkDelete,btnUpdateRecipe;
    Recipe recipe = RecipeFragment.selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvrecipeWriter = findViewById(R.id.tvrecipeWriter);
        tvIngredient = findViewById(R.id.tvIngredient);
        tvDirection = findViewById(R.id.tvDirection);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnBookmarkDelete = findViewById(R.id.btnBookmarkDelete);
        btnUpdateRecipe = findViewById(R.id.btnUpdateRecipe);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("recipe_id", Integer.valueOf(recipe.getRecipe_id()) );
        map.put("user_id", Integer.valueOf(LoginActivity.getLoggedAccount().getUser_id()));

        retrofitServices = RetrofitInstance.getInstance().create(RetrofitServices.class);
        Call<GetRecipesResponse> call = retrofitServices.recipebyid(recipe);
        call.enqueue(new Callback<GetRecipesResponse>() {
            @Override
            public void onResponse(Call<GetRecipesResponse> call, Response<GetRecipesResponse> response) {
                if(response.code()==200){
                    GetRecipesResponse recipesResponse = response.body();
                    Recipe recipe = recipesResponse.getRecipes().get(0);
                    if(recipe!=null){
                        tvRecipeName.setText(recipe.getRecipe_name());
                        tvrecipeWriter.setText(recipe.getUsername());
                        tvIngredient.setText(recipe.getIngredient());
                        tvDirection.setText(recipe.getDirection());
                    }

                }else{
                    try {
                        Toast.makeText(RecipeDetailActivity.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRecipesResponse> call, Throwable t) {
                Toast.makeText(RecipeDetailActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Listener for bookmark button
         * */
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookmark(map);
            }
        });

        /**
         * Check if the recipe is user's recipe
         * */
        if(recipe.getUser_id()==LoginActivity.getLoggedAccount().getUser_id()){
            btnUpdateRecipe.setVisibility(View.VISIBLE);
            btnUpdateRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RecipeDetailActivity.this,UpdateRecipeActivity.class);
                    intent.putExtra("recipe_id",recipe.getRecipe_id());
                    startActivity(intent);
                }
            });

        }

        /**
         * set delete bookmark button visibility
         * */
        btnBookmarkDelete.setVisibility(View.GONE);
        checkIfBookmarkExist(map);


        /**
         * set delete bookmark button callback
         * */
        btnBookmarkDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBookmark(map);
            }
        });
    }

    /**
     * If user pressed back button, push them to main page
     * to prevent back to add page, etc.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RecipeDetailActivity.this, MainActivity.class));
        finish();
    }

    private void addBookmark(HashMap map){
        Call <DefaultResponse> call = retrofitServices.addBookmark(map);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()==200){
                    DefaultResponse resp = response.body();
                    Toast.makeText(RecipeDetailActivity.this, resp.getMessage(),Toast.LENGTH_SHORT).show();
                    checkIfBookmarkExist(map);
                }else{
                    try {
                        Toast.makeText(RecipeDetailActivity.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RecipeDetailActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBookmark(HashMap map){
        Call<DefaultResponse> call = retrofitServices.deleteBookmark(map);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()==200){
                    DefaultResponse resp = response.body();
                    Toast.makeText(RecipeDetailActivity.this, resp.getMessage(),Toast.LENGTH_SHORT).show();
                    if(resp.getMessage().equalsIgnoreCase("Success")){
                        Intent intent = new Intent(RecipeDetailActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    try {
                        Toast.makeText(RecipeDetailActivity.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RecipeDetailActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkIfBookmarkExist(HashMap map){
        Call<DefaultResponse> checkbookmarkCall = retrofitServices.checkBookmark(map);
        checkbookmarkCall.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()==200){
                    DefaultResponse resp = response.body();
                    if(resp.getMessage().equalsIgnoreCase("Exist")){
                        btnBookmarkDelete.setVisibility(View.VISIBLE);
                    }
                }else{
                    try {
                        Toast.makeText(RecipeDetailActivity.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RecipeDetailActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}