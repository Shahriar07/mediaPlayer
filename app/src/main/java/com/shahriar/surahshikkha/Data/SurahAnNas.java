package com.shahriar.surahshikkha.Data;

import android.content.Context;
import android.util.Log;

import com.shahriar.surahshikkha.Factory.SurahFactory;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.ApplicationContextManager;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahAnNas extends Surah {

    public int[] getDurationList() {
        return duration;
    }
    // [0 , 1, 2, maxInt] - It has only one verse where 0-1 is starting, 1-2 is the verse, 2-maxint is the padding
    protected final int duration[] ={0, 6950,13400,19150,24700,33050,41750,49300,Integer.MAX_VALUE};

    public SurahAnNas(Context context) {
        super(context);
        this.setSurahName(context.getString(R.string.surah_an_nas));
        this.setSurahNameSecondary(context.getString(R.string.bn_surah_an_nas));
        this.setSurahNumber(114);
        this.setMadani(false);
        this.setResourceId(R.raw.s_114);
        prepareSuraVerses();
        setsurahDurationFromRaw();
        setVerseCount(getVerses().size());
    }

//    @Override
//    public Surah getSuraContent() {
//        return new SurahAnNas();
//    }

//    @Override
//    public void prepareSuraVerses() {
//
//        if ( context ==null){
//            throw new NullPointerException("Need to set ApplicationContext in contextManager");
//        }
//        String arabicVerseName = "a_bismillah";
//        String secondLanguageVerseName = "b_bismillah";
//        int arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
//        int secondLanguageVerseId = context.getResources().getIdentifier(secondLanguageVerseName,"drawable",context.getPackageName());
//        Log.d(getClass().getSimpleName(),"Arabic verse Id "+arabicVerseId + " secondLanguageVerseId "+secondLanguageVerseId);
//        Verse v = new Verse(0, arabicVerseId, secondLanguageVerseId );
//        this.getVerses().add(v);
//        int verseCount = getVerseCount();
//        for (int i =1; i <= verseCount ; i++){
//            arabicVerseName = "a"+getSurahNumber()+""+(i);
//            secondLanguageVerseName = "b"+getSurahNumber()+""+(i);
//            arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
//            secondLanguageVerseId = context.getResources().getIdentifier(secondLanguageVerseName,"drawable",context.getPackageName());
//            v = new Verse(i, arabicVerseId, secondLanguageVerseId);
//            Log.d(getClass().getSimpleName(),"Arabic verse Id "+arabicVerseId + " secondLanguageVerseId "+secondLanguageVerseId);
//            this.getVerses().add(v);
//        }
//    }
}
