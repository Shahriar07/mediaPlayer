package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlKafirun extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6950,15000,22954,31554,38400,47300,53200,Integer.MAX_VALUE};

    public SurahAlKafirun(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_kafirun));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_kafirun));
        this.setSurahNumber(109);
        this.setMadani(false);
        this.setResourceId(R.raw.s_109);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
