package com.app.superdistributor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView LottieHomeView;

    TextView MainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieHomeView = findViewById(R.id.lottiehomeview);
        MainTextView = findViewById(R.id.main_text);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        MainTextView.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this, WelcomeActivity.class);
                MainActivity.this.startActivity(i);
                MainActivity.this.finish();
            }
        },4500);

    }
}