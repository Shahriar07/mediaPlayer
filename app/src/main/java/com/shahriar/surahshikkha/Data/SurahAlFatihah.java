package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAlFatihah extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={250, 6250,11700,16380,20950,27600,33130,36770,45300,Integer.MAX_VALUE}; //52088

    public SurahAlFatihah(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_fatihah));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_fatihah));
        this.setSurahNumber(1);
        this.setMadani(false);
        this.setResourceId(R.raw.s_1);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
