package com.example.foodapp.retrofit;

import com.example.foodapp.data.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomMealAPI {
    @GET("random.php")
    public Call<MealResponse>getMealList();

    @GET("filter.php?")
    public Call<categoryResponse>getcategoryList(@Query("c")String categoryName);

    @GET("search.php?")
    public Call <MealResponse>getSearchItems(@Query("s")String SearchName);

    @GET("categories.php")
    public Call<CategoryKindResponse>getCategoriesKindList();

    @GET("lookup.php?")
    public Call<MealResponse>getMealDetails(@Query("i") String IDmeal);
}
