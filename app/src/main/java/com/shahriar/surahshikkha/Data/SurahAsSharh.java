package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAsSharh extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6830,10536,15209,21043,24657,29400,33830,38300,42000,Integer.MAX_VALUE};

    public SurahAsSharh(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_as_sharh));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_as_sharh));
        this.setSurahNumber(94);
        this.setMadani(false);
        this.setResourceId(R.raw.s_94);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
