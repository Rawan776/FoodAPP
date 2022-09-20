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
import com.example.foodapp.data.categoryInfo;
import com.example.foodapp.recycleviewInter;

import java.util.List;

public class MealsByCategory extends RecyclerView.Adapter<MealsByCategory.ViewHolder>{
    Context context;
    recycleviewInter rvinterface;
    List<categoryInfo> mealsByCategory;

    public MealsByCategory(Context context, recycleviewInter rvinterface, List<categoryInfo> mealsByCategory) {
        this.context = context;
        this.rvinterface = rvinterface;
        this.mealsByCategory = mealsByCategory;
    }

    public void setMealsByCategory(List<categoryInfo> mealsByCategory) {
        this.mealsByCategory = mealsByCategory;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.categorylist_layout,null,false);
        ViewHolder myViewHolder=new ViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(mealsByCategory.get(position).getStrMealThumb()).into(holder.mealimageView);
        holder.mealtextView.setText(mealsByCategory.get(position).getStrMeal());
    }

    @Override
    public int getItemCount() {
        return mealsByCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealtextView;
        ImageView mealimageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealtextView=itemView.findViewById(R.id.textView1);
            mealimageView=itemView.findViewById(R.id.imageView1);
            //called callback as it send position from here to activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rvinterface.onClickItem(getAdapterPosition());
                }
            });
        }
    }
}
