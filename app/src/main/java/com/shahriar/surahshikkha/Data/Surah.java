package com.shahriar.surahshikkha.Data;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.ApplicationContextManager;
import com.shahriar.surahshikkha.Utility.Constants;
import com.shahriar.surahshikkha.Utility.SharedPreferenceController;

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
        mp.release();
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

    public Surah(Context context) {
        this.context = ApplicationContextManager.getInstance(context).getAppContext();
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

    public String getSurahNameSecondary() {
        return  this.surahInfo.getSurahNameSecondary();
    }

    public void setSurahNameSecondary(String surahNameSecondary) {
        this.surahInfo.setSurahNameSecondary(surahNameSecondary);
    }

    public void prepareSuraVerses() {
        ApplicationContextManager applicationContextManager = ApplicationContextManager.getInstance(this.context);
        Context context = applicationContextManager.getAppContext();
        if ( context ==null){
            throw new NullPointerException("Need to set ApplicationContext in contextManager");
        }
        SharedPreferenceController controller = new SharedPreferenceController(context);
        int language = controller.readIntWithKey(Constants.SELECTED_LANGUAGE);

        String arabicVerseName = "a_bismillah";
      //  String languageInitial = (language == Constants.LANGUAGE_ENGLISH_VALUE) ?"e":"b";
      //  String secondLanguageVerseName = languageInitial+"_bismillah";
        int arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
        //int secondLanguageVerseId = context.getResources().getIdentifier(secondLanguageVerseName,"drawable",context.getPackageName());

        // Get bangla verse transliteration
        int banglaTransliterationId = context.getResources().getIdentifier("s"+getSurahNumber(),"array",context.getPackageName());
        String[] banglaTransliterationList = context.getResources().getStringArray(banglaTransliterationId);

        // Get english verse transliteration
        int englishTransliterationId = context.getResources().getIdentifier("se"+getSurahNumber(),"array",context.getPackageName());
        String[] englishTransliterationList = context.getResources().getStringArray(englishTransliterationId);

        Verse v;
        if (banglaTransliterationList.length >0) {
            v = new Verse(0, arabicVerseId, context.getString(R.string.bismillah_bangla), context.getString(R.string.bismillah_english));
            this.verses.add(v);
        }
        int verseCount = getVerseCount();

        for (int i =1; i <= verseCount ; i++){
            arabicVerseName = "a"+getSurahNumber()+""+(i);
           // secondLanguageVerseName = languageInitial+getSurahNumber()+""+(i); // b+114+1 -> b1141  or e+114+1 -> e1141
            arabicVerseId = context.getResources().getIdentifier(arabicVerseName,"drawable",context.getPackageName());
            if (banglaTransliterationList.length > 0) {
                v = new Verse(i, arabicVerseId, banglaTransliterationList[i-1], englishTransliterationList[i-1]);
                this.verses.add(v);
            }
            else{
                v = new Verse(i, arabicVerseId, " "," ");
                this.verses.add(v);
            }
        }
    }
}
