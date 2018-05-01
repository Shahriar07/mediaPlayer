package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlQadr extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6750,15064,21449,27240,39150,44176,Integer.MAX_VALUE};

    public SurahAlQadr(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_qadr));
        this.setSurahNumber(97);
        this.setMadani(false);
        this.setResourceId(R.raw.s_97);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
