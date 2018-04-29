package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlAdiyat extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6650,9900,13354,16654,19800,23000,31500,38400,46100,53700,58800,69100,Integer.MAX_VALUE};

    public SurahAlAdiyat(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_adiyat));
        this.setSurahNumber(100);
        this.setMadani(false);
        this.setResourceId(R.raw.s_100);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
