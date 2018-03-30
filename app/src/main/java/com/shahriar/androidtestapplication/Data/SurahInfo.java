package com.shahriar.androidtestapplication.Data;

/**
 * Created by H. M. Shahriar on 3/26/2018.
 */

public class SurahInfo {
    private String surahName;
    private int surahNumber;
    private boolean madani;
    private int surahDuration;

    public SurahInfo(String surahName, int surahNumber, boolean madani,int surahDuration) {
        this.surahName = surahName;
        this.surahNumber = surahNumber;
        this.madani = madani;
        this.surahDuration = surahDuration;
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

    public int getSurahDuration() {
        return surahDuration;
    }

    public void setSurahDuration(int surahDuration) {
        this.surahDuration = surahDuration;
    }
}
