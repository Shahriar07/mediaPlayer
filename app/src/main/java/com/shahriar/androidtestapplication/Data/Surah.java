package com.shahriar.androidtestapplication.Data;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;
import com.shahriar.androidtestapplication.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public abstract class  Surah {

//    public abstract Surah getSuraContent();
    private ArrayList<Verse> verses = new ArrayList<>();
    private SurahInfo surahInfo;
    Context context;

    int resourceId;
    public abstract int[] getDurationList();

    protected void setsurahDurationFromRaw() {
        MediaPlayer mp = MediaPlayer.create(context, getResourceId());
        int duration = mp.getDuration();
        Log.i(getClass().getSimpleName(),"Duration :"+duration);
        setSurahDuration(duration);
    }
    public int getVerseCount(){
        return getDurationList().length - Constants.EXTRA_SURAH_VERSE_IN_DURATION;
    }

    public ArrayList<Verse> getVerses() {
        return verses;
    }

    public void setVerses(ArrayList<Verse> verses) {
        this.verses = verses;
    }

    public Surah(String surahName, ArrayList<Verse> verses, int surahNumber, boolean madani, int verseCount) {
        context = ApplicationContextManager.getInstance(null).getAppContext();
        this.surahInfo = new SurahInfo(surahName,surahNumber,madani,0, verseCount);
        this.verses = verses;
    }

    public Surah() {
        context = ApplicationContextManager.getInstance(null).getAppContext();
        this.surahInfo = new SurahInfo();
    }

    public int getSurahNumber() {
        return surahInfo.getSurahNumber();
    }

    public void setSurahNumber(int surahNumber) {
        this.surahInfo.setSurahNumber(surahNumber);
    }

    public boolean isMadani() {
        return surahInfo.isMadani();
    }

    public void setMadani(boolean madani) {
        this.surahInfo.setMadani(madani);
    }

    public String getSurahName() {
        return surahInfo.getSurahName();
    }

    public void setSurahName(String surahName) {
        this.surahInfo.setSurahName(surahName);
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getSurahDuration() {
        return surahInfo.getSurahDuration();
    }

    public void setSurahDuration(int surahDuration) {
        this.surahInfo.setSurahDuration(surahDuration);
    }

    public void setVerseCount(int verseCount) {
        this.surahInfo.setVerseCount(verseCount);
    }

    public void prepareSuraVerses() {
        ApplicationContextManager applicationContextManager = ApplicationContextManager.getInstance(null);
        Context context = applicationContextManager.getAppContext();
        if ( context ==null){
            throw new NullPointerException("Need to set ApplicationContext in contextManager");
        }
        String arabicVerseName = "a_bismillah";
        String secondLanguageVerseName = "b_bismillah";
        int arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
        int secondLanguageVerseId = context.getResources().getIdentifier(secondLanguageVerseName,"drawable",context.getPackageName());
        Verse v = new Verse(0, arabicVerseId, secondLanguageVerseId );
        this.getVerses().add(v);
        int verseCount = getVerseCount();
        for (int i =1; i <= verseCount ; i++){
            arabicVerseName = "a"+getSurahNumber()+""+(i);
            secondLanguageVerseName = "b"+getSurahNumber()+""+(i);
            arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
            secondLanguageVerseId = context.getResources().getIdentifier(secondLanguageVerseName,"drawable",context.getPackageName());
            v = new Verse(i, arabicVerseId, secondLanguageVerseId);
            this.getVerses().add(v);
        }
    }
}
