package com.shahriar.surahshikkha.Data;

/**
 * Created by H. M. Shahriar on 3/26/2018.
 */

public class SurahInfo {
    private String surahName;
    private int surahNumber;
    private boolean madani;
    private int surahDuration;
    private int verseCount;

    public SurahInfo(String surahName, int surahNumber, boolean madani,int surahDuration, int verseCount) {
        this.surahName = surahName;
        this.surahNumber = surahNumber;
        this.madani = madani;
        this.surahDuration = surahDuration;
        this.verseCount = verseCount;
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

    public int getVerseCount() {
        return verseCount;
    }

    public void setVerseCount(int verseCount) {
        this.verseCount = verseCount;
    }
}
