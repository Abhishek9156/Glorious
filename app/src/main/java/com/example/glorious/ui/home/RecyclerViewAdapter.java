package com.example.glorious.ui.home;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.glorious.R;
import com.example.glorious.response.data.ProductData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    ArrayList<ProductData> productData;
    Context context;
    private ArrayList<ProductData> searchData;

    public RecyclerViewAdapter(Context co, ArrayList<ProductData> mCustomerList) {
        this.context = co;
        this.productData = mCustomerList;
       this.searchData = new ArrayList<ProductData>();
        this.searchData.addAll(productData);
    }



    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.datadaptorrecycler, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        ProductData dateDatas= productData.get(position);
       Glide.with(context).load(dateDatas.productImage).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);

        holder.tvName.setText(dateDatas.productName);
        holder.tvdescription.setText(dateDatas.productdetail);
        holder.tvquantity.setText("Quantity: "+dateDatas.productStock);
        if(dateDatas.productRating.equals("0.0") || dateDatas.productRating==null){
            holder.ratingBar.setVisibility(View.GONE);
        }else {
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(Float.parseFloat(dateDatas.productRating));
        }
        holder.tvprice.setText("MRP ₹"+dateDatas.productPrice+dateDatas.productUnitPiece);

        if(dateDatas.productOffer==null){
            holder.tvoffer.setVisibility(View.GONE);
        }else {
            holder.tvoffer.setVisibility(View.VISIBLE);
            holder.tvoffer.setText(dateDatas.productOffer);
        }

        final  int itemPosition=position;

        holder.detailbnt.setOnClickListener(view ->
            onClickCard( itemPosition
            ));
   }



    private void onClickCard(  int itemPosition) {
        if (productData != null) {
            Intent intent = new Intent(context, DetailDialogActivity.class);
            ProductData data=productData.get(itemPosition);
            String orderDetail = new Gson().toJson(data);
            Log.d(TAG, "onClick:  ProductDetail \n" + orderDetail);
            intent.putExtra("KeyConstants.Product_DETAIL", orderDetail);
            context.startActivity(intent);
            ((Activity)context).setResult(Activity.RESULT_OK);
        }

    }

    @Override
    public int getItemCount() {
      return   productData.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productData.clear();
        if (charText.length() == 0) {
            productData.addAll(searchData);
        } else {
            for (ProductData wp : searchData) {
                if (wp.productName.toLowerCase(Locale.getDefault()).contains(charText)
                        ||wp.productCategory.toLowerCase(Locale.getDefault()).contains(charText)) {
                    productData.add(wp);
                }
            }
        }
        notifyDataSetChanged();

    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvdescription,tvquantity,tvprice,tvoffer;
        ImageView imageView;
        RatingBar ratingBar;
       //LinearLayout addtocart;
        CardView detailbnt;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvProduct);
            tvdescription=itemView.findViewById(R.id.tvdescription);
            tvquantity=itemView.findViewById(R.id.tvquantity);
            ratingBar=itemView.findViewById(R.id.tvrating);
            tvprice=itemView.findViewById(R.id.tvprice);
            imageView=itemView.findViewById(R.id.ivproduct);
            tvoffer=itemView.findViewById(R.id.tvoffer);
           // addtocart=itemView.findViewById(R.id.linearlayour);
            detailbnt=itemView.findViewById(R.id.cardviews);
        }
    }
}


