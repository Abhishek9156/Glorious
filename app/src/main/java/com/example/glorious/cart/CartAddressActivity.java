package com.example.glorious.cart;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.glorious.MainActivity;
import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.SignUpResponse;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartAddressActivity extends AppCompatActivity {
    String uid,pid,orderquantity,img,pname,price,color;
    TextView name,tvprice,quantity,totalamt,buy;
    ImageView imageView;
    EditText userName,numberText,address1,address2,city,state,zipcode;
    ProgressDialog progressDialog;
    float totalamountt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_address);

        uid = getIntent().getStringExtra(KeyConstants.UID);
        pid = getIntent().getStringExtra(KeyConstants.PID);
        orderquantity = getIntent().getStringExtra(KeyConstants.PQUANTYTY);
        img = getIntent().getStringExtra(KeyConstants.PIMAGE);
        pname = getIntent().getStringExtra(KeyConstants.PNAME);
        price = getIntent().getStringExtra(KeyConstants.PAMOUNT);
        color = getIntent().getStringExtra(KeyConstants.PCOLOR);
        Log.d(TAG, "onClick:  ProductDetail \n" + uid+pid+orderquantity);

        name=findViewById(R.id.name);
        buy=findViewById(R.id.buy);
        tvprice=findViewById(R.id.price);
        quantity=findViewById(R.id.quantity);
        totalamt=findViewById(R.id.totalamt);
        imageView=findViewById(R.id.image);
        userName=findViewById(R.id.cuserName);
        numberText=findViewById(R.id.numberText);
        address1=findViewById(R.id.address1);
        address2=findViewById(R.id.address2);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        zipcode=findViewById(R.id.zipcode);
        progressDialog = new ProgressDialog(CartAddressActivity.this);

        name.setText(pname);
        Glide.with(getApplicationContext()).load(img).into(imageView);
        tvprice.setText(price);
        quantity.setText(orderquantity);
         totalamountt=Float.parseFloat(orderquantity.trim())*Float.parseFloat(price.trim());
        totalamt.setText("₹"+totalamountt);

        buy.setOnClickListener(view -> {
            checkValidation(userName.getText().toString(),numberText.getText().toString(),address1.getText().toString(),
                    address2.getText().toString(),city.getText().toString(),state.getText().toString(),zipcode.getText().toString()  );
        });

    }
    public void checkValidation(String name, String mno, String address, String addredd, String scity, String sstate, String spincode) {
        if (TextUtils.isEmpty(userName.getText().toString())) {
            userName.setError(getString(R.string.please_enter_value));
            userName.requestFocus();
        }else if (numberText.getText().toString().length() < 10) {
            numberText.setError(getString(R.string.please_enter_valid_number));
            numberText.requestFocus();
        }  else if (TextUtils.isEmpty(address1.getText().toString())) {
            address1.setError(getString(R.string.please_enter_value));
            address1.requestFocus();
        }else if (TextUtils.isEmpty(address2.getText().toString())) {
            address2.setError(getString(R.string.please_enter_value));
            address2.requestFocus();
        }else if (TextUtils.isEmpty(city.getText().toString())) {
            city.setError(getString(R.string.please_enter_value));
            city.requestFocus();
        }else if (TextUtils.isEmpty(state.getText().toString())) {
            state.setError(getString(R.string.please_enter_value));
            state.requestFocus();
        }else if (TextUtils.isEmpty(zipcode.getText().toString())) {
            zipcode.setError(getString(R.string.please_enter_value));
            zipcode.requestFocus();
        } else {
            if (Utility.isConnected(CartAddressActivity.this)) {
                callApi(name,mno,address,addredd,scity,sstate,spincode);
            } else {
                Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callApi(String name, String mno, String address, String addredd, String scity, String sstate, String spincode) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).ordered(
                uid,price,name,address,addredd,scity,sstate,spincode,mno,orderquantity,pid, String.valueOf(totalamountt));
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                progressDialog.dismiss();

                Log.d("Response",response.toString());
                SignUpResponse upResponse= response.body();
                if(upResponse.status.equals("true")){
                    Toast.makeText(CartAddressActivity.this, upResponse.msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else {
                    Toast.makeText(CartAddressActivity.this, upResponse.msg, Toast.LENGTH_SHORT).show();
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