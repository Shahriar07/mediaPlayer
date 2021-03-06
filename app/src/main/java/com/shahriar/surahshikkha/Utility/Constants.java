package com.shahriar.surahshikkha.Utility;

/**
 * Created by H. M. Shahriar on 3/3/2018.
 */

public class Constants {

    // Intent extras
    public static final String SURAH_ACTIVITY_SURAH_NO = "surah_no";
    public static final int EXTRA_SURAH_VERSE_IN_DURATION = 3; // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding

    // Shared preference key
    public static final String SURAH_VERSE_REPEAT_CONTROL = "verse_repeat";
    public static final String SURAH_VERSE_MAX_REPEAT_COUNT = "max_repeat_count";
    public static final String SELECTED_LANGUAGE = "language";
    public static final String SURAH_VERSE_AUTO_SCROLL = "auto_scroll";
    public static final String SURAH_SORT_CONTROL = "sort";
    public static final String MENU_ENGLISH_TRANSLATION = "english_translation";
    public static final String MENU_BANGLA_TRANSLATION = "bangla_translation";
    public static final String ACTIVITY_SHOW_GUIDE = "show_guide";

    public static final int SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT = 3;
    public static final int SURAH_VERSE_MAX_REPEAT_COUNT_NUMBER = 15;
    public static final int SURAH_VERSE_MIN_REPEAT_COUNT_NUMBER = 1;

    // Sort
    public static final int SURAH_VERSE_SORT_BY_NUMBER = 0;
    public static final int SURAH_VERSE_SORT_BY_DURATION = 1;
    public static final int SURAH_VERSE_SORT_BY_VERSE_NUMBER = 2;

    // Language
    public static final String LANGUAGE_ENGLISH = "English";
    public static final String LANGUAGE_BANGLA = "বাংলা";
    public static final int LANGUAGE_ENGLISH_VALUE = 0;
    public static final int LANGUAGE_BANGLA_VALUE = 1;
    public static final String[] LANGUAGE_LIST = {LANGUAGE_ENGLISH,LANGUAGE_BANGLA};

}
