package com.example.glorious.ui.orderhistory;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.glorious.R;
import com.example.glorious.response.OrderHistoryResponse;
import com.example.glorious.response.data.OrderHistoryData;
import com.example.glorious.response.data.ProductData;
import com.google.gson.Gson;

public class OrderHistoryActivity extends AppCompatActivity {
    private String categoryName;
    private Gson gson = new Gson();
    OrderHistoryData data;
    ImageView product_image;
    RatingBar rating;
    TextView pname,status,itemPrice,product_category,product_Stock,productseller,productOffer,
            totalAmt,payment,name,address,mno,date,orderid,totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        product_image=findViewById(R.id.product_image);
        rating=findViewById(R.id.rating);
        pname=findViewById(R.id.pname);
        status=findViewById(R.id.status);
        itemPrice=findViewById(R.id.itemPrice);
        product_category=findViewById(R.id.product_category);
        product_Stock=findViewById(R.id.product_Stock);
        productseller=findViewById(R.id.productseller);
        productOffer=findViewById(R.id.productOffer);
        totalAmount=findViewById(R.id.totalAmount);
        totalAmt=findViewById(R.id.totalAmt);
        payment=findViewById(R.id.payment);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        mno=findViewById(R.id.mno);
        date=findViewById(R.id.date);
        orderid=findViewById(R.id.orderid);

        categoryName = getIntent().getStringExtra("KeyConstants.Product_DETAIL");
        Log.d(TAG, "Intent Data:" + categoryName);

        if (!TextUtils.isEmpty(categoryName)) {
            data = gson.fromJson(categoryName, OrderHistoryData.class);
            Glide.with(getApplicationContext()).load(data.productImage).placeholder(R.drawable.bag).into(product_image);
            rating.setRating(Float.parseFloat(data.productRating));
            pname.setText(data.productName);
            status.setText(data.orderStatus);
            itemPrice.setText(data.productPrice+" "+data.productUnitPiece);
            product_category.setText(data.productCategory);
            product_Stock.setText(data.productStock);
            productseller.setText(data.productSeller);
            productOffer.setText(data.productOffer);
            totalAmt.setText(data.orderAmount);
            payment.setText(data.paymentMethod);
            name.setText(data.orderShipName);
            address.setText(data.orderShipAddress+" "+data.orderShipAddress2+" "+data.orderCity+" "+data.orderState+" "+data.orderZip);
            mno.setText(data.orderPhone);
            date.setText(data.orderDate);
            orderid.setText(data.orderID);
            totalAmount.setText(data.totalAmoutPay);

        }
    }
}