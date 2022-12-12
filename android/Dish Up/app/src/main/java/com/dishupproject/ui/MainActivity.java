package com.dishupproject.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.dishupproject.R;
import com.dishupproject.data.RetrofitInstance;
import com.dishupproject.data.model.GetRecipesResponse;
import com.dishupproject.data.model.Recipe;
import com.dishupproject.data.services.RetrofitServices;
import com.google.android.material.tabs.TabLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is class for main activity.
 * this activity consists of fragments:
 * Recipe fragment to show all the recipe,
 * Bookmark fragment to show all recipe bookmarked by user.
 *
 * @author Dhau' Embun Azzahra
 */
public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    ListView recipeList;
    private static List<Recipe> recipesss;
    public static Recipe selectedRecipe = null;
    private RetrofitServices retrofitServices;
    private static ArrayAdapter<Recipe> recipeLVAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitServices = RetrofitInstance.getInstance().create(RetrofitServices.class);

        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        //for all recipes
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm,getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Recipes"));
        tabLayout.addTab(tabLayout.newTab().setText("Bookmarks"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });



        recipeList = (ListView) findViewById(R.id.lvRecipes);

        Call<GetRecipesResponse> call = retrofitServices.recipes();
        call.enqueue(new Callback<GetRecipesResponse>() {
            @Override
            public void onResponse(Call<GetRecipesResponse> call, Response<GetRecipesResponse> response) {
                if(response.code()==200){
                    GetRecipesResponse resp = response.body();
                    recipesss = resp.getRecipes();
                    recipeLVAdapter = new ArrayAdapter<Recipe>(MainActivity.this,android.R.layout.simple_list_item_1,
                            recipesss);
                    recipeList.setAdapter(recipeLVAdapter);
                }else{
                    try {
                        Toast.makeText(MainActivity.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(MainActivity.this,RecipeDetailActivity.class);
                startActivity(intent);
            }
        });
    }

}