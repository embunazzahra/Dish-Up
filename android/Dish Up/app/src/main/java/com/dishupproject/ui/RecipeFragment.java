package com.dishupproject.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.dishupproject.R;
import com.dishupproject.data.RetrofitInstance;
import com.dishupproject.data.model.DefaultResponse;
import com.dishupproject.data.model.GetRecipesResponse;
import com.dishupproject.data.model.Recipe;
import com.dishupproject.data.services.RetrofitServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is a fragment to display all recipe.
 */
public class RecipeFragment extends Fragment {
    /**
     * A recipe list view.
     */
    ListView recipeList;
    /**
     * Array adapter for all recipe listview.
     */
    private static ArrayAdapter<Recipe> recipeLVAdapter;
    private RetrofitServices retrofitServices;
    /**
     * A List for recipes in the listview.
     */
    private static List<Recipe> recipes;
    /**
     * A recipe which user has selected from listview.
     */
    public static Recipe selectedRecipe = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        retrofitServices = RetrofitInstance.getInstance().create(RetrofitServices.class);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.addProduct).setVisible(true);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search recipe here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recipeLVAdapter.getFilter().filter(s);
                return false;
            }
        });

    }
    /**
     * This will move the activity if one of the menu is clicked.
     * @param item menu item.
     * @return selected item.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(getActivity(),"search clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addProduct:
                startActivity(new Intent(getActivity(), AddRecipeActivity.class));
                break;
            case R.id.aboutme:
                startActivity(new Intent(getActivity(), UserProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View recipeView = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeList = (ListView) recipeView.findViewById(R.id.lvRecipes);

        Call<GetRecipesResponse> call = retrofitServices.recipes();
        call.enqueue(new Callback<GetRecipesResponse>() {
            @Override
            public void onResponse(Call<GetRecipesResponse> call, Response<GetRecipesResponse> response) {
                if(response.code()==200){
                    GetRecipesResponse resp = response.body();
                    recipes = resp.getRecipes();
                    recipeLVAdapter = new ArrayAdapter<Recipe>(getActivity(),android.R.layout.simple_list_item_1,
                            recipes);
                    recipeList.setAdapter(recipeLVAdapter);
                }else{
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRecipesResponse> call, Throwable t) {

            }
        });

        /**
         * If the listview is clicked,
         * it will move to recipe detail activity
         * in RecipeDetailActivity.class
         */
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRecipe = (Recipe) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(),RecipeDetailActivity.class);
                startActivity(intent);
            }
        });

        return  recipeView;
    }
}