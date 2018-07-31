package com.shahriar.surahshikkha.UI;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shahriar.surahshikkha.Adapter.SurahListAdapter;
import com.shahriar.surahshikkha.CustomComponents.CustomTypeface;
import com.shahriar.surahshikkha.Data.SurahInfo;
import com.shahriar.surahshikkha.Dialog.ExitDialog;
import com.shahriar.surahshikkha.Dialog.HelpDialog;
import com.shahriar.surahshikkha.Dialog.LanguageDialog;
import com.shahriar.surahshikkha.Dialog.ListItemDialog;
import com.shahriar.surahshikkha.Dialog.RepeatCountDialog;
import com.shahriar.surahshikkha.Interfaces.AlertDialogCommandInterface;
import com.shahriar.surahshikkha.Interfaces.DialogItemTouchListener;
import com.shahriar.surahshikkha.Interfaces.OnRecycleViewClicked;
import com.shahriar.surahshikkha.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.surahshikkha.Listeners.RecyclerItemTouchListener;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.Constants;
import com.shahriar.surahshikkha.Utility.LocaleManager;
import com.shahriar.surahshikkha.Utility.SharedPreferenceController;
import com.shahriar.surahshikkha.Utility.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private DrawerLayout mDrawerLayout;

    // list to show surah
    private RecyclerView.LayoutManager mLayoutManager;
    private SurahListAdapter mAdapter;
    private RecyclerView surahListView;

    private SwitchCompat menuBdTranslationSwitch;
    private SwitchCompat menuEnTranslationSwitch;
    private TextView drawerMaxRepeatCount;
    private TextView drawerSelectedLanguage;

    private TextView sortTypeTextHeaderView;
    private TextView sortTypeTextView;
    SearchView searchView;
    MenuItem searchMenuItem;

    Typeface typeface;
    Context localizedContext;

    private ArrayList<SurahInfo> surahInfoList;
    SharedPreferenceController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        localizedContext = this;
        Log.d("TimeTEst","onCreate");
        controller = new SharedPreferenceController(this);
        typeface = ResourcesCompat.getFont(this, R.font.solaimanlipi);
        //int index = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);
       // Context context = LocaleManager.setLocale(DashboardActivity.this, index==0?"en":"bn");
        initComponent(localizedContext);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(getClass().getSimpleName(), "OnCreate "+query);
        }
        else
        {
            closeSearchBar();
        }
    }

    private void closeSearchBar()
    {
        // close search view if its visible
        if (searchView != null && searchView.isShown()) {
            searchMenuItem.collapseActionView();
            searchView.setQuery("", false);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initComponent(this);
    }

    void updateTitleBar(Context context)
    {
        SpannableString s = new SpannableString(context.getString(R.string.app_name));
        s.setSpan(new CustomTypeface("",typeface), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(s);
    }

    void initComponent(Context context){
        Log.d("TimeTEst","initComponent");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Log.d("TimeTEst","setSupportActionBar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        updateTitleBar(context);

        sortTypeTextHeaderView = (TextView) findViewById(R.id.SortTextHeader);
        sortTypeTextView = (TextView)findViewById(R.id.SortText);
        Log.d("TimeTEst","setHomeAsUpIndicator");
        surahListView = (RecyclerView) findViewById(R.id.surahList);
        surahListView.setHasFixedSize(true);
        mLayoutManager = new ScrollingLinearLayoutManager(this,1);
        surahListView.setLayoutManager(mLayoutManager);
        surahInfoList = getSurahInfoList(context);
        int type = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL,Constants.SURAH_VERSE_SORT_BY_NUMBER);
        Log.d("TimeTEst","sortList");
        sortList(type);
        mAdapter = new SurahListAdapter(surahInfoList,context);
        Log.d("TimeTEst","SurahListAdapter");
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
//        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_item_decoration));
//        surahListView.addItemDecoration(dividerItemDecoration);
        Log.d("TimeTEst","addItemDecoration");

        surahListView.setAdapter(mAdapter);
        Log.d("TimeTEst","setAdapter");
        surahListView.addOnItemTouchListener(new RecyclerItemTouchListener(this, surahListView, new OnRecycleViewClicked(){
            @Override
            public void onClick(View view, int position) {
                SurahInfo info = mAdapter.getFilteredData().get(position);
                //Toast.makeText(getApplicationContext(), info.getSurahName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent surahIntent = new Intent(DashboardActivity.this, SurahActivity.class);
                surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO,info.getSurahNumber());
                startActivity(surahIntent);
                closeSearchBar();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void setSortText(int type, Context context) {
        String header = context.getString(R.string.sort_header);
        sortTypeTextHeaderView.setText(header);
        String sortText = "";
        if (type == Constants.SURAH_VERSE_SORT_BY_NUMBER)
            sortText = context.getString(R.string.surah_number);
        else if (type == Constants.SURAH_VERSE_SORT_BY_DURATION)
            sortText = context.getString(R.string.duration);
        else if (type == Constants.SURAH_VERSE_SORT_BY_VERSE_NUMBER)
            sortText = context.getString(R.string.verses);
        else
            sortText = context.getString(R.string.duration);

        sortText = sortText.substring(0, sortText.length() - 1); // Remove the last ':' from text
        sortTypeTextView.setText(sortText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TimeTEst","onResume");

    }

    private void initializeMenuItem(){
        Log.d("DashboardActivity", "initializeMenuItem()");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d("TimeTEst","initializeMenuItem setNavigationItemSelectedListener");
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
        Log.d("TimeTEst","initComponent Menu");
        Menu menu = navigationView.getMenu();
        MenuItem menuItem;

        // Set english translation
        View actionView;
        menuItem = menu.findItem(R.id.english_verse_translation);
        menuItem.setTitle(localizedContext.getString(R.string.menu_english_translation));
        actionView = menuItem.getActionView();//MenuItemCompat.getActionView(menuItem);
        menuEnTranslationSwitch = (SwitchCompat) actionView.findViewById(R.id.switcher);

        boolean isEnglishTranslationOn = controller.readBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION);
        menuEnTranslationSwitch.setChecked(isEnglishTranslationOn);
        menuEnTranslationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.writeBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION,menuEnTranslationSwitch.isChecked());
            }
        });

        // Set bangla translation
        menuItem = menu.findItem(R.id.bangla_verse_translation);
        actionView = menuItem.getActionView();//MenuItemCompat.getActionView(menuItem);
        menuBdTranslationSwitch = (SwitchCompat) actionView.findViewById(R.id.switcher);
        menuItem.setTitle(localizedContext.getString(R.string.menu_bangla_translation));

        boolean isBanglaTranslation = controller.readBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION);
        menuBdTranslationSwitch.setChecked(isBanglaTranslation);
        menuBdTranslationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.writeBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION,menuBdTranslationSwitch.isChecked());
            }
        });

        // Set Max repeat count
        Locale locale = Utility.getCurrentLocale(this);
        Log.d("TimeTEst","initComponent drawerMaxRepeatCount");
        menuItem = menu.findItem(R.id.max_loop_count_control);
        menuItem.setTitle(localizedContext.getString(R.string.max_repeat_count));
        actionView = menuItem.getActionView();//MenuItemCompat.getActionView(menuItem);
        drawerMaxRepeatCount = (TextView) actionView.findViewById(R.id.menu_max_repeat_count);
        drawerMaxRepeatCount.setTypeface(typeface);
        int maxRepeatCount = controller.readIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT);
        if (maxRepeatCount == -1) {
            controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT, Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT);
            maxRepeatCount = Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT;
        }
        drawerMaxRepeatCount.setText(Utility.getLocalizedInteger(maxRepeatCount,locale));
        drawerMaxRepeatCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                showMaxLoopCountPopup();
            }
        });


        // Set the language of application
       menuItem = menu.findItem(R.id.languageControl);
        menuItem.setTitle(localizedContext.getString(R.string.language_control));
        actionView = menuItem.getActionView();//MenuItemCompat.getActionView(menuItem);
        drawerSelectedLanguage = (TextView) actionView.findViewById(R.id.language_control);
        drawerSelectedLanguage.setTypeface(typeface);
        int selectedLanguage = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);
        if (selectedLanguage == -1) {
            if ( "bn".equals(locale.getLanguage())) {
                controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, Constants.LANGUAGE_BANGLA_VALUE);
                selectedLanguage = Constants.LANGUAGE_BANGLA_VALUE;
            }
            else {
                controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, Constants.LANGUAGE_ENGLISH_VALUE);
                selectedLanguage = Constants.LANGUAGE_ENGLISH_VALUE;
            }
        }
        drawerSelectedLanguage.setText(Utility.getLanguageText(selectedLanguage));
        drawerSelectedLanguage.setOnClickListener(this);

        // set rate us
        menuItem = menu.findItem(R.id.rateUs);
        menuItem.setTitle(localizedContext.getString(R.string.rate_us));
        Log.d("TimeTEst","rate app");

        // set guide
        menuItem = menu.findItem(R.id.userGuide);
        menuItem.setTitle(localizedContext.getString(R.string.user_guide));

        // set share
        menuItem = menu.findItem(R.id.menu_item_share);
        menuItem.setTitle(localizedContext.getString(R.string.menu_share));

        // set menuItemControl
        menuItem = menu.findItem(R.id.menu_item_control);
        menuItem.setTitle(localizedContext.getString(R.string.menu_control));

        // set communicate
        menuItem = menu.findItem(R.id.menu_item_communicate);
        menuItem.setTitle(localizedContext.getString(R.string.menu_communicate));

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        ImageView nevHeader = (ImageView)findViewById(R.id.drawer_header_image);
        Drawable imageDrawable = localizedContext.getResources().getDrawable(R.drawable.splash_surah_shiksha);
        if (nevHeader != null && imageDrawable != null) {
            Log.i("DashboardActivity", "Image and header is not null");
            nevHeader.setBackground(imageDrawable);
        }
        else
        {
            Log.i("DashboardActivity", "Image and header is null");
        }

    }

    private void applyFontToMenuItem(MenuItem mi) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeface("" , typeface), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mNewTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)), 0, mNewTitle.length(), 0);
        mi.setTitle(mNewTitle);
    }

    private void updateLanguage(Context localizedContext){
        this.localizedContext = localizedContext;
        typeface = ResourcesCompat.getFont(localizedContext, R.font.solaimanlipi);
        updateListLanguage(localizedContext);
        invalidateOptionsMenu();
        updateTitleBar(localizedContext);
    }

    private void updateListLanguage(Context localiContext){
        surahInfoList = getSurahInfoList(localiContext);
        int selectedOrder = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL,1);
        sortList(selectedOrder);
        mAdapter.setContext(localiContext);
        mAdapter.setSurahList(surahInfoList);
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
            Collections.sort(surahInfoList,comparatorByDuration);

        if (mAdapter != null) {
            mAdapter.setSurahList(surahInfoList);
            mAdapter.notifyDataSetChanged();
            scrollListToPosition(0);
        }
        setSortText(type,localizedContext);
    }

    /*
     * Scroll List to position
     */
    private  void scrollListToPosition(int index){
        mLayoutManager.scrollToPosition(index);
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
                showLanguageDialog(localizedContext);
                break;
            default:
                break;
        }
    }

    private void closeDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Handle navigation view item clicks here.
        switch(id){
            case R.id.max_loop_count_control:
            {
                closeDrawer();
                showMaxLoopCountPopup();
                break;
            }
//            case R.id.loop_control_switch:
//            {
//                final SharedPreferenceController controller = new SharedPreferenceController(this);
//                if(menuRepeatSwitch.isChecked()){
//                    menuRepeatSwitch.setChecked(false);
//                    controller.writeBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL,false);
//                }
//                else{
//                    menuRepeatSwitch.setChecked(true);
//                    controller.writeBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL,true);
//                }
//                break;
//            }
            case R.id.rateUs:
            {
                closeDrawer();
                Utility utility = new Utility();
                utility.rateUs(DashboardActivity.this);
                break;
            }
            case R.id.english_verse_translation:
            {
                final SharedPreferenceController controller = new SharedPreferenceController(this);
                if(menuEnTranslationSwitch.isChecked()){
                    menuEnTranslationSwitch.setChecked(false);
                    controller.writeBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION,false);
                }
                else{
                    menuEnTranslationSwitch.setChecked(true);
                    controller.writeBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION,true);
                }
                break;
            }
            case R.id.bangla_verse_translation:
            {
                final SharedPreferenceController controller = new SharedPreferenceController(this);
                if(menuBdTranslationSwitch.isChecked()){
                    menuBdTranslationSwitch.setChecked(false);
                    controller.writeBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION,false);
                }
                else{
                    menuBdTranslationSwitch.setChecked(true);
                    controller.writeBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION,true);
                }
                break;
            }

            case R.id.userGuide:
            {
                closeDrawer();
                showUserGuide();
                break;
            }
            case R.id.menu_item_share:
            {
                closeDrawer();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + "\n" + getString(R.string.share_text) + "\nhttps://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)));
                break;
            }
            case R.id.languageControl:
            {
                closeDrawer();
                showLanguageDialog(localizedContext);
                break;
            }
        }
        return true;
    }

    private void showUserGuide ()
    {
        String contents[] = {localizedContext.getString(R.string.instruction1),localizedContext.getString(R.string.instruction2),localizedContext.getString(R.string.instruction3)};
        HelpDialog dialog = new HelpDialog(this, localizedContext.getString(R.string.help_outlined_title),localizedContext.getString(R.string.ok),contents);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }


    private void showLanguageDialog(Context context) {
//        final ArrayList<String> itemList = new ArrayList<String>(Arrays.asList(this.getResources().getStringArray(R.array.s114)));
        final ArrayList<String> itemList = new ArrayList<>();
        for (int i = 0; i < Constants.LANGUAGE_LIST.length; i++) {
            itemList.add(Constants.LANGUAGE_LIST[i]); // TODO: Need to get from single source
        }
        int selectedLanguage = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);
        LanguageDialog dialog = new LanguageDialog(this,localizedContext.getString(R.string.language_control),localizedContext.getString(R.string.cancel),itemList, selectedLanguage, new DialogItemTouchListener() {
            @Override
            public void onDialogItemSelected(int position) {
                controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, position);
                drawerSelectedLanguage.setText(Utility.getLanguageText(position));
                Context localizedContext = LocaleManager.setNewLocale(DashboardActivity.this,Utility.getLanguage(position),Utility.getCountry(position));
                Locale locale = Utility.getCurrentLocale(localizedContext);
                Log.d(getClass().getSimpleName(), "Locale " + locale.getLanguage() + " country " + locale.getCountry());
                updateLanguage(localizedContext);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void showMaxLoopCountPopup() {
        final Locale locale = Utility.getCurrentLocale(this);
        final ArrayList<String> itemList = new ArrayList<>();
        for (int i = Constants.SURAH_VERSE_MIN_REPEAT_COUNT_NUMBER; i<= Constants.SURAH_VERSE_MAX_REPEAT_COUNT_NUMBER; i++) {
            itemList.add(Utility.getLocalizedInteger(i,locale)); // TODO: Need to get from single source
        }
        int loopCountValue = controller.readIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT,Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT);
        RepeatCountDialog dialog = new RepeatCountDialog(this,localizedContext.getString(R.string.max_repeat_count),localizedContext.getString(R.string.cancel),itemList, loopCountValue, new DialogItemTouchListener() {
            @Override
            public void onDialogItemSelected(int position) {
                int maxRepeatCount = Integer.parseInt(itemList.get(position));
                controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT,maxRepeatCount);
                drawerMaxRepeatCount.setText(Utility.getLocalizedInteger(maxRepeatCount,locale));
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.scrollToPosition();
    }

    private ArrayList<SurahInfo> getSurahInfoList (Context context){
        ArrayList<SurahInfo> surahList = new ArrayList<>();
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_fatihah),context.getString(R.string.bn_surah_al_fatihah), 1, false,45871,7));
        surahList.add(new SurahInfo(context.getString(R.string.surah_at_tariq),context.getString(R.string.bn_surah_at_tariq), 86, false,100656,17));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_ala),context.getString(R.string.bn_surah_al_ala), 87, false,108216,19));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_ghashiyah),context.getString(R.string.bn_surah_al_ghashiyah), 88, false,134568,26));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_fajr),context.getString(R.string.bn_surah_al_fajr), 89, false,213408,30));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_balad),context.getString(R.string.bn_surah_al_balad), 90, false,120456,20));
        surahList.add(new SurahInfo(context.getString(R.string.surah_ash_shams),context.getString(R.string.bn_surah_ash_shams), 91, false,84816,15));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_layl),context.getString(R.string.bn_surah_al_layl), 92, false,112032,21));
        surahList.add(new SurahInfo(context.getString(R.string.surah_ad_duha),context.getString(R.string.bn_surah_ad_duha), 93, false,65304,11));
        surahList.add(new SurahInfo(context.getString(R.string.surah_as_sharh),context.getString(R.string.bn_surah_as_sharh), 94, false,43200,8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_at_tin),context.getString(R.string.bn_surah_at_tin), 95, false,65160,8));
