package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAzZilzalah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6900,12000,16550,21954,27600,32800,41850,49450,59050,Integer.MAX_VALUE};

    public SurahAzZilzalah(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_az_zilzalah));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_az_zilzalah));
        this.setSurahNumber(99);
        this.setMadani(false);
        this.setResourceId(R.raw.s_99);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
