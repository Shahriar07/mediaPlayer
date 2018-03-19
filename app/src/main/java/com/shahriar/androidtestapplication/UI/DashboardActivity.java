package com.shahriar.androidtestapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;
import com.shahriar.androidtestapplication.Utility.Constants;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        initComponent();
        ApplicationContextManager.getInstance(this);
    }

    void initComponent(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        Intent surahIntent = new Intent(DashboardActivity.this, SurahActivity.class);
//                        switch (v.getId()){
//                            case R.id.surah_balad:
//                                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,90);
//                                break;
//                            case R.id.surah_naas:
                                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
//                                break;
//                            default:
//                                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
//                                break;
//                        }

                        startActivity(surahIntent);

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );



    }

    @Override
    public void onClick(View v) {
//        Intent surahIntent = new Intent(this, SurahActivity.class);
//        switch (v.getId()){
//            case R.id.surah_balad:
//                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,90);
//                break;
//            case R.id.surah_naas:
//                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
//                break;
//            default:
//                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
//                break;
//        }
//
//        startActivity(surahIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
