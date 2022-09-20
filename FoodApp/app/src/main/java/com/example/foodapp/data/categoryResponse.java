package com.example.foodapp.data;

import java.util.ArrayList;
import java.util.List;

public class categoryResponse {
    //use with popular items
    private List<categoryInfo> meals = new ArrayList<categoryInfo>();

    public List<categoryInfo> getcategoryMeals() {
        return meals;
    }
}
