package com.example.glorious.wishlist;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.glorious.MainActivity;
import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.SignUpResponse;
import com.example.glorious.response.WishlistResponse;
import com.example.glorious.response.data.ProductData;
import com.example.glorious.response.data.WishlistData;
import com.example.glorious.ui.home.DetailDialogActivity;
import com.example.glorious.ui.home.HomeFragment;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WishlistAdaptor extends RecyclerView.Adapter<WishlistAdaptor.RecyclerViewViewHolder> {
    ArrayList<WishlistData> wishlistData;
    Context context;

    public WishlistAdaptor( Context context,ArrayList<WishlistData> wishlistData) {
        this.wishlistData = wishlistData;
        this.context = context;
    }

    @NonNull
    @Override
    public WishlistAdaptor.RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.datadaptorrecycler, parent, false);
        return new RecyclerViewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdaptor.RecyclerViewViewHolder holder, int position) {
        WishlistData dateDatas=wishlistData.get(position);
        Glide.with(context).load(dateDatas.productImage).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
        String rating=dateDatas.productRating;
        holder.tvName.setText(dateDatas.productName);
        holder.tvdescription.setText("Seller: "+dateDatas.productSeller);
        holder.tvquantity.setText("Quantity: "+dateDatas.productStock);
        if(rating.equals("0.0") || rating==null){
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
        if(dateDatas.status.equals("1")){
            Glide.with(context).load(R.drawable.wishlistred).placeholder(R.drawable.wishlistcartline_24).into(holder.favicon);
        }
        final  int itemPosition=position;

        holder.cardView.setOnClickListener(view ->
                onClickCard( itemPosition
                ));
        holder.favicon.setOnClickListener(view ->  removewishlist( itemPosition));
    }

    private void removewishlist(int itemPosition) {
        String pid=wishlistData.get(itemPosition).productID;
        String uid= SharedPreferenceUtil.getInstance(context).getStringValue(KeyConstants.UID,"");
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).removewishlistdata(pid,uid);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse upResponse=response.body();
                if(upResponse.status.equals("true")){
                    Toast.makeText(context, upResponse.msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                                       context.startActivity(intent);
                    ((Activity)context).setResult(Activity.RESULT_OK);
                }else {
                    Toast.makeText(context, upResponse.msg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

            }
        });
    }

    private void onClickCard(int itemPosition) {
        if (wishlistData != null) {
            Intent intent = new Intent(context, DetailDialogActivity.class);
            WishlistData data=wishlistData.get(itemPosition);
            String orderDetail = new Gson().toJson(data);
            Log.d(TAG, "onClick:  ProductDetail \n" + orderDetail);
            intent.putExtra("KeyConstants.Product_DETAIL", orderDetail);
            context.startActivity(intent);
            ((Activity)context).setResult(Activity.RESULT_OK);
        }
    }

    @Override
    public int getItemCount() {
        return wishlistData.size();
    }

    public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvdescription,tvquantity,tvprice,tvoffer;
        ImageView imageView,favicon;
        RatingBar ratingBar;
        CardView cardView;
        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvProduct);
            tvdescription=itemView.findViewById(R.id.tvdescription);
            tvquantity=itemView.findViewById(R.id.tvquantity);
            ratingBar=itemView.findViewById(R.id.tvrating);
            tvprice=itemView.findViewById(R.id.tvprice);
            imageView=itemView.findViewById(R.id.ivproduct);
            tvoffer=itemView.findViewById(R.id.tvoffer);
            favicon=itemView.findViewById(R.id.imfav);
            cardView=itemView.findViewById(R.id.cardviews);
        }
    }
}
