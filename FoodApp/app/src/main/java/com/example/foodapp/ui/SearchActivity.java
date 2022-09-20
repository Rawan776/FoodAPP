package com.example.foodapp.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Adapters.MostPopularAdapter;
import com.example.foodapp.Adapters.SearchNFavAdapter;
import com.example.foodapp.R;
import com.example.foodapp.data.Meal;
import com.example.foodapp.data.MealResponse;
import com.example.foodapp.retrofit.RandomMealAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
  RecyclerView recSearch;
  SearchNFavAdapter searchNFavAdapter;
  List<Meal> meals;
  EditText searchBOX;
  ImageView imgSearch;
  TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        txt1=findViewById(R.id.txt1_search);
        searchBOX=findViewById(R.id.ed_search);
        imgSearch=findViewById(R.id.ic_search);
        meals =new ArrayList<>();
        recSearch=findViewById(R.id.Search_rec);
        searchNFavAdapter =new SearchNFavAdapter(meals ,getApplication());
        recSearch.setAdapter(searchNFavAdapter);
        recSearch.setLayoutManager(new GridLayoutManager(getApplication(),2,GridLayoutManager.VERTICAL,false));
        //retrofit for search
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Retrofit retrofitSearch= new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RandomMealAPI apiSearch=retrofitSearch.create(RandomMealAPI.class);
        Call<MealResponse> callSearch=apiSearch.getSearchItems(searchBOX.getText().toString());
        callSearch.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.body()!=null) {
                    searchNFavAdapter.getData(response.body().getMeals());
                    txt1.setVisibility(TextView.GONE);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                    txt1.setText("NOT Found");
            }
        });
            }
        });

     }
}