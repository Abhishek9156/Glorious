package com.example.glorious.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glorious.MainActivity;
import com.example.glorious.R;
import com.example.glorious.forgotpassword.ForgotPasswordActivity;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.LoginResponse;
import com.example.glorious.signup.SignUpActivity;
import com.example.glorious.util.BaseApplication;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;
import com.example.glorious.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    TextView login,signup,forgotpass;
    EditText input,password;
    ApiServices apiInterface;
    String username;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signup= findViewById(R.id.text_signup);
        login=findViewById(R.id.text_login);
        forgotpass=findViewById(R.id.forgotPass);
        apiInterface = AppRetrofit.getClient().create(ApiServices.class);
        input=findViewById(R.id.input_number);
        password=findViewById(R.id.input_password);
        progressDialog = new ProgressDialog(SignInActivity.this);
       username= SharedPreferenceUtil.getInstance(getApplicationContext()).getStringValue(KeyConstants.UID,"");
        if(!username.isEmpty()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
       signup.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
        login.setOnClickListener(view -> {

            checkValidation(input.getText().toString().trim(),password.getText().toString().trim());

        });
        forgotpass.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
        });
    }


    public void checkValidation(String checkNumber, String checkPassword) {
        if (input.getText().toString().length() < 10) {
            input.setError(getString(R.string.please_enter_valid_number));
            input.requestFocus();
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError(getString(R.string.please_enter_password));
            password.requestFocus();
        } else {
            if (Utility.isConnected(SignInActivity.this)) {
                callApi(checkNumber,checkPassword);
            } else {
                Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void callApi(String number, String password) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<LoginResponse> call= AppRetrofit.getClient().create(ApiServices.class).login(number,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("data",response+"");
                progressDialog.dismiss();
                LoginResponse response1= response.body();
                if(response1.status.equals("true")) {

                   SharedPreferenceUtil
                            .getInstance(getApplicationContext())
                            .setValue(KeyConstants.UID, response1.data.uid);
                    SharedPreferenceUtil
                            .getInstance(getApplicationContext())
                            .setValue(KeyConstants.FNAME, response1.data.fname);
                    SharedPreferenceUtil
                            .getInstance(getApplicationContext())
                            .setValue(KeyConstants.EMAIL, response1.data.email);
                    SharedPreferenceUtil
                            .getInstance(getApplicationContext())
                            .setValue(KeyConstants.MOBILE, response1.data.mobile);
                    SharedPreferenceUtil
                            .getInstance(getApplicationContext())
                            .setValue(KeyConstants.LNAME, response1.data.lname);
                    SharedPreferenceUtil
                            .getInstance(getApplicationContext())
                            .setValue(KeyConstants.CREATED_AT, response1.data.created_at);


                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    Toast.makeText(SignInActivity.this, response1.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("fail",t.toString());
            }
        });
    }


}