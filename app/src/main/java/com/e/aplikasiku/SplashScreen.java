package com.e.aplikasiku;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private static int timeout=5000;

    private ImageView Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Img = (ImageView) findViewById(R.id.imgg);

        Animation animation= AnimationUtils.loadAnimation(SplashScreen.this, R.anim.animation);
        Img.startAnimation(animation);


        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LamanUtama.class);
                startActivity(intent);
                finish();
            }
        },timeout);
    }
    }


