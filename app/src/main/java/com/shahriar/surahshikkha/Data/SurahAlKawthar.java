package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlKawthar extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7150,14200,17854,23200,Integer.MAX_VALUE};

    public SurahAlKawthar(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_kawthar));
        this.setSurahNumber(108);
        this.setMadani(false);
        this.setResourceId(R.raw.s_108);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
