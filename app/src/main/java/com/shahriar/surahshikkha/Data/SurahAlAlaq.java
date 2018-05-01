package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlAlaq extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6951,11552,16341,19839,23240,28800,Integer.MAX_VALUE};

    public SurahAlAlaq(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_alaq));
        this.setSurahNumber(96);
        this.setMadani(false);
        this.setResourceId(R.raw.s_96);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
