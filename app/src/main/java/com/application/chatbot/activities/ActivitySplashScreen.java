package com.application.chatbot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.application.chatbot.AppConfig;
import com.application.chatbot.R;

public class ActivitySplashScreen extends AppCompatActivity {

    private ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img = findViewById(R.id.logo);

        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        img.startAnimation(aniFade);

        showState();
    }

    private void showState() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivitySplashScreen.this, ActivityHome.class));
                finish();
            }
        }, AppConfig.SPLASH_TIME);
    }
}
