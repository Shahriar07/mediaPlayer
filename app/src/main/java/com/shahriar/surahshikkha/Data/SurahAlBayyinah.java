package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlBayyinah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6750,20000,27734,31954,44285,66310,85770,98000,125000,Integer.MAX_VALUE};

    public SurahAlBayyinah(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_bayyinah));
        this.setSurahNumber(98);
        this.setMadani(false);
        this.setResourceId(R.raw.s_98);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
