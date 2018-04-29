package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAshShams extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7250,11000,14754,20154,26400,32000,Integer.MAX_VALUE};

    public SurahAshShams(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_ash_shams));
        this.setSurahNumber(91);
        this.setMadani(false);
        this.setResourceId(R.raw.s_91);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
