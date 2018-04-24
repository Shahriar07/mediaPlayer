package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAdDuha extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 5700,7030,11200,16500,21000,26200,31300,39000,45000,51600,56300,64100,Integer.MAX_VALUE};

    public SurahAdDuha(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_ad_duha));
        this.setSurahNumber(93);
        this.setMadani(false);
        this.setResourceId(R.raw.ad_duha_093);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }

//    @Override
//    public Surah getSuraContent() {
//        return new SurahAlBalad();
//    }
}
