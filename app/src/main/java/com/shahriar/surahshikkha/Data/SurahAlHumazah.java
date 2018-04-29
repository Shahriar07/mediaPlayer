package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlHumazah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7550,12400,18154,25054,32200,37400,41100,45600,51600,57100,Integer.MAX_VALUE};

    public SurahAlHumazah(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_humazah));
        this.setSurahNumber(104);
        this.setMadani(false);
        this.setResourceId(R.raw.s_104);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
