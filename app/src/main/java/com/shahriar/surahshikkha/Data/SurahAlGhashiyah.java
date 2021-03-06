package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlGhashiyah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6050,10200,15600,19800,23600,27650,35500,41854,48200,51550,55500,59950,64000,69000,73150,76170,80000,
            86400,91850,95650,99250,106400,110700,115250,120500,126500,133300,Integer.MAX_VALUE};

    public SurahAlGhashiyah(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_ghashiyah));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_ghashiyah));
        this.setSurahNumber(88);
        this.setMadani(false);
        this.setResourceId(R.raw.s_88);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
