package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlFalaq extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7350,10850,14754,19904,26430,32000,Integer.MAX_VALUE};

    public SurahAlFalaq(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_falaq));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_falaq));
        this.setSurahNumber(113);
        this.setMadani(false);
        this.setResourceId(R.raw.s_113);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
