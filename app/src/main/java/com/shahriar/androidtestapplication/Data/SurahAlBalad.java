package com.shahriar.androidtestapplication.Data;

import com.shahriar.androidtestapplication.Exception.SurahContentNotPreparedException;
import com.shahriar.androidtestapplication.Factory.SurahFactory;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAlBalad extends Surah {

    static
    {
        SurahFactory.getInstance().registerSurah("90", new SurahAlBalad());
    }

    public int[] getDuration() {
        return duration;
    }

    protected final int duration[] ={0, 7200,13230,19200,23500,29000,35200,39300,45000,51000,57000,63300,66100,71800,73800,80000,84000,88400,99200,105300,113050,119015,Integer.MAX_VALUE};

    public SurahAlBalad() {
        super();
        this.setSurahName("Surah-Al-Balad");
        this.setSurahNumber(90);
        this.setMadani(false);
        prepareSuraVerses();
    }

    @Override
    public Surah getSuraContent() {
        return new SurahAlBalad();
    }

    @Override
    public void prepareSuraVerses() {
        for (int i =0; i < duration.length - 1 ; i++){
            Verse v = new Verse(i, "Verse in Arabic", "Verse in Bangla");
            this.getVerses().add(v);
        }
    }
}
