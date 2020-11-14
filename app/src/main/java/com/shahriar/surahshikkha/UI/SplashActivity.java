package com.shahriar.surahshikkha.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.ApplicationContextManager;
import com.shahriar.surahshikkha.Utility.LocaleManager;

/**
 * Created by H. M. Shahriar on 5/14/2018.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationContextManager.getInstance(this);

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.onAttach(newBase));
    }
}
