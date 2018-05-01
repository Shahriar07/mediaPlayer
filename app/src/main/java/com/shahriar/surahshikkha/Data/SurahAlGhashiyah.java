package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlGhashiyah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 5921,10100,15300,19500,23300,27254,34854,41654,47644,51142,54900,59258,63500,68383,72700,75770,79702,85870,91254,95250,98960,105839,110000,114550,120000,126260,132600,Integer.MAX_VALUE};

    public SurahAlGhashiyah(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_ghashiyah));
        this.setSurahNumber(88);
        this.setMadani(false);
        this.setResourceId(R.raw.s_88);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
