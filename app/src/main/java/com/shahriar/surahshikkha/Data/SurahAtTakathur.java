package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAtTakathur extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7050,11000,15054,21154,28000,35000,41400,50000,61100,Integer.MAX_VALUE};

    public SurahAtTakathur(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_at_takathur));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_at_takathur));
        this.setSurahNumber(102);
        this.setMadani(false);
        this.setResourceId(R.raw.s_102);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
