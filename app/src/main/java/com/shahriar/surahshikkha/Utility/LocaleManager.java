package com.shahriar.surahshikkha.Utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;

/**
 * Created by H. M. Shahriar on 4/15/2018.
 */
public class LocaleManager {

        private static final String SELECTED_LANGUAGE = "locale.manager.new.language";
        private static final String SELECTED_COUNTRY = "locale.manager.new.country";

        public static Context onAttach(Context context) {
            String lang = getPersistedLanguageData(context, Locale.getDefault().getLanguage());
            String countryData = getPersistedCountryData(context, Locale.getDefault().getCountry());
            return setLocale(context, lang, countryData);
        }

        public static Context onAttach(Context context, String defaultLanguage, String defaultCountry) {
            String lang = getPersistedLanguageData(context, defaultLanguage);
            String countryData = getPersistedCountryData(context, defaultCountry);
            return setLocale(context, lang, defaultCountry);
        }

        public static String getLanguage(Context context) {
            Locale locale = Utility.getCurrentLocale(context);
            Log.d("LocaleManager"," getLanguage "+locale.getLanguage());
            return locale.getLanguage();
        }

        public static String getCountry(Context context) {
            Locale locale = Utility.getCurrentLocale(context);
            Log.d("LocaleManager"," getCountry "+locale.getCountry());
            return locale.getCountry();
        }
    public static Context setLocale(Context context) {
        return setNewLocale(context, getLanguage(context), getCountry(context));
    }

    public static Context setNewLocale(Context context, String language, String country){
           // persist(context,language);
            return setLocale(context,language, country);
    }

        public static Context setLocale(Context context, String language, String country) {
            persist(context, language, country);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return updateResources(context, language, country);
            }

            return updateResourcesLegacy(context, language, country);
        }

        private static String getPersistedLanguageData(Context context, String defaultLanguage) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
        }


        private static String getPersistedCountryData(Context context, String defaultCountry) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(SELECTED_COUNTRY, defaultCountry);
        }

        private static void persist(Context context, String language, String country) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(SELECTED_LANGUAGE, language);
            editor.putString(SELECTED_COUNTRY, country);
            editor.apply();
        }

        @TargetApi(Build.VERSION_CODES.N)
        private static Context updateResources(Context context, String language, String country) {
            Locale locale = new Locale(language,country);
            Locale.setDefault(locale);

            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);

            return context.createConfigurationContext(configuration);
        }

        @SuppressWarnings("deprecation")
        private static Context updateResourcesLegacy(Context context, String language, String country) {
            Locale locale = new Locale(language, country);
            Locale.setDefault(locale);

            Resources resources = context.getApplicationContext().getResources();

            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;

            resources.updateConfiguration(configuration, resources.getDisplayMetrics());

            return context;
        }
}
