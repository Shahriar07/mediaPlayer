package com.shahriar.androidtestapplication.Data;

/**
 * Created by H. M. Shahriar on 3/26/2018.
 */

public class SurahInfo {
    private String surahName;
    private int surahNumber;
    private boolean madani;

    public SurahInfo(String surahName, int surahNumber, boolean madani) {
        this.surahName = surahName;
        this.surahNumber = surahNumber;
        this.madani = madani;
    }

    public SurahInfo(){

    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
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
}
