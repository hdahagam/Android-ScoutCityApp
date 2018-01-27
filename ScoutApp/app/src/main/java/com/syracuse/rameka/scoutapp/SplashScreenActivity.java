/**
 * @FileName SplashScreenActivity.java
 * @Functionality

 * This is the first activity launced when the application is launched. This screen stay for 4 seconds before it disappears
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreenActivity extends AppCompatActivity {

    private static int TIME_INTERVAL = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //This activity is run in a thread asynchronously to achieve the effect.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },TIME_INTERVAL);
    }
}
