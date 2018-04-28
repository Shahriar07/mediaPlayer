package com.shahriar.surahshikkha.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by H. M. Shahriar on 3/31/2018.
 */
public class SharedPreferenceController {

    Context appContext;

    public SharedPreferenceController(Context context) {
        this.appContext = context;
    }

    private SharedPreferences getSharedPreferences(Context context){
        if (appContext == null) {
            appContext = ApplicationContextManager.getInstance(null).getAppContext();
        }
        return PreferenceManager.getDefaultSharedPreferences(appContext);
    }
    public boolean readBooleanWithKey(String key, boolean defaultValue){
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        if (key!=null)
        return sharedPref.getBoolean(key, defaultValue);
        return defaultValue;
    }

    public boolean readBooleanWithKey(String key){
        return  readBooleanWithKey(key,true);
    }

    public boolean writeBooleanWithKey(String key, boolean value){
        if (key == null) return false;
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public int readIntWithKey(String key){
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        int defaultValue = -1;
        if (key!=null)
            return sharedPref.getInt(key, defaultValue);
        return defaultValue;
    }
    public int readIntWithKey(String key, int defaultValue){
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        if (key!=null)
            return sharedPref.getInt(key, defaultValue);
        return defaultValue;
    }
    public boolean writeIntWithKey(String key, int value){
        if (key == null) return false;
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    public String readStringWithKey(String key){
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        String defaultValue = "";
        if (key!=null)
            return sharedPref.getString(key, defaultValue);
        return defaultValue;
    }

    public boolean writeStringWithKey(String key, String value){
        if (value == null || key == null)
            return false;
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        return editor.commit();
    }

}
