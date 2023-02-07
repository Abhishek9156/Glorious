package com.example.glorious.cart;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.glorious.MainActivity;
import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.SignUpResponse;
import com.example.glorious.response.data.ProductData;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDataAdaptor extends RecyclerView.Adapter<CartDataAdaptor.Viewholder> {
    Context context;
    ArrayList<ProductData> data;
    String orderQuantity;
    public CartDataAdaptor(Context context, ArrayList<ProductData> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclercartdata, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ProductData productData=data.get(position);
        holder.amount.setText("₹"+productData.productPrice);
        Glide.with(context).load(productData.productImage).placeholder(R.drawable.bag).into(holder.product_image);
        holder.rating.setRating(Float.parseFloat(productData.productRating));
        holder.itemName.setText(productData.productName);
        holder.color.setText(productData.productColor);
        holder.seller.setText(productData.productSeller);
        final int itemPosition=position;
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(arrayAdapter);

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               orderQuantity = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.buyy.setOnClickListener(view ->
            buyorder( itemPosition
        ));


        holder.back.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            ((Activity)context).setResult(Activity.RESULT_OK);
        });

        holder.remove.setOnClickListener(view ->

                onClickCard( itemPosition
                ));


    }

    private void buyorder(int itemPosition) {
        String uid=SharedPreferenceUtil.getInstance(context).getStringValue(KeyConstants.UID,"");
        String pid=data.get(itemPosition).productID;
        Intent intent = new Intent(context, CartAddressActivity.class);
        Log.d(TAG, "onClick:  ProductDetail \n" + uid+pid+orderQuantity);
        intent.putExtra(KeyConstants.UID, uid);
        intent.putExtra(KeyConstants.PID, pid);
        intent.putExtra(KeyConstants.PQUANTYTY, orderQuantity);
        intent.putExtra(KeyConstants.PNAME, data.get(itemPosition).productName);
        intent.putExtra(KeyConstants.PAMOUNT, data.get(itemPosition).productPrice);
        intent.putExtra(KeyConstants.PIMAGE, data.get(itemPosition).productImage);
        intent.putExtra(KeyConstants.PCOLOR, data.get(itemPosition).productColor);
        context.startActivity(intent);
        ((Activity)context).setResult(Activity.RESULT_OK);


    }

    private void onClickCard(int itemPosition) {
        String uid=SharedPreferenceUtil.getInstance(context).getStringValue(KeyConstants.UID,"");
        String pid=data.get(itemPosition).productID;
        callApi(uid,pid);

    }

    private void callApi(String uid, String pid) {
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).removecartdata(uid,pid);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d("Response",response.toString());
                SignUpResponse upResponse=response.body();
                if(upResponse.status.equals("true")){
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).setResult(Activity.RESULT_OK);
                    Toast.makeText(context, upResponse.msg, Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, upResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        Spinner spinner;
        ImageView product_image;
        TextView itemName,color,seller,amount,back,remove,buyy;
        RatingBar rating;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            spinner=itemView.findViewById(R.id.spinner);
            product_image=itemView.findViewById(R.id.product_image);
            itemName=itemView.findViewById(R.id.itemName);
            color=itemView.findViewById(R.id.color);
            seller=itemView.findViewById(R.id.seller);
            rating=itemView.findViewById(R.id.rating);
            amount=itemView.findViewById(R.id.amount);
            back=itemView.findViewById(R.id.back);
            remove=itemView.findViewById(R.id.remove);
            buyy=itemView.findViewById(R.id.odertxtdeli);
        }
    }
}
