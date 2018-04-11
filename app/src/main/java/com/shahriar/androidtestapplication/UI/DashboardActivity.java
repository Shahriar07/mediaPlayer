package com.shahriar.androidtestapplication.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shahriar.androidtestapplication.Adapter.SurahListAdapter;
import com.shahriar.androidtestapplication.Data.SurahInfo;
import com.shahriar.androidtestapplication.Interfaces.OnRecycleViewClicked;
import com.shahriar.androidtestapplication.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.androidtestapplication.Listeners.SurahItemTouchListener;
import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;
import com.shahriar.androidtestapplication.Utility.Constants;
import com.shahriar.androidtestapplication.Utility.SharedPreferenceController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerLayout;

    // list to show surah
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView surahListView;

    private SwitchCompat menuRepeatSwitch;
    private TextView drawerMaxRepeatCount;

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

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.loop_control_switch);
        View actionView = menuItem.getActionView();//MenuItemCompat.getActionView(menuItem);

        menuRepeatSwitch = (SwitchCompat) actionView.findViewById(R.id.switcher);
        final SharedPreferenceController controller = new SharedPreferenceController();
        boolean isRepeatOn = controller.readBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL);
        menuRepeatSwitch.setChecked(isRepeatOn);
        menuRepeatSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.writeBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL,menuRepeatSwitch.isChecked());
            }
        });

        menuItem = menu.findItem(R.id.max_loop_count_control);
        actionView = menuItem.getActionView();//MenuItemCompat.getActionView(menuItem);
        drawerMaxRepeatCount = (TextView) actionView.findViewById(R.id.menu_max_repeat_count);
        int maxRepeatCount = controller.readIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT);
        if (maxRepeatCount == 0) {
            controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT, Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT);
            maxRepeatCount = Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT;
        }
        drawerMaxRepeatCount.setText(""+maxRepeatCount);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();
        int id = item.getItemId();

        // Handle navigation view item clicks here.
        switch(id){
            case R.id.max_loop_count_control:
            {
                showMaxLoopCountPopup();
                break;
            }
            case R.id.loop_control_switch:
            {
                final SharedPreferenceController controller = new SharedPreferenceController();
                if(menuRepeatSwitch.isChecked()){
                    menuRepeatSwitch.setChecked(false);
                    controller.writeBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL,false);
                }
                else{
                    menuRepeatSwitch.setChecked(true);
                    controller.writeBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL,true);
                }
                break;
            }
        }
        return true;
    }

    private void showMaxLoopCountPopup() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builderSingle.setTitle("Repeat Count");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        for (int i = 5; i<15; i++) {
            arrayAdapter.add(""+i);
        }
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final SharedPreferenceController controller = new SharedPreferenceController();
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int maxRepeatCount = Integer.parseInt(arrayAdapter.getItem(which));
                controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT,maxRepeatCount);
                drawerMaxRepeatCount.setText(""+maxRepeatCount);
            }
        });
        builderSingle.create().show();
    }

    private ArrayList<SurahInfo> getSurahInfoList (){
        ArrayList<SurahInfo> surahList = new ArrayList<>();
        surahList.add(new SurahInfo("Surah-al-Fatihah", 1, false,51015,7));
        surahList.add(new SurahInfo("Surah-al-balad", 90, false,120015,20));
        surahList.add(new SurahInfo("Surah-an-naas", 114, false,50015,6));
        return surahList;
    }
}
