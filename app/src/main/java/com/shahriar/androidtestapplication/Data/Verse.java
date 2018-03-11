package com.shahriar.androidtestapplication.Data;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class Verse {
    private int verseNo;
    private String surahVersesArabic;
    private int surahVersesTranslatedResourceId;

    public Verse(int verseNo, String surahVersesArabic, int surahVersesTranslated) {
        this.verseNo = verseNo;
        this.surahVersesArabic = surahVersesArabic;
        this.surahVersesTranslatedResourceId = surahVersesTranslated;
    }

    public int getVerseNo() {
        return verseNo;
    }

    public void setVerseNo(int verseNo) {
        this.verseNo = verseNo;
    }

    public String getSurahVersesArabic() {
        return surahVersesArabic;
    }

    public void setSurahVersesArabic(String surahVersesArabic) {
        this.surahVersesArabic = surahVersesArabic;
    }

    public int getSurahVersesTranslatedResourceId() {
        return surahVersesTranslatedResourceId;
    }

    public void setSurahVersesTranslatedResourceId(int surahVersesTranslatedResourceId) {
        this.surahVersesTranslatedResourceId = surahVersesTranslatedResourceId;
    }
}
