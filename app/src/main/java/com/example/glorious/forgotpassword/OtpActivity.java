package com.example.glorious.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.glorious.R;

public class OtpActivity extends AppCompatActivity {
TextView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            startActivity(new Intent(OtpActivity.this,ChangePasswordActivity.class));
        });
    }
}