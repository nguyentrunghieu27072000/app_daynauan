package com.example.nguyentung.NauAnVungMien;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar prLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prLoad = (ProgressBar) findViewById(R.id.prLoad);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Viec ban se thao tac vs UI
                Intent main = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        },1000);
        prLoad.setVisibility(View.VISIBLE);
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        th2.start();
    }
}
