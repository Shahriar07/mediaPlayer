package com.shahriar.surahshikkha.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by H. M. Shahriar on 5/14/2018.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
