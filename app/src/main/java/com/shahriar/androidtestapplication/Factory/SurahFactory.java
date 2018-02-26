package com.shahriar.androidtestapplication.Factory;

import android.util.Log;

import com.shahriar.androidtestapplication.Data.Surah;

import java.util.HashMap;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahFactory {

    private HashMap m_RegisteredSurah = new HashMap();
    private static SurahFactory instance;

    public static SurahFactory getInstance(){
        if (instance == null)
        {
            synchronized (SurahFactory.class){
                if (instance == null)
                instance = new SurahFactory();
            }
        }
        return instance;
    }

    public void registerSurah(String surahNumber, Surah surah){
        Log.d(getClass().getSimpleName(),"Registered surah " + surahNumber );
        m_RegisteredSurah.put(surahNumber, surah);
    }

    public Surah prepareSurah(String surahNumber){
        Surah surah = (Surah)m_RegisteredSurah.get(surahNumber);
        if (surah != null)
         return surah.getSuraContent();

        return null;
    }
}
