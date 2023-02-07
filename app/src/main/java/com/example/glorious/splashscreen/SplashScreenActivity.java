package com.example.glorious.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.glorious.MainActivity;
import com.example.glorious.R;
import com.example.glorious.signin.SignInActivity;
import com.squareup.picasso.Picasso;

public class SplashScreenActivity extends AppCompatActivity {

    private final int stime=4000;
   // LinearLayout linearLayout;
   // ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //imageView=findViewById(R.id.simage);
        textView=findViewById(R.id.textView);
       // Picasso.with(this).load(R.drawable.search_border).into(imageView);
       // imageView.animate().translationY(-100).setDuration(1000).setStartDelay(500);
        textView.animate().translationY(-400).setDuration(1000).setStartDelay(500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));

                finish();
            }

        },stime);
    }
}