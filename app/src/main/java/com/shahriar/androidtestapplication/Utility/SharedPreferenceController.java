package com.shahriar.androidtestapplication.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by H. M. Shahriar on 3/31/2018.
 */
public class SharedPreferenceController {

    public boolean readBooleanWithKey(String key){
        Context context = ApplicationContextManager.getInstance(null).getAppContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean defaultValue = false;
        if (key!=null)
        return sharedPref.getBoolean(key, defaultValue);
        return defaultValue;
    }

    public boolean writeBooleanWithKey(String key, boolean value){
        if (key == null) return false;
        Context context = ApplicationContextManager.getInstance(null).getAppContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public int readIntWithKey(String key){
        Context context = ApplicationContextManager.getInstance(null).getAppContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int defaultValue = 0;
        if (key!=null)
            return sharedPref.getInt(key, defaultValue);
        return defaultValue;
    }

    public boolean writeIntWithKey(String key, int value){
        if (key == null) return false;
        Context context = ApplicationContextManager.getInstance(null).getAppContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public String readStringWithKey(String key){
        Context context = ApplicationContextManager.getInstance(null).getAppContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultValue = "";
        if (key!=null)
            return sharedPref.getString(key, defaultValue);
        return defaultValue;
    }

    public boolean writeStringWithKey(String key, String value){
        if (value == null || key == null)
            return false;
        Context context = ApplicationContextManager.getInstance(null).getAppContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        return editor.commit();
    }

}
