package net.gumcode.malangvoice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import net.gumcode.malangvoice.R;

/**
 * Created by A. Fauzi Harismawan on 12/31/2015.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent change = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(change);
                finish();
            }
        }, 2000);
    }
}
