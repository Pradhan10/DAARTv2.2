package com.testworkshop.prototype_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {

    private static final String FLAG = "NOT_logged_in";
    private final String TAG = "IntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_intro);
        addSlide(IntroFragment.newInstance(R.layout.intro1));

        setColorDoneText(0xaaffff00);

    }


    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.

    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when slide is changed
        SharedPreferences sharedPreferencs = getSharedPreferences(FLAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPreferencs.edit();
        e.putBoolean(FLAG, false);
        e.apply();
    }
}
