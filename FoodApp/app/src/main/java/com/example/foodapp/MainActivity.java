package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.foodapp.Adapters.MostPopularAdapter;
import com.example.foodapp.data.Meal;
import com.example.foodapp.data.*;
import com.example.foodapp.retrofit.RandomMealAPI;
import com.example.foodapp.ui.CategoryActivity;
import com.example.foodapp.ui.FavoriteActivity;
import com.example.foodapp.ui.MealDetails;
import com.example.foodapp.ui.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements recycleviewInter{
    MeowBottomNavigation bottomNavigation;
    ImageView img;
    RecyclerView recMostPopular;
    MostPopularAdapter mostPopularAdapter;
    List<categoryInfo> categoryInfos;
    ImageView IMGSearch;
    Meal MEAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Search box
        IMGSearch=findViewById(R.id.img_search);
        IMGSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(MainActivity.this, SearchActivity.class);
                 startActivity(search);
            }
        });
        //random meal
        img = findViewById(R.id.img_random_meal);
        //navigation bar
        bottomNavigation= findViewById(R.id.bottomNavigation);
        //add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model( 1,R.drawable.ic_favorite_border ));
        bottomNavigation.add(new MeowBottomNavigation.Model( 2,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model( 3,R.drawable.ic_category));
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            Intent i;
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 1:
                        i=new Intent(MainActivity.this, FavoriteActivity.class);
                        startActivity(i);
                        break;
                    case 2:
                        break;
                    case 3:
                        i=new Intent(MainActivity.this, CategoryActivity.class);
                        startActivity(i);
                        break;
                }
            }
        });
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {

            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),"youClicked"+item.getId(),Toast.LENGTH_SHORT).show();

            }
        });
        //bottomNavigation.setCount(1,"3");*/
        bottomNavigation.show(2 ,true);
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),"youReClicked"+item.getId(),Toast.LENGTH_SHORT).show();

            }
        });
       //retrofit for randomMeal
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RandomMealAPI api=retrofit.create(RandomMealAPI.class);
        Call<MealResponse>call=api.getMealList();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                Meal meal;
                    if(response.body()!=null){
                        meal=new Meal();
                        meal=response.body().getMeals().get(0);
                        Glide.with(getApplicationContext()).load(meal.getStrMealThumb()).into(img);
                        MEAL=meal;
                        Log.d("test","meal id:%d(meal.getIdMeal())");
                    }
                    else
                        return;
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.d("testf","failed");
            }
        });

        // clickableimage
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, MealDetails.class);
                intent.putExtra("MealID",MEAL.getIdMeal());
//                intent.putExtra("category",MEAL.getStrCategory());
//                intent.putExtra("thumb",MEAL.getStrMealThumb());
//                intent.putExtra("area",MEAL.getStrArea());
//                intent.putExtra("instructions",MEAL.getStrInstructions());
                startActivity(intent);
            }
        });
       //recycler view of popular item
        categoryInfos =new ArrayList<>();
        recMostPopular=findViewById(R.id.rec_view_meals_popular);
        mostPopularAdapter =new MostPopularAdapter(categoryInfos ,getApplication(),this);
        recMostPopular.setAdapter(mostPopularAdapter);
        recMostPopular.setLayoutManager(new LinearLayoutManager(getApplication(),LinearLayoutManager.HORIZONTAL,false));
        //retrofit of popular item
        Call<categoryResponse>call1=api.getcategoryList("Seafood");
        call1.enqueue(new Callback<categoryResponse>() {
            @Override
            public void onResponse(Call<categoryResponse> call, Response<categoryResponse> response) {
                  if(response.body()!=null){
                      mostPopularAdapter.setData(response.body().getcategoryMeals());
                  }
            }

            @Override
            public void onFailure(Call<categoryResponse> call, Throwable t) {
                Log.d("testg","failed");

            }
        });



    }
    //changed into long press click
    @Override
    public void onClickItem(int position) {
//        Intent intent =new Intent(MainActivity.this, MealDetails.class);
//        intent.putExtra("MealID",MEAL.getIdMeal());
//        startActivity(intent);
    }
}