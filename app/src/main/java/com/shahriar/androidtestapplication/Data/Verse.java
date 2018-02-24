package com.shahriar.androidtestapplication.Data;

/**
 * Created by USER on 2/24/2018.
 */

public class Verse {
    private int verseNo;
    private String surahVersesArabic;
    private String surahVersesTranslated;

    public Verse(int verseNo, String surahVersesArabic, String surahVersesTranslated) {
        this.verseNo = verseNo;
        this.surahVersesArabic = surahVersesArabic;
        this.surahVersesTranslated = surahVersesTranslated;
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

    public String getSurahVersesTranslated() {
        return surahVersesTranslated;
    }

    public void setSurahVersesTranslated(String surahVersesTranslated) {
        this.surahVersesTranslated = surahVersesTranslated;
    }
}
