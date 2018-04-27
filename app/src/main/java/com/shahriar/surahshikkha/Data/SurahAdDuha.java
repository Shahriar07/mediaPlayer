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
    protected final int duration[] ={0, 6280,8230,11200,15900,21350,26500,32000,39550,46000,51000,57500,64100,Integer.MAX_VALUE};

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
