package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.data.categoryInfo;
import com.example.foodapp.recycleviewInter;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularAdapter.ViewHolder>{
    List<categoryInfo> populatItemsData;
    Context context;
    recycleviewInter rvinterface;

    public MostPopularAdapter(List<categoryInfo> populatItemsData, Context context, recycleviewInter rvinterface) {
        this.populatItemsData = populatItemsData;
        this.context = context;
        this.rvinterface = rvinterface;
    }

    public void setData(List<categoryInfo> populatItemsData){
        this.populatItemsData = populatItemsData;
          notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MostPopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item_layout,parent,false);
        ViewHolder holder=new ViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostPopularAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(populatItemsData.get(position).getStrMealThumb()).into(holder.imgPopular);
    }

    @Override
    public int getItemCount() {
        return populatItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPopular;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPopular=itemView.findViewById(R.id.popular_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rvinterface.onClickItem(getAdapterPosition());
                }
            });
        }
    }
}
