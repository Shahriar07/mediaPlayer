package com.shahriar.surahshikkha.UI;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.shahriar.surahshikkha.Interfaces.AudioControllerListener;
import com.shahriar.surahshikkha.Interfaces.DashboardListItemListener;
import com.shahriar.surahshikkha.Interfaces.DashboardListItemUpdateListener;
import com.shahriar.surahshikkha.Interfaces.DialogItemTouchListener;
import com.shahriar.surahshikkha.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.Constants;
import com.shahriar.surahshikkha.Utility.LocaleManager;
import com.shahriar.surahshikkha.Utility.SharedPreferenceController;
import com.shahriar.surahshikkha.Utility.Utility;
import com.shahriar.surahshikkha.manager.MediaManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, DashboardListItemListener, AudioControllerListener, DashboardListItemUpdateListener {

    static SurahInfo surahInfo;
    static int position;
    static boolean isContinuousPlay = true;
    SearchView searchView;
    MenuItem searchMenuItem;
    Typeface typeface;
    Context localizedContext;
    SharedPreferenceController controller;
    MediaManager mediaManager;
    // Playing surah information
    int surahNumber;
    Handler mediaHandler;
    Comparator<SurahInfo> comparatorByNumber = new Comparator<SurahInfo>() {
        @Override
        public int compare(SurahInfo lhs, SurahInfo rhs) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getSurahNumber() > rhs.getSurahNumber() ? 1 : (lhs.getSurahNumber() < rhs.getSurahNumber()) ? -1 : 0;
        }
    };
    Comparator<SurahInfo> comparatorByVerseNumber = new Comparator<SurahInfo>() {
        @Override
        public int compare(SurahInfo lhs, SurahInfo rhs) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getVerseCount() > rhs.getVerseCount() ? 1 : (lhs.getVerseCount() < rhs.getVerseCount()) ? -1 : 0;
        }
    };
    Comparator<SurahInfo> comparatorByDuration = new Comparator<SurahInfo>() {
        @Override
        public int compare(SurahInfo lhs, SurahInfo rhs) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getSurahDuration() > rhs.getSurahDuration() ? 1 : (lhs.getSurahDuration() < rhs.getSurahDuration()) ? -1 : 0;
        }
    };
    AlertDialogCommandInterface exitDialogInterface = new AlertDialogCommandInterface() {
        @Override
        public void onPositiveButtonClicked() {
            finish();
        }

        @Override
        public void onNegativeButtonClicked() {

        }
    };
    private DrawerLayout mDrawerLayout;
    // list to show surah
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * Runnable to update progressbar
     */
    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (mediaManager != null && mediaManager.isMediaPlaying()) {
                updateProgressBar(surahInfo, position);
            }
        }
    };
    private SurahListAdapter mAdapter;

    private SwitchCompat menuBdTranslationSwitch;
    private SwitchCompat menuEnTranslationSwitch;
    private TextView drawerMaxRepeatCount;
    private TextView drawerSelectedLanguage;
    //private TextView sortTypeTextHeaderView;
    private TextView sortTypeTextView;
    private ArrayList<SurahInfo> surahInfoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.drawer_layout);
        localizedContext = this;
        Log.d(getClass().getSimpleName(), "onCreate");
        controller = new SharedPreferenceController(this);
        typeface = ResourcesCompat.getFont(this, R.font.solaimanlipi);
        //int index = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);
        // Context context = LocaleManager.setLocale(DashboardActivity.this, index==0?"en":"bn");
        initComponent(localizedContext);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(getClass().getSimpleName(), "OnCreate " + query);
        } else {
            closeSearchBar();
        }

        // initialize in oncreate as values are not dependent on language/context
        surahNumber = 0;
        DashboardActivity.position = 0;
    }

    private void closeSearchBar() {
        Log.d(getClass().getSimpleName(), "closeSearchBar");
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

    void updateTitleBar(Context context) {
        Log.d(getClass().getSimpleName(), "updateTitleBar");
        SpannableString s = new SpannableString(context.getString(R.string.app_name));
        s.setSpan(new CustomTypeface("", typeface), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(s);
    }

    void initComponent(Context context) {
        Log.d(getClass().getSimpleName(), "initComponent");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            Log.d(getClass().getSimpleName(), "setSupportActionBar");
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        updateTitleBar(context);

        mediaHandler = new Handler();

        Log.d(getClass().getSimpleName(), "setHomeAsUpIndicator");
        RecyclerView surahListView = (RecyclerView) findViewById(R.id.surahList);
        surahListView.setHasFixedSize(true);
        mLayoutManager = new ScrollingLinearLayoutManager(this, 1);
        surahListView.setLayoutManager(mLayoutManager);
        surahInfoList = getSurahInfoList(context);
        int type = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL, Constants.SURAH_VERSE_SORT_BY_NUMBER);
        Log.d(getClass().getSimpleName(), "sortList");
        sortList(type);
        mAdapter = new SurahListAdapter(surahInfoList, context, this);
        Log.d(getClass().getSimpleName(), "SurahListAdapter");

        surahListView.setAdapter(mAdapter);
        Log.d(getClass().getSimpleName(), "setAdapter");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getClass().getSimpleName(), "onResume");
        scrollListToPosition(DashboardActivity.position);
    }

    private void initializeMenuItem() {
        Log.d(getClass().getSimpleName(), "initializeMenuItem()");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d(getClass().getSimpleName(), "initializeMenuItem setNavigationItemSelectedListener");
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
        Log.d(getClass().getSimpleName(), "initComponent Menu");
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
                controller.writeBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION, menuEnTranslationSwitch.isChecked());
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
                controller.writeBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION, menuBdTranslationSwitch.isChecked());
            }
        });

        // Set Max repeat count
        Locale locale = Utility.getCurrentLocale(this);
        Log.d(getClass().getSimpleName(), "initComponent drawerMaxRepeatCount");
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
        drawerMaxRepeatCount.setText(Utility.getLocalizedInteger(maxRepeatCount, locale));
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
            if ("bn".equals(locale.getLanguage())) {
                controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, Constants.LANGUAGE_BANGLA_VALUE);
                selectedLanguage = Constants.LANGUAGE_BANGLA_VALUE;
            } else {
                controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, Constants.LANGUAGE_ENGLISH_VALUE);
                selectedLanguage = Constants.LANGUAGE_ENGLISH_VALUE;
            }
        }
        drawerSelectedLanguage.setText(Utility.getLanguageText(selectedLanguage));
        drawerSelectedLanguage.setOnClickListener(this);

        // set rate us
        menuItem = menu.findItem(R.id.rateUs);
        menuItem.setTitle(localizedContext.getString(R.string.rate_us));
        Log.d(getClass().getSimpleName(), "rate app");

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
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        ImageView nevHeader = (ImageView) findViewById(R.id.drawer_header_image);
        Drawable imageDrawable = localizedContext.getResources().getDrawable(R.drawable.splash_surah_shiksha);
        if (nevHeader != null && imageDrawable != null) {
            Log.i("DashboardActivity", "Image and header is not null");
            nevHeader.setBackground(imageDrawable);
        } else {
            Log.i("DashboardActivity", "Image and header is null");
        }

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Log.d(getClass().getSimpleName(), "applyFontToMenuItem");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeface("", typeface), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mNewTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)), 0, mNewTitle.length(), 0);
        mi.setTitle(mNewTitle);
    }

    private void updateLanguage(Context localizedContext) {
        Log.d(getClass().getSimpleName(), "updateLanguage");
        this.localizedContext = localizedContext;
        typeface = ResourcesCompat.getFont(localizedContext, R.font.solaimanlipi);
        updateListLanguage(localizedContext);
        invalidateOptionsMenu();
        updateTitleBar(localizedContext);
    }

    private void updateListLanguage(Context localiContext) {
        Log.d(getClass().getSimpleName(), "updateListLanguage");
        stopCurrentMedia(true);
        surahInfoList = getSurahInfoList(localiContext);
        int selectedOrder = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL, Constants.SURAH_VERSE_SORT_BY_NUMBER);
        sortList(selectedOrder);
        mAdapter.setContext(localiContext);
        mAdapter.setSurahList(surahInfoList);
        mAdapter.notifyDataSetChanged();
    }

    void sortList(int type) {
        Log.d(getClass().getSimpleName(), " Sort List " + type);
        stopCurrentMedia(true);
        if (type == Constants.SURAH_VERSE_SORT_BY_NUMBER)
            Collections.sort(surahInfoList, comparatorByNumber);
        else if (type == Constants.SURAH_VERSE_SORT_BY_DURATION)
            Collections.sort(surahInfoList, comparatorByDuration);
        else if (type == Constants.SURAH_VERSE_SORT_BY_VERSE_NUMBER)
            Collections.sort(surahInfoList, comparatorByVerseNumber);
        else
            Collections.sort(surahInfoList, comparatorByDuration);

        if (mAdapter != null) {
            mAdapter.setSurahList(surahInfoList);
            mAdapter.notifyDataSetChanged();
            scrollListToPosition(0);
        }
        //setSortText(type, localizedContext);
    }

    /*
     * Scroll List to position
     */
    private void scrollListToPosition(int index) {
        Log.d(getClass().getSimpleName(), "scrollListToPosition " + index);
        if (mLayoutManager != null)
        mLayoutManager.scrollToPosition(index);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.onAttach(newBase));
    }

    @Override
    public void onClick(View v) {
        Log.d(getClass().getSimpleName(), "onClick");
        switch (v.getId()) {
            case R.id.language_control:
                mDrawerLayout.closeDrawers();
                showLanguageDialog(localizedContext);
                break;
            default:
                break;
        }
    }

    private void closeDrawer() {
        Log.d(getClass().getSimpleName(), "closeDrawer");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(getClass().getSimpleName(), "onNavigationItemSelected");
        int id = item.getItemId();
        // Handle navigation view item clicks here.
        switch (id) {
            case R.id.max_loop_count_control: {
                closeDrawer();
                showMaxLoopCountPopup();
                break;
            }
            case R.id.rateUs: {
                closeDrawer();
                Utility utility = new Utility();
                utility.rateUs(DashboardActivity.this);
                break;
            }
            case R.id.english_verse_translation: {
                final SharedPreferenceController controller = new SharedPreferenceController(this);
                if (menuEnTranslationSwitch.isChecked()) {
                    menuEnTranslationSwitch.setChecked(false);
                    controller.writeBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION, false);
                } else {
                    menuEnTranslationSwitch.setChecked(true);
                    controller.writeBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION, true);
                }
                break;
            }
            case R.id.bangla_verse_translation: {
                final SharedPreferenceController controller = new SharedPreferenceController(this);
                if (menuBdTranslationSwitch.isChecked()) {
                    menuBdTranslationSwitch.setChecked(false);
                    controller.writeBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION, false);
                } else {
                    menuBdTranslationSwitch.setChecked(true);
                    controller.writeBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION, true);
                }
                break;
            }

            case R.id.userGuide: {
                closeDrawer();
                showUserGuide();
                break;
            }
            case R.id.menu_item_share: {
                closeDrawer();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + "\n" + getString(R.string.share_text) + "\nhttps://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)));
                break;
            }
            case R.id.languageControl: {
                closeDrawer();
                showLanguageDialog(localizedContext);
                break;
            }
        }
        return true;
    }

    private void showUserGuide() {
        Log.d(getClass().getSimpleName(), "showUserGuide");
        String contents[] = {localizedContext.getString(R.string.instruction1), localizedContext.getString(R.string.instruction2), localizedContext.getString(R.string.instruction3)};
        HelpDialog dialog = new HelpDialog(this, localizedContext.getString(R.string.help_outlined_title), localizedContext.getString(R.string.ok), contents);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void showLanguageDialog(Context context) {
        Log.d(getClass().getSimpleName(), "showLanguageDialog");
//        final ArrayList<String> itemList = new ArrayList<String>(Arrays.asList(this.getResources().getStringArray(R.array.s114)));
        final ArrayList<String> itemList = new ArrayList<>();
        for (int i = 0; i < Constants.LANGUAGE_LIST.length; i++) {
            itemList.add(Constants.LANGUAGE_LIST[i]); // TODO: Need to get from single source
        }
        int selectedLanguage = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);
        LanguageDialog dialog = new LanguageDialog(this, localizedContext.getString(R.string.language_control), localizedContext.getString(R.string.cancel), itemList, selectedLanguage, new DialogItemTouchListener() {
            @Override
            public void onDialogItemSelected(int position) {
                controller.writeIntWithKey(Constants.SELECTED_LANGUAGE, position);
                drawerSelectedLanguage.setText(Utility.getLanguageText(position));
                Context localizedContext = LocaleManager.setNewLocale(DashboardActivity.this, Utility.getLanguage(position), Utility.getCountry(position));
                Locale locale = Utility.getCurrentLocale(localizedContext);
                Log.d(getClass().getSimpleName(), "Locale " + locale.getLanguage() + " country " + locale.getCountry());
                updateLanguage(localizedContext);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void showMaxLoopCountPopup() {
        Log.d(getClass().getSimpleName(), "showMaxLoopCountPopup");
        final Locale locale = Utility.getCurrentLocale(this);
        final ArrayList<String> itemList = new ArrayList<>();
        for (int i = Constants.SURAH_VERSE_MIN_REPEAT_COUNT_NUMBER; i <= Constants.SURAH_VERSE_MAX_REPEAT_COUNT_NUMBER; i++) {
            itemList.add(Utility.getLocalizedInteger(i, locale)); // TODO: Need to get from single source
        }
        int loopCountValue = controller.readIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT, Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT);
        RepeatCountDialog dialog = new RepeatCountDialog(this, localizedContext.getString(R.string.max_repeat_count), localizedContext.getString(R.string.cancel), itemList, loopCountValue, new DialogItemTouchListener() {
            @Override
            public void onDialogItemSelected(int position) {
                int maxRepeatCount = Integer.parseInt(itemList.get(position));
                controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT, maxRepeatCount);
                drawerMaxRepeatCount.setText(Utility.getLocalizedInteger(maxRepeatCount, locale));
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.scrollToPosition();
    }

    private ArrayList<SurahInfo> getSurahInfoList(@NonNull Context context) {
        Log.d(getClass().getSimpleName(), "getSurahInfoList");
        ArrayList<SurahInfo> surahList = new ArrayList<>();
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_fatihah), context.getString(R.string.bn_surah_al_fatihah), 1, false, 46080, 7));
        surahList.add(new SurahInfo(context.getString(R.string.surah_at_tariq), context.getString(R.string.bn_surah_at_tariq), 86, false, 100656, 17));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_ala), context.getString(R.string.bn_surah_al_ala), 87, false, 108216, 19));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_ghashiyah), context.getString(R.string.bn_surah_al_ghashiyah), 88, false, 134568, 26));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_fajr), context.getString(R.string.bn_surah_al_fajr), 89, false, 213408, 30));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_balad), context.getString(R.string.bn_surah_al_balad), 90, false, 120456, 20));
        surahList.add(new SurahInfo(context.getString(R.string.surah_ash_shams), context.getString(R.string.bn_surah_ash_shams), 91, false, 84816, 15));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_layl), context.getString(R.string.bn_surah_al_layl), 92, false, 112032, 21));
        surahList.add(new SurahInfo(context.getString(R.string.surah_ad_duha), context.getString(R.string.bn_surah_ad_duha), 93, false, 65304, 11));
        surahList.add(new SurahInfo(context.getString(R.string.surah_as_sharh), context.getString(R.string.bn_surah_as_sharh), 94, false, 43200, 8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_at_tin), context.getString(R.string.bn_surah_at_tin), 95, false, 65160, 8));
