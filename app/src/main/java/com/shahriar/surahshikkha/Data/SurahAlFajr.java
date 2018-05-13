package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlFajr extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6647,8000,10522,13163,15978,20564,26892,31404,38560,46230,51900,57720,63100,69280,76076,92038,105607,113154,123210,129261,136241,143630,151437,169068,174816,182242,187731,196150,204336,207571,212000,Integer.MAX_VALUE};

    public SurahAlFajr(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_fajr));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_fajr));
        this.setSurahNumber(89);
        this.setMadani(false);
        this.setResourceId(R.raw.s_89);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
