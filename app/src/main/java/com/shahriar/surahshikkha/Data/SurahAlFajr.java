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
    protected final int duration[] ={0, 6647,8000,10522,13163,15978,20564,26892,31404,38560,46180,51650,57320,62655,69616,76176,92038,105607,113154,123210,129161,136141,143571,151437,168968,174816,181942,187631,196150,204336,207571,212000,Integer.MAX_VALUE};

    public SurahAlFajr(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_fajr));
        this.setSurahNumber(89);
        this.setMadani(false);
        this.setResourceId(R.raw.s_89);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
