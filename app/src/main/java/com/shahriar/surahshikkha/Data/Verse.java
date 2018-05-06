package com.shahriar.surahshikkha.Data;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class Verse {
    private int verseNo;
    private int surahVersesArabicResourceId;
//    private String verseTransliterationInBangla;
    private String verseTranslationInBangla;
    private String verseTranslationInEnglish;

    public Verse(int verseNo, int surahVersesArabicResourceId,String verseTranslationInBangla, String verseTranslationInEnglish) {
        this.verseNo = verseNo;
        this.surahVersesArabicResourceId = surahVersesArabicResourceId;
//        this.verseTransliterationInBangla = verseTransliterationInBangla;
        this.verseTranslationInBangla = verseTranslationInBangla;
        this.verseTranslationInEnglish = verseTranslationInEnglish;
    }

    public int getVerseNo() {
        return verseNo;
    }

    public int getSurahVersesArabicResourceId() {
        return surahVersesArabicResourceId;
    }

//    public String getVerseTransliterationInBangla() {
//        return verseTransliterationInBangla;
//    }

    public String getVerseTranslationInBangla() {
        return verseTranslationInBangla;
    }

    public String getVerseTranslationInEnglish() {
        return verseTranslationInEnglish;
    }
}
