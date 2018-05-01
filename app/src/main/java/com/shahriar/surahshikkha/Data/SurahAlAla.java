package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlAla extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7050,11000,14854,18300,23900,29370,33790,47000,50280,55550,
            60250,64800,70150,76900,81600,86100,90800,95950,101400,107100,Integer.MAX_VALUE};

    public SurahAlAla(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_ala));
        this.setSurahNumber(87);
        this.setMadani(false);
        this.setResourceId(R.raw.s_87);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
