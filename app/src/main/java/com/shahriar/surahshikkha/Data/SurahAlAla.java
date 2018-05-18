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
    protected final int duration[] ={0, 7050,11000,14954,18700,23900,29500,34100,47000,50580,55750,
            60700,65300,70550,77400,81900,86300,91000,96350,101800,107100,Integer.MAX_VALUE};

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
