package com.example.glorious.ui.home;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.glorious.MainActivity;
import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.SignUpResponse;
import com.example.glorious.response.data.ProductData;
import com.example.glorious.util.BaseApplication;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDialogActivity extends AppCompatActivity {
    private String productDetail;
    ProductData data;
    String pid;
    private Gson gson = new Gson();
    ImageView productIamge;
    RatingBar ratingBar;
    ProgressDialog progressDialog;
    TextView itemName,itemPrice,product_category,product_Stock,productseller,
            productOffer,productdetail,addtowishlist,addtocart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_dialog);
        progressDialog = new ProgressDialog(DetailDialogActivity.this);

        productIamge = findViewById(R.id.product_image);
        ratingBar = findViewById(R.id.rating);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        product_category = findViewById(R.id.product_category);
        product_Stock = findViewById(R.id.product_Stock);
        productseller = findViewById(R.id.productseller);
        productOffer = findViewById(R.id.productOffer);
        productdetail = findViewById(R.id.productdetail);
        addtowishlist = findViewById(R.id.addtowishlist);
        addtocart = findViewById(R.id.addtocart);

        productDetail = getIntent().getStringExtra("KeyConstants.Product_DETAIL");
        Log.d(TAG, "Intent Data:" + productDetail);
        if (!TextUtils.isEmpty(productDetail)) {
            data = gson.fromJson(productDetail, ProductData.class);
            Glide.with(getApplicationContext()).load(data.productImage).placeholder(R.drawable.bag).into(productIamge);
            ratingBar.setRating(Float.parseFloat(data.productRating));
            itemName.setText(data.productName);
            itemPrice.setText("₹"+data.productPrice+data.productUnitPiece);
            product_category.setText(data.productCategory);
            product_Stock.setText(data.productStock);
            productseller.setText(data.productSeller);
            productOffer.setText(data.productOffer);
            productdetail.setText(data.productdetail);
            pid=data.productID;
        }
        String loginType= SharedPreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(KeyConstants.UID ,"");

        addtocart.setOnClickListener(view -> {
            addtocartApi(loginType,pid);
        });

        addtowishlist.setOnClickListener(view -> {
            addtowishlistApi(loginType,pid);
        });

    }

    private void addtowishlistApi(String userid,String productid) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).addtowishlist(productid,userid);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                progressDialog.dismiss();
                Log.d("Response",response.toString());

                SignUpResponse upResponse=response.body();
                if(upResponse.status.equals("true")){
                    Toast.makeText(DetailDialogActivity.this, upResponse.msg, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailDialogActivity.this, upResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Error",t.toString());
            }
        });
    }

    private void addtocartApi(String userid,String productid) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).addtoCart(userid,productid);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                progressDialog.dismiss();
                Log.d("Response",response.toString());


                SignUpResponse upResponse=response.body();
                if(upResponse.status.equals("true")){
                    Toast.makeText(DetailDialogActivity.this, upResponse.msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else {
                    Toast.makeText(DetailDialogActivity.this, upResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Error",t.toString());


            }
        });
    }
}