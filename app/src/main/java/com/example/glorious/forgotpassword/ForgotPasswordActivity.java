package com.example.glorious.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.SignUpResponse;
import com.example.glorious.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
TextView btnsend;
EditText mnumber;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mnumber=findViewById(R.id.editTextPhone);
        btnsend=findViewById(R.id.btnsend);
        btnsend.setOnClickListener(view -> {
            checkValidation(mnumber.getText().toString().trim());
        });
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);

    }
    public void checkValidation(String checkNumber) {
        if (TextUtils.isEmpty(mnumber.getText().toString())) {
            mnumber.setError(getString(R.string.enter_mobile_number));
            mnumber.requestFocus();
        } else {
            if (Utility.isConnected(ForgotPasswordActivity.this)) {
                callApi(checkNumber);
            } else {
                Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callApi(String checkNumber) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).isuserRegister(checkNumber);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d("Response",response.toString());
                progressDialog.dismiss();

                SignUpResponse upResponse= response.body();
                if(upResponse.status.equals("true")) {
                    startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                }else{
                    Toast.makeText(ForgotPasswordActivity.this, upResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("Error",t.toString());
                progressDialog.dismiss();

            }
        });
    }
}