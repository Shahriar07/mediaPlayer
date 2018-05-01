package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAtTin extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6777,12000,16298,21450,30563,37878,49234,55612,63200,Integer.MAX_VALUE};

    public SurahAtTin(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_at_tin));
        this.setSurahNumber(95);
        this.setMadani(false);
        this.setResourceId(R.raw.s_95);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
