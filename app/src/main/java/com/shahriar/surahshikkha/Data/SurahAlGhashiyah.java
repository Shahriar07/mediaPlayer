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
    protected final int duration[] ={0, 6050,10200,15600,19800,23600,27650,35500,41854,47900,51150,55250,59950,63950,69000,73150,76150,79900,
            86400,91750,95450,98960,106139,110300,114950,120300,126500,133000,Integer.MAX_VALUE};

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
