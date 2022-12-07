package com.dishupproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dishupproject.R;
import com.dishupproject.data.RetrofitInstance;
import com.dishupproject.data.model.GetRecipesResponse;
import com.dishupproject.data.model.Recipe;
import com.dishupproject.data.services.RetrofitServices;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is activity for user profile.
 *
 * @author Dhau' Embun Azzahra
 */
public class UserProfileActivity extends AppCompatActivity {
    /**
     * Textview for display user's info.
     */
    TextView tvProfileUsername,tvProfileEmail,tvUserRecipes;
    /**
     * List view for display user's recipe.
     */
    ListView lvUserRecipes;
    /**
     * Button for logged out the current user.
     */
    Button btnLogout;
    RetrofitServices retrofitServices;
    /**
     * A List for recipes in the listview.
     */
    private List<Recipe> recipes;
    /**
     * Array adapter for recipe listview.
     */
    private static ArrayAdapter<Recipe> userRecipeLVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        tvProfileUsername = findViewById(R.id.tvProfileUsername);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        lvUserRecipes = findViewById(R.id.lvUserRecipes);
        tvUserRecipes = findViewById(R.id.tvUserRecipes);
        btnLogout = findViewById(R.id.btnLogout);

        /**
         * set text on User Profile
         */
        tvProfileUsername.setText(LoginActivity.getLoggedAccount().getUsername());
        tvProfileEmail.setText(LoginActivity.getLoggedAccount().getEmail());

        /**
         * Fetch all recipes create by user
         */
        retrofitServices = RetrofitInstance.getInstance().create(RetrofitServices.class);
        Call<GetRecipesResponse> call = retrofitServices.recipebyuser(LoginActivity.getLoggedAccount());
        call.enqueue(new Callback<GetRecipesResponse>() {
            @Override
            public void onResponse(Call<GetRecipesResponse> call, Response<GetRecipesResponse> response) {
                if(response.code()==200){
                    GetRecipesResponse resp = response.body();
                    recipes = resp.getRecipes();
                    if(recipes!=null){
                        userRecipeLVAdapter = new ArrayAdapter<Recipe>(UserProfileActivity.this,android.R.layout.simple_list_item_1, recipes);
                        lvUserRecipes.setAdapter(userRecipeLVAdapter);
                    }else {
                        tvUserRecipes.setText(resp.getMessage());
                        tvUserRecipes.setVisibility(View.VISIBLE);
                        lvUserRecipes.setVisibility(View.GONE);
                    }

                }else{
                    try {
                        Toast.makeText(UserProfileActivity.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRecipesResponse> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * If the listview is clicked,
         * it will move to recipe detail activity
         * in RecipeDetailActivity.class
         */
        lvUserRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecipeFragment.selectedRecipe = recipes.get(i);
                Intent intent = new Intent(UserProfileActivity.this,RecipeDetailActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Logout user
         */
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
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
        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
        finish();
    }

    /**
     * Function for removing session of the current user
     * and intent to the login activity.
     */
    private void logOut(){
        //remove session and back to login session
        SessionManager sessionManager = new SessionManager(UserProfileActivity.this);
        sessionManager.removeSession();

        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}