package com.shahriar.surahshikkha.Data;

import android.content.Context;

import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 4/29/2018.
 */

public class SurahAlFajr extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6647,8300,10900,13700,16600,21050,27000,31650,39300,46880,52100,57950,63350,69850,
            76376,92250,106100,113754,124210,130100,137100,144300,152150,169800,175350,182650,188300,196750,204636,208200,212300,Integer.MAX_VALUE};

    public SurahAlFajr(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_fajr));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_fajr));
        this.setSurahNumber(89);
        this.setMadani(false);
        this.setResourceId(R.raw.s_89);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }
}
