package com.shahriar.androidtestapplication.Data;

import java.util.ArrayList;

/**
 * Created by USER on 2/24/2018.
 */

public class Surah {

    private String surahName;
    private ArrayList<Verse> verses = new ArrayList<>();
    private int surahNumber;
    private boolean madani;

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
}
