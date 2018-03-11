package com.shahriar.androidtestapplication.Data;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class Verse {
    private int verseNo;
    private int surahVersesArabicResourceId;
    private int surahVersesTranslatedResourceId;

    public Verse(int verseNo, int surahVersesArabicResourceId, int surahVersesTranslated) {
        this.verseNo = verseNo;
        this.surahVersesArabicResourceId = surahVersesArabicResourceId;
        this.surahVersesTranslatedResourceId = surahVersesTranslated;
    }

    public int getVerseNo() {
        return verseNo;
    }

    public void setVerseNo(int verseNo) {
        this.verseNo = verseNo;
    }

    public int getSurahVersesArabicResourceId() {
        return surahVersesArabicResourceId;
    }

    public void setSurahVersesArabicResourceId(int surahVersesArabicResourceId) {
        this.surahVersesArabicResourceId = surahVersesArabicResourceId;
    }

    public int getSurahVersesTranslatedResourceId() {
        return surahVersesTranslatedResourceId;
    }

    public void setSurahVersesTranslatedResourceId(int surahVersesTranslatedResourceId) {
        this.surahVersesTranslatedResourceId = surahVersesTranslatedResourceId;
    }
}
