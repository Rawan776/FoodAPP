package com.example.foodapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.foodapp.Adapters.CategoryListAdapter;
import com.example.foodapp.Adapters.MostPopularAdapter;
import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.data.CategoryKind;
import com.example.foodapp.data.CategoryKindResponse;
import com.example.foodapp.data.Meal;
import com.example.foodapp.data.MealResponse;
import com.example.foodapp.data.categoryInfo;
import com.example.foodapp.recycleviewInter;
import com.example.foodapp.retrofit.RandomMealAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity implements recycleviewInter {
    MeowBottomNavigation bottomNavigation;
    RecyclerView recCategoryList;
    CategoryListAdapter mostCategoryAdapter;
    List<CategoryKind> categorykinds;
    List<CategoryKind> categorynames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

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
                        i=new Intent(CategoryActivity.this, FavoriteActivity.class);
                        startActivity(i);
                        break;
                    case 2:
                        i=new Intent(CategoryActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case 3:
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
        bottomNavigation.show(3 ,true);
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),"youReClicked"+item.getId(),Toast.LENGTH_SHORT).show();

            }
        });

        //recycleview
        categorykinds =new ArrayList<>();
        recCategoryList=findViewById(R.id.recycleview);
        mostCategoryAdapter =new CategoryListAdapter(categorykinds ,getApplication(),this);
        recCategoryList.setAdapter(mostCategoryAdapter);
        recCategoryList.setLayoutManager(new GridLayoutManager(getApplication(),2,GridLayoutManager.VERTICAL,false));

        //retrofit
        Retrofit retrofitCategory= new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RandomMealAPI api=retrofitCategory.create(RandomMealAPI.class);
        Call<CategoryKindResponse> callCategory=api.getCategoriesKindList();
        callCategory.enqueue(new Callback<CategoryKindResponse>() {
            @Override
            public void onResponse(Call<CategoryKindResponse> call, Response<CategoryKindResponse> response) {
                List<CategoryKind>categoryKinds;
                if(response.body()!=null){
                    categoryKinds=new ArrayList<>();
                    categoryKinds=response.body().getCategoriesKindList();
                    mostCategoryAdapter.setCategoryList(categoryKinds);
                    categorynames=categoryKinds;
                }
            }

            @Override
            public void onFailure(Call<CategoryKindResponse> call, Throwable t) {
                Log.d("testg","failed");
            }
        });
    }

    @Override
    public void onClickItem(int position) {
        Intent i =new Intent(CategoryActivity.this,SpecificCategoryMeals.class);
        i.putExtra("CategoryName",categorynames.get(position).getStrCategory());
        startActivity(i);
    }
}