package com.example.smartbin.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbin.R;
import com.example.smartbin.model.ModelUser;
import com.example.smartbin.utils.AppUtils;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Animation spls_anim,spls_fade,spls_anim_top;
    ImageView sbImg,ccImg,logoImg;
    TextView ewText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoImg = findViewById(R.id.logo);
        sbImg = findViewById(R.id.sbText);
        ccImg = findViewById(R.id.cc);
        ewText = findViewById(R.id.ewText);

        spls_anim = AnimationUtils.loadAnimation(this,R.anim.spls_anim);
        spls_fade = AnimationUtils.loadAnimation(this,R.anim.spls_fade);
        spls_anim_top = AnimationUtils.loadAnimation(this,R.anim.spls_anim_top);

        ewText.setAnimation(spls_anim_top);
        logoImg.setAnimation(spls_fade);
        sbImg.setAnimation(spls_anim);
        ccImg.setAnimation(spls_anim);

        Thread thread = new Thread()
        {
            @Override
            public void run() {
                try{
                    sleep(5000); }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    ModelUser.LoginAuth user = AppUtils.getUserModel();
                    if (user == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    }
                    finish();
                }
                //super.run();
            }
        };
        thread.start();
    }
}