//        surahList.add(new SurahInfo(getString(R.string.surah_al_alaq), 96, false,95328,19));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_qadr),context.getString(R.string.bn_surah_al_qadr), 97, false,45360,5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_bayyinah),context.getString(R.string.bn_surah_al_bayyinah), 98, true,126648,8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_az_zilzalah),context.getString(R.string.bn_surah_az_zilzalah), 99, true,60192,8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_adiyat),context.getString(R.string.bn_surah_al_adiyat), 100, false,70272,11));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_qariah),context.getString(R.string.bn_surah_al_qariah), 101, false,62784,11));
        surahList.add(new SurahInfo(context.getString(R.string.surah_at_takathur),context.getString(R.string.bn_surah_at_takathur), 102, false,62856,8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_asr),context.getString(R.string.bn_surah_al_asr), 103, false,27648,3));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_humazah),context.getString(R.string.bn_surah_al_humazah), 104, false,58248,9));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_fil),context.getString(R.string.bn_surah_al_fil), 105, false,49176,5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_quraysh),context.getString(R.string.bn_surah_quraysh), 106, false,42768,4));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_maun),context.getString(R.string.bn_surah_al_maun), 107, false,57744,7));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_kawthar),context.getString(R.string.bn_surah_al_kawthar), 108, false,24768,3));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_kafirun),context.getString(R.string.bn_surah_al_kafirun), 109, false,54504,6));
        surahList.add(new SurahInfo(context.getString(R.string.surah_an_nasr),context.getString(R.string.bn_surah_an_nasr), 110, true,35136,3));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_masad),context.getString(R.string.bn_surah_al_masad), 111, false,41760,5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_ikhlas),context.getString(R.string.bn_surah_al_ikhlas), 112, false,22104,4));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_falaq),context.getString(R.string.bn_surah_al_falaq), 113, false,33264,5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_an_nas),context.getString(R.string.bn_surah_an_nas), 114, false,50256,6));
        return surahList;
    }

    /*
     * Create and set menu items for action bar
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        android.view.MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_activity_action_bar_items, menu);
        initializeMenuItem();

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(localizedContext.getResources().getString(R.string.search_hint));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSort:
                closeSearchBar();
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
        int selectedOrder = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL,1);
        ListItemDialog dialog = new ListItemDialog(this,localizedContext.getString(R.string.sort),localizedContext.getString(R.string.cancel),new ArrayList<String>(Arrays.asList(localizedContext.getResources().getStringArray(R.array.sort_array))),selectedOrder, new DialogItemTouchListener() {
            @Override
            public void onDialogItemSelected(int position) {
                sortList(position);
                controller.writeIntWithKey(Constants.SURAH_SORT_CONTROL,position);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
            return;
        }
        ExitDialog dialog = new ExitDialog(this, localizedContext.getString(R.string.exit),localizedContext.getString(R.string.cancel),localizedContext.getString(R.string.exit_text),exitDialogInterface);
        dialog.show();
    }

    AlertDialogCommandInterface exitDialogInterface = new AlertDialogCommandInterface() {
        @Override
        public void onPositiveButtonClicked() {
            finish();
        }

        @Override
        public void onNegativeButtonClicked() {

        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (!"".equals(query))
        {
            mAdapter.getFilter().filter(query);
        }
        // close search view if its visible
        if (searchView.isShown()) {
            searchMenuItem.collapseActionView();
            searchView.setQuery("", false);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }
}
