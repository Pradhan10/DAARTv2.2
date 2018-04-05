package com.testworkshop.prototype_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {
    private static final String FLAG = "NOT_logged_in";
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_splash);
        //Check if not logged in, redirect to login if needed

        boolean b = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferencs = getSharedPreferences(FLAG, Context.MODE_PRIVATE);
                if (sharedPreferencs.getBoolean(FLAG, true)) {
                    //User using app for first time, goto login
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);


    }
}