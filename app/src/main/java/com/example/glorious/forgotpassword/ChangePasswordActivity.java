package com.example.glorious.forgotpassword;

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

public class ChangePasswordActivity extends AppCompatActivity {
    EditText editText1,editText2;
    ProgressDialog progressDialog;
    String mno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editText1=findViewById(R.id.tvPassword1);
        editText2=findViewById(R.id.tvconPassword1);
        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mno=bundle.getString("Mobile");
        }
        findViewById(R.id.btnLogin1).setOnClickListener(view -> {
            checkValidation(editText1.getText().toString().trim(),editText2.getText().toString().trim());
        });
    }
    public void checkValidation(String pass, String checkPassword) {
        if (TextUtils.isEmpty(editText1.getText().toString())) {
            editText1.setError(getString(R.string.please_enter_valid_number));
            editText1.requestFocus();
        } else if (TextUtils.isEmpty(editText2.getText().toString())) {
            editText2.setError(getString(R.string.please_enter_password));
            editText2.requestFocus();
        }else if(!pass.equals(checkPassword)){
            Toast.makeText(this, "Password is not same", Toast.LENGTH_SHORT).show();
        }

        else {
            if (Utility.isConnected(ChangePasswordActivity.this)) {
                callApi(mno,checkPassword);
            } else {
                Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callApi(String mno, String checkPassword) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<SignUpResponse> call= AppRetrofit.getClient().create(ApiServices.class).updatePassword(mno,checkPassword);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d("Response",response.toString());
                progressDialog.dismiss();

                SignUpResponse sign= response.body();
                if(sign.status.equals("true")){
                    Toast.makeText(ChangePasswordActivity.this, sign.msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }else {
                    Toast.makeText(ChangePasswordActivity.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("error",t.toString());
                progressDialog.dismiss();


            }
        });
    }
}