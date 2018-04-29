package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlQariah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7250,9500,12100,18154,26000,34100,40300,44500,49600,53350,58100,61100,Integer.MAX_VALUE};

    public SurahAlQariah(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_qariah));
        this.setSurahNumber(101);
        this.setMadani(false);
        this.setResourceId(R.raw.s_101);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
