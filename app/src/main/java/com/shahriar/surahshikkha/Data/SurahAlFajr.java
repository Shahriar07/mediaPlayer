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
    protected final int duration[] ={0, 6647,8300,10900,13700,16600,21050,27000,31650,39300,46880,52100,58050,63350,69900,
            76376,92450,106300,114050,124210,130250,137100,144450,152250,169900,175400,182900,188400,196900,204636,208350,212300,Integer.MAX_VALUE};

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
