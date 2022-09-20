package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.data.Meal;

import java.util.List;

public class SearchNFavAdapter extends RecyclerView.Adapter<SearchNFavAdapter.ViewHolder> {
    List<Meal> mealInfo;
    Context context;

    public SearchNFavAdapter(List<Meal> mealInfo, Context context) {
        this.mealInfo = mealInfo;
        this.context = context;
    }
    public void getData(List<Meal>mealInfo){
        this.mealInfo = mealInfo;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_fav_layout,parent,false);
        SearchNFavAdapter.ViewHolder holder= new ViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNFavAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(mealInfo.get(position).getStrMealThumb()).into(holder.img);
        holder.txt1.setText(mealInfo.get(position).getStrMeal());
    }



    @Override
    public int getItemCount() {
        return mealInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_searched_meal);
            txt1=itemView.findViewById(R.id.searched_meal);
        }
    }
}
