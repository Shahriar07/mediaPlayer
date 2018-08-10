package com.shahriar.surahshikkha.Data;

import android.content.Context;
import android.media.MediaPlayer;

import com.shahriar.surahshikkha.Exception.SurahContentNotPreparedException;
import com.shahriar.surahshikkha.Factory.SurahFactory;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.ApplicationContextManager;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAlBalad extends Surah {

    public int[] getDurationList() {
        return duration;
    }

    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7500,13730,19600,23950,29600,35200,39800,45500,51300,57700,64000,66800,71900,73900,80500,84500,88900,99900,106200,113950,119015,Integer.MAX_VALUE};

    public SurahAlBalad(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_al_balad));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_al_balad));
        this.setSurahNumber(90);
        this.setMadani(false);
        this.setResourceId(R.raw.s_90);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }

//    @Override
//    public Surah getSuraContent() {
//        return new SurahAlBalad();
//    }
}
