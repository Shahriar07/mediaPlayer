package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAtTariq extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7550,12900,18480,22400,29300,36650,43475,51380,56950,61880,68700,73550,76850,81900,84600,89650,92320,99500,Integer.MAX_VALUE};

    public SurahAtTariq(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_at_tariq));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_at_tariq));
        this.setSurahNumber(86);
        this.setMadani(false);
        this.setResourceId(R.raw.s_86);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
