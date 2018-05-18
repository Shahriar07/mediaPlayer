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
    protected final int duration[] ={0, 5921,10100,15300,19700,23300,27400,35500,41854,47844,51142,55100,59258,63700,68683,73050,75980,79702,
            86000,91454,95250,98960,106139,110000,114750,120300,126260,133000,Integer.MAX_VALUE};

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
