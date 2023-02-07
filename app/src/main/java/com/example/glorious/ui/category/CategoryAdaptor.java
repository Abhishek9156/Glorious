package com.example.glorious.ui.category;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.glorious.R;
import com.example.glorious.response.data.CategoryData;


import java.util.ArrayList;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.RecyclerViewHolder> {
    Context context;
    ArrayList<CategoryData> data;
    public CategoryAdaptor(Context context, ArrayList<CategoryData> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public CategoryAdaptor.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.categoryadaptor, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.RecyclerViewHolder holder, int position) {
        CategoryData categoryData= data.get(position);
        Glide.with(context).load(categoryData.categoryImage).placeholder(R.drawable.bag).into(holder.imageView);
        holder.textView.setText(categoryData.categoryName);

        final  int itemPosition=position;
        holder.cardView.setOnClickListener(view ->
            onClickCard( itemPosition));
    }

    private void onClickCard(int itemPosition) {
        if (data != null) {
            Intent intent = new Intent(context, CategoryDetailActivity.class);
            String datas=data.get(itemPosition).categoryName;
            Log.d(TAG, "onClick:  CategoryDetail \n" + datas);
            intent.putExtra("KeyConstants.CategoryDetail", datas);
            context.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.catimg);
            textView=itemView.findViewById(R.id.tvProduct);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }
}
