package com.kabadiwala.fujitsu.thekabadiwala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread splashthread = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });
                    finish();
                    overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        splashthread.start();

    }

    @Override
    public void onBackPressed() {}

}

