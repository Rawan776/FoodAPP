package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.data.CategoryKind;
import com.example.foodapp.data.Meal;
import com.example.foodapp.data.categoryInfo;
import com.example.foodapp.recycleviewInter;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{
    List<CategoryKind> cart;
    Context context;
    recycleviewInter rvinterface;
    //constructor
    public CategoryListAdapter(List<CategoryKind> cart, Context context,recycleviewInter rvinterface) {
        this.cart = cart;
        this.context = context;
        this.rvinterface=rvinterface;
    }


    public void setCategoryList(List<CategoryKind> cart) {
        this.cart = cart;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.categorylist_layout,null,false);
        ViewHolder myViewHolder=new ViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(cart.get(position).getStrCategoryThumb()).into(holder.categoryimageView);
        holder.categorytextView.setText(cart.get(position).getStrCategory());
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categorytextView;
        ImageView categoryimageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categorytextView=itemView.findViewById(R.id.textView1);
            categoryimageView=itemView.findViewById(R.id.imageView1);
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
