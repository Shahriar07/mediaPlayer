package com.shahriar.surahshikkha.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shahriar.surahshikkha.Adapter.SurahListAdapter;
import com.shahriar.surahshikkha.Data.SurahInfo;
import com.shahriar.surahshikkha.Dialog.LanguageDialog;
import com.shahriar.surahshikkha.Interfaces.LanguageDialogInterface;
import com.shahriar.surahshikkha.Interfaces.OnRecycleViewClicked;
import com.shahriar.surahshikkha.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.surahshikkha.Listeners.SurahItemTouchListener;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.ApplicationContextManager;
import com.shahriar.surahshikkha.Utility.Constants;
import com.shahriar.surahshikkha.Utility.LocaleManager;
import com.shahriar.surahshikkha.Utility.SharedPreferenceController;
import com.shahriar.surahshikkha.Utility.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerLayout;

    // list to show surah
    private RecyclerView.LayoutManager mLayoutManager;
    private SurahListAdapter mAdapter;
    private RecyclerView surahListView;

    private SwitchCompat menuRepeatSwitch;
    private TextView drawerMaxRepeatCount;
    private TextView drawerSelectedLanguage;

    private ArrayList<SurahInfo> surahInfoList;
    SharedPreferenceController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new SharedPreferenceController(this);
        //int index = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);
       // Context context = LocaleManager.setLocale(DashboardActivity.this, index==0?"en":"bn");

        ApplicationContextManager.getInstance(this);
        initComponent(this);
    }

    void initComponent(Context context){

        setContentView(R.layout.drawer_layout);
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
        int type = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL,Constants.SURAH_VERSE_SORT_BY_NUMBER);
        sortList(type);
        mAdapter = new SurahListAdapter(surahInfoList,context);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_item_decoration));
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
        if (maxRepeatCount == -1) {
            controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT, Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT);
            maxRepeatCount = Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT;
        }
        drawerMaxRepeatCount.setText(Utility.getLocalizedInteger(maxRepeatCount,null));

        menuItem = menu.findItem(R.id.language_control_switch);
        menuItem.setTitle(R.string.language_control);
        actionView = menuItem.getActionView();//MenuItemCompat.getActionView(menuItem);
        drawerSelectedLanguage = (TextView) actionView.findViewById(R.id.language_control);
        int selectedLanguage = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);
        if (selectedLanguage == -1) {
            controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, Constants.LANGUAGE_ENGLISH_VALUE);
            selectedLanguage = Constants.LANGUAGE_ENGLISH_VALUE;
        }
        drawerSelectedLanguage.setText(Utility.getLanguageText(selectedLanguage));
        drawerSelectedLanguage.setOnClickListener(this);
    }

    private void updateLanguage(Context localizedContext){
        invalidateOptionsMenu();
        mAdapter.notifyDataSetChanged();
    }


    void sortList(int type){
        Log.d(getClass().getSimpleName()," Sort List "+ type);

        if (type == Constants.SURAH_VERSE_SORT_BY_NUMBER)
            Collections.sort(surahInfoList,comparatorByNumber);
        else if (type == Constants.SURAH_VERSE_SORT_BY_DURATION)
            Collections.sort(surahInfoList,comparatorByDuration);
        else if (type == Constants.SURAH_VERSE_SORT_BY_VERSE_NUMBER)
            Collections.sort(surahInfoList,comparatorByVerseNumber);
        else
            Collections.sort(surahInfoList,comparatorByNumber);

        if (mAdapter != null) {
            mAdapter.setSurahList(surahInfoList);
            mAdapter.notifyDataSetChanged();
        }
    }

    Comparator<SurahInfo> comparatorByNumber = new Comparator<SurahInfo>() {
        @Override
        public int compare(SurahInfo lhs, SurahInfo rhs) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getSurahNumber() > rhs.getSurahNumber() ? 1 : (lhs.getSurahNumber() < rhs.getSurahNumber() ) ? -1 : 0;
        }
    };

    Comparator<SurahInfo> comparatorByVerseNumber = new Comparator<SurahInfo>() {
        @Override
        public int compare(SurahInfo lhs, SurahInfo rhs) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getVerseCount() > rhs.getVerseCount() ? 1 : (lhs.getVerseCount() < rhs.getVerseCount() ) ? -1 : 0;
        }
    };
    Comparator<SurahInfo> comparatorByDuration = new Comparator<SurahInfo>() {
        @Override
        public int compare(SurahInfo lhs, SurahInfo rhs) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getSurahDuration() > rhs.getSurahDuration() ? 1 : (lhs.getSurahDuration() < rhs.getSurahDuration() ) ? -1 : 0;
        }
    };


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.onAttach(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.language_control:
                mDrawerLayout.closeDrawers();
                showLanguageDialog();
                break;
            default:
                break;
        }
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
                final SharedPreferenceController controller = new SharedPreferenceController(this);
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
            case R.id.language_control_switch:
            {
                showLanguageDialog();
                break;
            }
        }
        return true;
    }

    private void showLanguageDialog() {
        LanguageDialog languageDialog = new LanguageDialog();
        AlertDialog languageAlertDialog = languageDialog.createLanguageDialog(this, null, new LanguageDialogInterface() {
            @Override
            public void selectedLanguageIndex(int index) {
                controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, index);
                drawerSelectedLanguage.setText(Utility.getLanguageText(index));
                //Context context = LocaleManager.setLocale(DashboardActivity.this, index==0?"en":"bn");
                //Toast.makeText(getApplicationContext(), "selectedLanguageIndex "+ index +" is selected!", Toast.LENGTH_SHORT).show();
                //updateLanguage(context);
                //recreate();

            }
        });
        if (languageAlertDialog != null){
            languageAlertDialog.show();
        }
    }

    private void showMaxLoopCountPopup() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builderSingle.setTitle(getString(R.string.max_repeat_count));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item);
        for (int i = Constants.SURAH_VERSE_MIN_REPEAT_COUNT_NUMBER; i<= Constants.SURAH_VERSE_MAX_REPEAT_COUNT_NUMBER; i++) {
            arrayAdapter.add(Utility.getLocalizedInteger(i,null)); // TODO: Need to get from single source
        }
        builderSingle.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final SharedPreferenceController controller = new SharedPreferenceController(this);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int maxRepeatCount = Integer.parseInt(arrayAdapter.getItem(which));
                controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT,maxRepeatCount);
                drawerMaxRepeatCount.setText(Utility.getLocalizedInteger(maxRepeatCount,null));
            }
        });
        builderSingle.create().show();
    }

    private ArrayList<SurahInfo> getSurahInfoList (){
        ArrayList<SurahInfo> surahList = new ArrayList<>();
        surahList.add(new SurahInfo(getString(R.string.surah_al_fatihah), 1, false,51015,7));
        surahList.add(new SurahInfo(getString(R.string.surah_al_balad), 90, false,120015,20));
        surahList.add(new SurahInfo(getString(R.string.surah_ad_duha), 93, false,65000,11));
        surahList.add(new SurahInfo(getString(R.string.surah_an_nas), 114, false,50015,6));
        return surahList;
    }


    /*
     * Create and set menu items for action bar
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        android.view.MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_activity_action_bar_items, menu);
        MenuItem repeatItem = menu.findItem(R.id.actionSort);
        int sortType = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL);
        //setRepeatIcon(isRepeatOn,repeatItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSort:
                showSortSelectDialog();
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortSelectDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builderSingle.setTitle(getString(R.string.sort));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item);
        arrayAdapter.addAll(getResources().getStringArray(R.array.sort_array)); // TODO: Need to get from single source

        builderSingle.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final SharedPreferenceController controller = new SharedPreferenceController(this);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sortList(which);
                controller.writeIntWithKey(Constants.SURAH_SORT_CONTROL,which);
            }
        });
        builderSingle.create().show();
    }
}
