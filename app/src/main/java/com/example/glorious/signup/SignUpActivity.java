package com.example.glorious.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.SignUpResponse;
import com.example.glorious.signin.SignInActivity;
import com.example.glorious.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity  {
    EditText fname,lname,email,mnumber,password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        mnumber=findViewById(R.id.mobile);
        password=findViewById(R.id.password);
        progressDialog = new ProgressDialog(SignUpActivity.this);

        findViewById(R.id.text_signUp).setOnClickListener(view -> {
            checkValidation(mnumber.getText().toString().trim(),password.getText().toString().trim(),
                    fname.getText().toString().trim(),lname.getText().toString().trim(),email.getText().toString().trim());
        });
    }

    public void checkValidation(String checkmNumber, String checkPassword,String finame,String laname,String eemail) {
        String s="^([0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";
        if (mnumber.getText().toString().length() < 10) {
            mnumber.setError(getString(R.string.please_enter_valid_number));
            mnumber.requestFocus();
        } else if (TextUtils.isEmpty(fname.getText().toString())) {
            fname.setError(getString(R.string.please_enter_value));
            fname.requestFocus();
        } else if (TextUtils.isEmpty(lname.getText().toString())) {
            lname.setError(getString(R.string.please_enter_value));
            lname.requestFocus();
        } else if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError(getString(R.string.please_enter_value));
            email.requestFocus();
        }else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError(getString(R.string.please_enter_password));
            password.requestFocus();
        }  else {

            if (Utility.isConnected(SignUpActivity.this)) {
                callApi(checkmNumber,checkPassword,eemail,laname,finame);
            } else {
                Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callApi(String checkmNumber, String checkPassword, String eemail, String laname, String finame) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).signUp(finame,laname,eemail,checkmNumber,checkPassword);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d("Signup",response.toString());
                progressDialog.dismiss();

                SignUpResponse sign=response.body();
                if(sign.status.equals("true")){
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }else{
                    Toast.makeText(SignUpActivity.this, sign.msg, Toast.LENGTH_SHORT).show();
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