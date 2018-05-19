package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlMasad extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6850,14854,22154,27500,33400,40100,Integer.MAX_VALUE};

    public SurahAlMasad(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_masad));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_masad));
        this.setSurahNumber(111);
        this.setMadani(false);
        this.setResourceId(R.raw.s_111);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
