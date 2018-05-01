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
    protected final int duration[] ={0, 6141,9800,14523,19679,24191,29450,32461,36165,41477,44437,48298,55655,59300,65277,71793,77780,81546,86920,92024,100238,107073,110309,Integer.MAX_VALUE};

    public SurahAlLayl(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_layl));
        this.setSurahNumber(92);
        this.setMadani(false);
        this.setResourceId(R.raw.s_92);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
