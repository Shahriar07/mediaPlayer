package com.shahriar.androidtestapplication.Data;

import com.shahriar.androidtestapplication.Factory.SurahFactory;
import com.shahriar.androidtestapplication.R;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAnNas extends Surah {

    static
    {
        SurahFactory.getInstance().registerSurah("114", new SurahAnNas());
    }

    public int[] getDuration() {
        return duration;
    }

    protected final int duration[] ={0, 6850,13230,18600,24500,32600,41600,49700,Integer.MAX_VALUE};

    public SurahAnNas() {
        super();
        this.setSurahName("Surah-An-Nas");
        this.setSurahNumber(114);
        this.setMadani(false);
        this.setResourceId(R.raw.surah_an_nas_114);
        prepareSuraVerses();
    }

    @Override
    public Surah getSuraContent() {
        return new SurahAnNas();
    }

    @Override
    public void prepareSuraVerses() {
        for (int i =0; i < duration.length - 1 ; i++){
            Verse v = new Verse(i, "Verse in Arabic", "Verse in Bangla");
            this.getVerses().add(v);
        }
    }
}
