package com.shahriar.androidtestapplication.Factory;

import android.util.Log;

import com.shahriar.androidtestapplication.Data.Surah;
import com.shahriar.androidtestapplication.Data.SurahAlBalad;
import com.shahriar.androidtestapplication.Data.SurahAnNas;

import java.util.HashMap;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahFactory {

//    private HashMap m_RegisteredSurah = new HashMap();
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

//    public void registerSurah(String surahNumber, Surah surah){
//        Log.d(getClass().getSimpleName(),"Registered surah " + surahNumber );
//        m_RegisteredSurah.put(surahNumber, surah);
//    }

    public Surah prepareSurah(String surahNumber){
        switch (surahNumber){
            case "114":
            {
                return new SurahAnNas();
            }
            case "90":
            {
                return new SurahAlBalad();
            }
            default:
                return new SurahAnNas();
        }
//        Surah surah = (Surah)m_RegisteredSurah.get(surahNumber);
//        if (surah != null)
//         return surah.getSuraContent();

//        return null;
    }
}
