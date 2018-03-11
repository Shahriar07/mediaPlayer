package com.shahriar.androidtestapplication.Data;

import android.content.Context;

import com.shahriar.androidtestapplication.Exception.SurahContentNotPreparedException;
import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;
import com.shahriar.androidtestapplication.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public abstract class  Surah {

    public abstract Surah getSuraContent();

    private String surahName;
    private ArrayList<Verse> verses = new ArrayList<>();
    private int surahNumber;
    private boolean madani;

    int resourceId;
    public abstract int[] getDuration();
    public int getVerseCount(){
        return getDuration().length - Constants.EXTRA_SURAH_VERSE_IN_DURATION;
    }

    public ArrayList<Verse> getVerses() {
        return verses;
    }

    public void setVerses(ArrayList<Verse> verses) {
        this.verses = verses;
    }

    public Surah(String surahName, ArrayList<Verse> verses, int surahNumber, boolean madani) {
        this.surahName = surahName;
        this.verses = verses;
        this.surahNumber = surahNumber;
        this.madani = madani;
    }

    public Surah() {

    }

    public int getSurahNumber() {
        return surahNumber;
    }

    public void setSurahNumber(int surahNumber) {
        this.surahNumber = surahNumber;
    }

    public boolean isMadani() {
        return madani;
    }

    public void setMadani(boolean madani) {
        this.madani = madani;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

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
