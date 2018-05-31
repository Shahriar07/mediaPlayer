package com.shahriar.surahshikkha.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.ApplicationContextManager;
import com.shahriar.surahshikkha.Utility.LocaleManager;
import com.shahriar.surahshikkha.Utility.Utility;

import java.util.Locale;

/**
 * Created by H. M. Shahriar on 5/14/2018.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationContextManager.getInstance(this);
        Locale locale = Utility.getCurrentLocale(this);
        LocaleManager.setLocale(this);
        Log.d(getClass().getSimpleName(), "Locale " + locale.getLanguage() + " country " + locale.getCountry());
       // setContentView(R.layout.splash_activity_layout);
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
