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
    protected final int duration[] ={0, 6647,8000,10522,13163,15978,20564,26892,31404,38760,46460,51950,57920,63100,69680,
            76376,92138,105807,113254,123810,129461,136440,144000,152000,169468,174916,182242,187731,196150,204336,207730,212100,Integer.MAX_VALUE};

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
