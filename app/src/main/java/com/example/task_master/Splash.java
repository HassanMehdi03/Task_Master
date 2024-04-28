package com.example.task_master;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvSlogan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();

        Animation logo_anim= AnimationUtils.loadAnimation(this,R.anim.logo_anim);
        Animation slogan_anim= AnimationUtils.loadAnimation(this,R.anim.slogan_anim);

        ivLogo.setAnimation(logo_anim);
        tvSlogan.setAnimation(slogan_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(Splash.this, Login.class);
                startActivity(i);
                finish();
            }
        },2000);


    }

    public void init()
    {
        ivLogo=findViewById(R.id.ivLogo);
        tvSlogan=findViewById(R.id.tvSlogan);
    }


}