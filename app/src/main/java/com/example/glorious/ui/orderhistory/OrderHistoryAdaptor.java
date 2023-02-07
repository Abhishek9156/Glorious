package com.example.glorious.ui.orderhistory;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.glorious.response.data.OrderHistoryData;
import com.example.glorious.response.data.WishlistData;
import com.example.glorious.ui.home.DetailDialogActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OrderHistoryAdaptor extends RecyclerView.Adapter<OrderHistoryAdaptor.ViewHolder> {
    ArrayList<OrderHistoryData> data;
    Context context;

    public OrderHistoryAdaptor(ArrayList<OrderHistoryData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.orderhistoyadaptor, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdaptor.ViewHolder holder, int position) {
        OrderHistoryData historyData=data.get(position);
        holder.name.setText(historyData.productName);
        holder.staus.setText(historyData.orderStatus);
        String ststus=historyData.orderStatus;
        Glide.with(context).load(historyData.productImage).placeholder(R.drawable.bag).into(holder.imageView);
        if(ststus.equals("Delivered")){
            holder.staus.setTextColor(Color.parseColor("#FF1A631E"));
        }else if(ststus.equals("Inprogress")){
            holder.staus.setTextColor(Color.parseColor("#009fe6"));
        }else if(ststus.equals("Rejected")){
            holder.staus.setTextColor(Color.parseColor("#FFEC1232"));
        }else {
            holder.staus.setTextColor(Color.parseColor("#fb891f"));
        }

        final  int itemPosition=position;

        holder.detail.setOnClickListener(view ->
                onClickCard( itemPosition
                ));
    }

    private void onClickCard(int itemPosition) {
        if (data != null) {
            Intent intent = new Intent(context, OrderHistoryActivity.class);
            OrderHistoryData dataa=data.get(itemPosition);
            String orderDetail = new Gson().toJson(dataa);
            Log.d(TAG, "onClick:  ProductDetail \n" + orderDetail);
            intent.putExtra("KeyConstants.Product_DETAIL", orderDetail);
            context.startActivity(intent);
            ((Activity)context).setResult(Activity.RESULT_OK);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,staus;
        CardView detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            staus=itemView.findViewById(R.id.status);
            detail=itemView.findViewById(R.id.detail);
        }
    }
}
