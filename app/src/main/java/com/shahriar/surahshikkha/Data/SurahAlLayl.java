package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlLayl extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6100,10100,14750,20000,24700,29550,32850,36700,41850,44850,48800,56100,60000,
            66000,72290,78000,82350,87140,92400,100400,107450,110309,Integer.MAX_VALUE};

    public SurahAlLayl(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_layl));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_layl));
        this.setSurahNumber(92);
        this.setMadani(false);
        this.setResourceId(R.raw.s_92);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
