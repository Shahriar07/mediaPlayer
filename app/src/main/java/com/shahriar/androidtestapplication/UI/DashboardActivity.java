package com.shahriar.androidtestapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shahriar.androidtestapplication.Adapter.SurahAdapter;
import com.shahriar.androidtestapplication.Adapter.SurahListAdapter;
import com.shahriar.androidtestapplication.Data.SurahInfo;
import com.shahriar.androidtestapplication.Data.Verse;
import com.shahriar.androidtestapplication.Interfaces.OnRecycleViewClicked;
import com.shahriar.androidtestapplication.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.androidtestapplication.Listeners.SurahItemTouchListener;
import com.shahriar.androidtestapplication.Listeners.VerseTouchListener;
import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;
import com.shahriar.androidtestapplication.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerLayout;

    // list to show surah
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView surahListView;

    private ArrayList<SurahInfo> surahInfoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        ApplicationContextManager.getInstance(this);
        initComponent();
    }

    void initComponent(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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



        surahListView = (RecyclerView) findViewById(R.id.surahList);
        mLayoutManager = new ScrollingLinearLayoutManager(this,5);
        surahListView.setLayoutManager(mLayoutManager);
        surahInfoList = getSurahInfoList();

        mAdapter = new SurahListAdapter(surahInfoList,this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.divider_item_decoration));
        surahListView.addItemDecoration(dividerItemDecoration);

        surahListView.setAdapter(mAdapter);

        surahListView.addOnItemTouchListener(new SurahItemTouchListener(getApplicationContext(), surahListView, new OnRecycleViewClicked(){
            @Override
            public void onClick(View view, int position) {
                SurahInfo info = surahInfoList.get(position);
                Toast.makeText(getApplicationContext(), info.getSurahName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent surahIntent = new Intent(DashboardActivity.this, SurahActivity.class);
                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,info.getSurahNumber());
                startActivity(surahIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Intent surahIntent = new Intent(DashboardActivity.this, SurahActivity.class);
//        if (id == R.id.nav_camera) {
//            surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,90);
//        } else if (id == R.id.nav_gallery) {
//            surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//
//        // set item as selected to persist highlight
//        item.setChecked(true);
//        // close drawer when item is tapped
//        mDrawerLayout.closeDrawers();
//
//        startActivity(surahIntent);

        // Add code here to update the UI based on the item selected
        // For example, swap UI fragments here

        return true;
    }

    private ArrayList<SurahInfo> getSurahInfoList (){
        ArrayList<SurahInfo> surahList = new ArrayList<>();
        surahList.add(new SurahInfo("Surah-al-balad", 90, false,120015));
        surahList.add(new SurahInfo("Surah-an-naas", 114, false,50015));
        return surahList;
    }
}
