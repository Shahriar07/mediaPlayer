package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlMaun extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7250,13550,20500,27854,34100,42000,50000,56100,Integer.MAX_VALUE};

    public SurahAlMaun(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_maun));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_maun));
        this.setSurahNumber(107);
        this.setMadani(false);
        this.setResourceId(R.raw.s_107);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
