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
    protected final int duration[] ={0, 6141,9800,14523,19679,24191,29450,32561,36265,41577,44537,48598,55655,59900,
            65377,71893,77780,81746,86920,92124,100338,107173,110309,Integer.MAX_VALUE};

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
