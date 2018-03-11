package com.shahriar.androidtestapplication.Data;

import android.content.Context;
import android.util.Log;

import com.shahriar.androidtestapplication.Factory.SurahFactory;
import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAnNas extends Surah {

    static
    {
        SurahFactory.getInstance().registerSurah("114", new SurahAnNas());
    }

    public int[] getDuration() {
        return duration;
    }
    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6850,13200,18650,24500,32920,41600,49700,Integer.MAX_VALUE};

    public SurahAnNas() {
        super();
        this.setSurahName("Surah-An-Nas");
        this.setSurahNumber(114);
        this.setMadani(false);
        this.setResourceId(R.raw.surah_an_nas_114);
        prepareSuraVerses();
    }

    @Override
    public Surah getSuraContent() {
        return new SurahAnNas();
    }

    @Override
    public void prepareSuraVerses() {
        ApplicationContextManager applicationContextManager = ApplicationContextManager.getInstance(null);
        Context context = applicationContextManager.getAppContext();
        if ( context ==null){
            throw new NullPointerException("Need to set ApplicationContext in contextManager");
        }
        String arabicVerseName = "a_bismillah";
        String secondLanguageVerseName = "b_bismillah";
        int arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
        int secondLanguageVerseId = context.getResources().getIdentifier(secondLanguageVerseName,"drawable",context.getPackageName());
        Log.d(getClass().getSimpleName(),"Arabic verse Id "+arabicVerseId + " secondLanguageVerseId "+arabicVerseId);
        Verse v = new Verse(0, arabicVerseId, secondLanguageVerseId );
        this.getVerses().add(v);
        int verseCount = getVerseCount();
        for (int i =1; i <= verseCount ; i++){
            arabicVerseName = "a"+getSurahNumber()+""+(i);
            secondLanguageVerseName = "b"+getSurahNumber()+""+(i);
            arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
            secondLanguageVerseId = context.getResources().getIdentifier(secondLanguageVerseName,"drawable",context.getPackageName());
            v = new Verse(i, arabicVerseId, secondLanguageVerseId);
            this.getVerses().add(v);
        }
    }
}
