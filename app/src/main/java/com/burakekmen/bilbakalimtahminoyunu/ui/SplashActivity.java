package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.burakekmen.bilbakalimtahminoyunu.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        gecisYap();
    }


    private void gecisYap(){
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, UyelikActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
