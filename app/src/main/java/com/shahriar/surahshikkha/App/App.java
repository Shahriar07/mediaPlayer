package com.shahriar.surahshikkha.App;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.shahriar.surahshikkha.Utility.LocaleManager;

import java.util.Locale;

/**
 * Created by H. M. Shahriar on 5/31/2018.
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}
