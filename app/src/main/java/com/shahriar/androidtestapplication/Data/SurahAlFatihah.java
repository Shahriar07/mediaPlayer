package com.shahriar.androidtestapplication.Data;

import com.shahriar.androidtestapplication.R;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAlFatihah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 12200,17230,22200,26700,33300,38750,42800,51400,Integer.MAX_VALUE};

    public SurahAlFatihah() {
        super();
        this.setSurahName(context.getString(R.string.surah_al_fatihah));
        this.setSurahNumber(1);
        this.setMadani(false);
        this.setResourceId(R.raw.surah_al_fatiha_1);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
