package com.shahriar.androidtestapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.Constants;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    Button surahButton;
    Button surahButton2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        initComponent();
    }

    void initComponent(){
        surahButton = (Button) findViewById(R.id.surah_balad);
        surahButton.setOnClickListener(this);

        surahButton2 = (Button) findViewById(R.id.surah_naas);
        surahButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent surahIntent = new Intent(this, SurahActivity.class);
        switch (v.getId()){
            case R.id.surah_balad:
                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,90);
                break;
            case R.id.surah_naas:
                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
                break;
            default:
                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
                break;
        }

        startActivity(surahIntent);
    }
}
