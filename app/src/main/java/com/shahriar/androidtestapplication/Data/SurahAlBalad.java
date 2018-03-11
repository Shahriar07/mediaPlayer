package com.shahriar.androidtestapplication.Data;

import android.content.Context;

import com.shahriar.androidtestapplication.Exception.SurahContentNotPreparedException;
import com.shahriar.androidtestapplication.Factory.SurahFactory;
import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;

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
    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 7200,13230,19200,23500,29000,35200,39300,45000,51000,57000,63300,66100,71800,73800,80000,84000,88400,99200,105300,113050,119015,Integer.MAX_VALUE};

    public SurahAlBalad() {
        super();
        this.setSurahName("Surah-Al-Balad");
        this.setSurahNumber(90);
        this.setMadani(false);
        this.setResourceId(R.raw.surah_al_balad_90);
        prepareSuraVerses();
    }

    @Override
    public Surah getSuraContent() {
        return new SurahAlBalad();
    }

    @Override
    public void prepareSuraVerses() {
        ApplicationContextManager applicationContextManager = ApplicationContextManager.getInstance(null);
        Context context = applicationContextManager.getAppContext();
        if ( context ==null){
            throw new NullPointerException("Need to set ApplicationContext in contextManager");
        }
        for (int i =0; i < duration.length - 1 ; i++){
            String verseName = "b"+getSurahNumber()+"1";//"b"+getSurahNumber()+""+(i+1);
            Verse v = new Verse(i, "Verse in Arabic", context.getResources().getIdentifier(verseName,"drawable",context.getPackageName()));
            this.getVerses().add(v);
        }
    }
}
