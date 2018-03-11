package com.shahriar.androidtestapplication.Data;

import com.shahriar.androidtestapplication.Exception.SurahContentNotPreparedException;
import com.shahriar.androidtestapplication.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public abstract class  Surah {

    public abstract Surah getSuraContent();
    public abstract void prepareSuraVerses();

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
}