//        surahList.add(new SurahInfo(getString(R.string.surah_al_alaq), 96, false,95328,19));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_qadr), context.getString(R.string.bn_surah_al_qadr), 97, false, 45360, 5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_bayyinah), context.getString(R.string.bn_surah_al_bayyinah), 98, true, 126648, 8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_az_zilzalah), context.getString(R.string.bn_surah_az_zilzalah), 99, true, 60192, 8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_adiyat), context.getString(R.string.bn_surah_al_adiyat), 100, false, 70272, 11));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_qariah), context.getString(R.string.bn_surah_al_qariah), 101, false, 62784, 11));
        surahList.add(new SurahInfo(context.getString(R.string.surah_at_takathur), context.getString(R.string.bn_surah_at_takathur), 102, false, 62856, 8));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_asr), context.getString(R.string.bn_surah_al_asr), 103, false, 27648, 3));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_humazah), context.getString(R.string.bn_surah_al_humazah), 104, false, 58248, 9));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_fil), context.getString(R.string.bn_surah_al_fil), 105, false, 49176, 5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_quraysh), context.getString(R.string.bn_surah_quraysh), 106, false, 42768, 4));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_maun), context.getString(R.string.bn_surah_al_maun), 107, false, 57744, 7));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_kawthar), context.getString(R.string.bn_surah_al_kawthar), 108, false, 24768, 3));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_kafirun), context.getString(R.string.bn_surah_al_kafirun), 109, false, 54504, 6));
        surahList.add(new SurahInfo(context.getString(R.string.surah_an_nasr), context.getString(R.string.bn_surah_an_nasr), 110, true, 35136, 3));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_masad), context.getString(R.string.bn_surah_al_masad), 111, false, 41760, 5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_ikhlas), context.getString(R.string.bn_surah_al_ikhlas), 112, false, 22104, 4));
        surahList.add(new SurahInfo(context.getString(R.string.surah_al_falaq), context.getString(R.string.bn_surah_al_falaq), 113, false, 33264, 5));
        surahList.add(new SurahInfo(context.getString(R.string.surah_an_nas), context.getString(R.string.bn_surah_an_nas), 114, false, 50256, 6));
        return surahList;
    }

    /*
     * Create and set menu items for action bar
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        Log.d(getClass().getSimpleName(), "onCreateOptionsMenu");
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
        Log.d(getClass().getSimpleName(), "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.actionSort:
                stopCurrentMedia(true);
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
        Log.d(getClass().getSimpleName(), "showSortSelectDialog");
        int selectedOrder = controller.readIntWithKey(Constants.SURAH_SORT_CONTROL, Constants.SURAH_VERSE_SORT_BY_NUMBER);
        ListItemDialog dialog = new ListItemDialog(this, localizedContext.getString(R.string.sort), localizedContext.getString(R.string.cancel), new ArrayList<String>(Arrays.asList(localizedContext.getResources().getStringArray(R.array.sort_array))), selectedOrder, new DialogItemTouchListener() {
            @Override
            public void onDialogItemSelected(int position) {
                surahInfoList = getSurahInfoList(localizedContext);
                sortList(position);
                controller.writeIntWithKey(Constants.SURAH_SORT_CONTROL, position);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Log.d(getClass().getSimpleName(), "onBackPressed");
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        ExitDialog dialog = new ExitDialog(this, localizedContext.getString(R.string.exit), localizedContext.getString(R.string.cancel), localizedContext.getString(R.string.exit_text), exitDialogInterface);
        dialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(getClass().getSimpleName(), "onQueryTextSubmit");

        if (!"".equals(query)) {
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
        Log.d(getClass().getSimpleName(), "onQueryTextChange");
        stopCurrentMedia(true);
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void playPauseButtonPressed(SurahInfo surahInfo, int position) {
        Log.d(getClass().getSimpleName(), "playPauseButtonPressed " + position + " isPlaying " + surahInfo.isPlaying() + " surahInfo " + surahInfo.getSurahNumber());
        if (surahInfo == null)
            return;
        if (mediaManager == null) {
            mediaManager = new MediaManager(this, Utility.getRawFileFromSurahNumber(surahInfo.getSurahNumber()), this, surahInfo.getSurahNumber());
            surahNumber = surahInfo.getSurahNumber();
            DashboardActivity.surahInfo = surahInfo;
            DashboardActivity.position = position;
        } else if ((surahInfo.getSurahNumber() != surahNumber)) {
            // Stop the previous audio and update the surahInfo in adapter
            stopCurrentMedia(true);

            // Set new audio
            mediaManager = new MediaManager(this, Utility.getRawFileFromSurahNumber(surahInfo.getSurahNumber()), this, surahInfo.getSurahNumber());
            surahNumber = surahInfo.getSurahNumber();
            DashboardActivity.surahInfo = surahInfo;
            DashboardActivity.position = position;
        }
        if (mediaManager.isMediaPlaying()) {
            Log.i(getClass().getSimpleName(), "Media is playing, stop the media");
            mediaManager.pauseMedia();
        } else {
            Log.i(getClass().getSimpleName(), "Media is not playing, start the media and update progressbar");
            mediaManager.startMedia();
            updateProgressBar(surahInfo, position);
        }
    }

    /**
     * Update the progressbar of playing surah
     *
     * @param surahInfo
     * @param position
     */
    private void updateProgressBar(SurahInfo surahInfo, int position) {
        //Log.d(getClass().getSimpleName(), "updateProgressBar");
        if (mediaManager != null && surahInfo != null) {
            int duration = mediaManager.getMediaDuration();
            int currentPosition = mediaManager.getCurrentPosition();
            int percent = currentPosition * 100 / duration;
            surahInfo.setAudioPercent(percent);
            View view = mLayoutManager.findViewByPosition(position);//surahListView.getChildAt(position - ));// linearLayoutManager is recyclerview's layout manager
            if (view != null) {
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.playProgressBar);
                progressBar.setProgress(percent);
            }
            mediaHandler.postDelayed(run, 1000);
        }
    }

    /**
     * Stop currently playing surah (if any)
     */
    private void stopCurrentMedia(boolean userInitiated) {
        Log.d(getClass().getSimpleName(), "stopCurrentMedia");
        if (mediaManager != null)
            mediaManager.stopMedia(userInitiated);
        if (DashboardActivity.surahInfo != null) {
            DashboardActivity.surahInfo.setAudioPercent(0);
            DashboardActivity.surahInfo.setPlaying(false);
            if (this.mAdapter != null)
                this.mAdapter.refresh(DashboardActivity.position, DashboardActivity.surahInfo, null);
        }
    }

    /**
     * Navigate to surah details activity to repeat single verse
     *
     * @param surahNumber
     */
    @Override
    public void listItemPressed(int surahNumber) {
        Log.d(getClass().getSimpleName(), "listItemPressed");
        stopCurrentMedia(true);
        Intent surahIntent = new Intent(DashboardActivity.this, SurahActivity.class);
        surahIntent.putExtra(Constants.SURAH_ACTIVITY_SURAH_NO, surahNumber);
        startActivity(surahIntent);
        closeSearchBar();
    }

    @Override
    public void audioPaused(int surahNumber) {
        Log.d(getClass().getSimpleName(), "audioPaused");
        if (mediaManager.isMediaPlaying()) {
            mediaManager.pauseMedia();
            DashboardActivity.surahInfo.setPlaying(false);
        }
    }

    /**
     * Update the progressbar according to audio
     *
     * @param surahNumber
     */
    @Override
    public void audioStarted(int surahNumber) {
        Log.d(getClass().getSimpleName(), "audioStarted");
        mediaHandler.postDelayed(run, 1000);
    }

    /**
     * Update the playPause button image when audio play finished
     *
     * @param surahNumber   Surah number to stop
     * @param userInitiated if userInitiated, next surah will not play automatically
     */
    @Override
    public void audioFinished(int surahNumber, boolean userInitiated) {
        Log.d(getClass().getSimpleName(), "audioFinished");
        //surahNumber = 0;
        if (mediaManager != null) {
            mediaManager.release();
            mediaManager = null;
        }
        Log.d(getClass().getSimpleName(), "surah number " + surahNumber + " surah info " + surahInfo.toString());
        DashboardActivity.surahInfo.setAudioPercent(0);
        DashboardActivity.surahInfo.setPlaying(false);
        if (this.mAdapter != null) {
            //if the finished method called automatically, try to play the next surah
            if (isContinuousPlay && !userInitiated) {
                Log.d("UpdateListener ", "UpdateListener, Not user Initiated");
                this.mAdapter.refresh(DashboardActivity.position, DashboardActivity.surahInfo, this);
            } else {
                Log.d("UpdateListener ", "UpdateListener, User Initiated");
                this.mAdapter.refresh(DashboardActivity.position, DashboardActivity.surahInfo, null);
            }
        }

//        // Play the next Surah if available
//        if (isContinuousPlay && !userInitiated)
//        {
//            if (surahInfoList.size()  > DashboardActivity.position + 1) {
//                Log.d(getClass().getSimpleName(), "Start the next surah ");
//                StartSurahAsyncTask startSurahTask = new StartSurahAsyncTask();
//                startSurahTask.execute(this.mAdapter);
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(), "onDestroy");
        if (mediaManager != null)
            mediaManager.release();
        mediaHandler.removeCallbacks(run);
        DashboardActivity.surahInfo = null;
        DashboardActivity.position = 0;
        Log.d(getClass().getSimpleName(), "On destroy called");
    }

    @Override
    public void listItemUpdated(int position) {
        Log.d(getClass().getSimpleName(), "listItemUpdated " + position);
        // Play the next Surah if available
        if (surahInfoList.size() > DashboardActivity.position + 1) {
            Log.d(getClass().getSimpleName(), "Start the next surah ");
            StartSurahAsyncTask startSurahTask = new StartSurahAsyncTask();
            startSurahTask.execute(this.mAdapter);
        }
    }


    class StartSurahAsyncTask extends AsyncTask<SurahListAdapter, Void, SurahListAdapter> {
        @Override
        protected SurahListAdapter doInBackground(SurahListAdapter... listAdapters) {
            DashboardActivity.position++;
            DashboardActivity.surahInfo = surahInfoList.get(DashboardActivity.position);
            DashboardActivity.surahInfo.setPlaying(true);
            return listAdapters[0];
        }

        protected void onPostExecute(SurahListAdapter listAdapters) {
            listAdapters.refresh(DashboardActivity.position, DashboardActivity.surahInfo, null);
            playPauseButtonPressed(DashboardActivity.surahInfo, DashboardActivity.position);
            scrollListToPosition(DashboardActivity.position);
        }
    }
}
