package com.example.foodapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.data.Meal;
import com.example.foodapp.data.MealResponse;
import com.example.foodapp.retrofit.RandomMealAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealDetails extends AppCompatActivity {
    private ImageButton fav;
    private ImageView mealImg;
    private TextView category;
    private TextView area;
    private TextView instructions;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        bundle=new Bundle();
        bundle=getIntent().getExtras();


        category=findViewById(R.id.category_name);
        area=findViewById(R.id.area_name);
        instructions=findViewById(R.id.instruction_name);
        fav=findViewById(R.id.fav_button);
        mealImg=findViewById(R.id.single_meal);


            if(bundle !=null) {
            //must use glide library
//            Glide.with(getApplicationContext()).load(getIntent().getStringExtra("thumb")).into(mealImg);
//            category.setText(bundle.getString("category"));
//            area.setText(bundle.getString("area"));
//            instructions.setText(bundle.getString("instructions"));
//
                //retrofit by id
                Retrofit retrofit= new Retrofit.Builder()
                        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RandomMealAPI api=retrofit.create(RandomMealAPI.class);
                Call<MealResponse> call=api.getMealDetails(bundle.getString("MealID"));
                call.enqueue(new Callback<MealResponse>() {


                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        Meal meal;
                        if (response.body() != null) {
                            meal = new Meal();
                            meal = response.body().getMeals().get(0);
                            Glide.with(getApplicationContext()).load(meal.getStrMealThumb()).into(mealImg);
                            category.setText(meal.getStrCategory());
                            area.setText(meal.getStrArea());
                            instructions.setText(meal.getStrInstructions());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {

                    }

                });
            }
    }

    }
