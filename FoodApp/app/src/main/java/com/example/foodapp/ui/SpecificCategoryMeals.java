package com.example.foodapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.foodapp.Adapters.CategoryListAdapter;
import com.example.foodapp.Adapters.MealsByCategory;
import com.example.foodapp.Adapters.MostPopularAdapter;
import com.example.foodapp.R;
import com.example.foodapp.data.CategoryKind;
import com.example.foodapp.data.categoryInfo;
import com.example.foodapp.data.categoryResponse;
import com.example.foodapp.recycleviewInter;
import com.example.foodapp.retrofit.RandomMealAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecificCategoryMeals extends AppCompatActivity implements recycleviewInter {
    TextView categoryName;
    RecyclerView rv_specificCategory;
    MealsByCategory Specific_Category;
    List<categoryInfo> Meals;
    Bundle Category_bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category_meals);
        categoryName=findViewById(R.id.tv_specificCtegory);
        Category_bundle=new Bundle();
        Category_bundle=getIntent().getExtras();

        //recycleview
        Meals =new ArrayList<>();
        rv_specificCategory=findViewById(R.id.rv_SpecificCategory);
        Specific_Category =new MealsByCategory(getApplicationContext(),this,Meals);
        rv_specificCategory.setAdapter(Specific_Category);
        rv_specificCategory.setLayoutManager(new GridLayoutManager(getApplication(),2,GridLayoutManager.VERTICAL,false));
        //retrofit
        if(Category_bundle!=null) {
            categoryName.setText(Category_bundle.getString("CategoryName"));
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RandomMealAPI api = retrofit.create(RandomMealAPI.class);
            Call<categoryResponse> Cat_API = api.getcategoryList(Category_bundle.getString("CategoryName"));
            Cat_API.enqueue(new Callback<categoryResponse>() {
                @Override
                public void onResponse(Call<categoryResponse> call, Response<categoryResponse> response) {
                    List<categoryInfo> mealDetail;
                    if(response.body()!=null) {
                        mealDetail=new ArrayList<>();
                        mealDetail=response.body().getcategoryMeals();
                        Specific_Category.setMealsByCategory(mealDetail);
                        Meals=mealDetail;
                    }
                }

                @Override
                public void onFailure(Call<categoryResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onClickItem(int position) {
        Intent i =new Intent(SpecificCategoryMeals.this,MealDetails.class);
        i.putExtra("MealID",Meals.get(position).getIdMeal());
        startActivity(i);
    }
}