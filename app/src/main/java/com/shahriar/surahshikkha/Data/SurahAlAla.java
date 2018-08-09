package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlAla extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7350,11550,15450,19050,24350,30050,34250,47250,50850,56050,
            61000,65450,70850,77650,82150,86500,91350,96600,102100,107100,Integer.MAX_VALUE};

    public SurahAlAla(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_ala));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_ala));
        this.setSurahNumber(87);
        this.setMadani(false);
        this.setResourceId(R.raw.s_87);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
