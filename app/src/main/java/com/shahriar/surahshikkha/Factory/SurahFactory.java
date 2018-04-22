package com.shahriar.surahshikkha.Factory;

import android.content.Context;
import android.util.Log;

import com.shahriar.surahshikkha.Data.Surah;
import com.shahriar.surahshikkha.Data.SurahAlBalad;
import com.shahriar.surahshikkha.Data.SurahAlFatihah;
import com.shahriar.surahshikkha.Data.SurahAnNas;

import java.util.HashMap;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahFactory {

//    private HashMap m_RegisteredSurah = new HashMap();
    private static SurahFactory instance;
    Context applicationContext;

    public static SurahFactory getInstance(Context context){
        if (instance == null)
        {
            synchronized (SurahFactory.class){
                if (instance == null)
                instance = new SurahFactory();
            }
        }
        if (context != null)
            instance.applicationContext = context.getApplicationContext();
        return instance;
    }

//    public void registerSurah(String surahNumber, Surah surah){
//        Log.d(getClass().getSimpleName(),"Registered surah " + surahNumber );
//        m_RegisteredSurah.put(surahNumber, surah);
//    }

    public Surah prepareSurah(String surahNumber){
        switch (surahNumber){
            case "1":
            {
                return new SurahAlFatihah(applicationContext);
            }
            case "114":
            {
                return new SurahAnNas(applicationContext);
            }
            case "90":
            {
                return new SurahAlBalad(applicationContext);
            }
            default:
                return new SurahAnNas(applicationContext);
        }
//        Surah surah = (Surah)m_RegisteredSurah.get(surahNumber);
//        if (surah != null)
//         return surah.getSuraContent();

//        return null;
    }
}
