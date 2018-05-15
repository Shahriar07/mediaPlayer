package com.shahriar.surahshikkha.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahriar.surahshikkha.Data.SurahInfo;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.ApplicationContextManager;
import com.shahriar.surahshikkha.Utility.Utility;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class SurahListAdapter extends RecyclerView.Adapter {

    public void setSurahList(ArrayList<SurahInfo> surahList) {
        this.surahList = surahList;
    }

    private ArrayList<SurahInfo> surahList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    public SurahListAdapter(ArrayList<SurahInfo> surahList, Context context) {
        this.surahList = surahList;
        this.context = context.getApplicationContext();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.dashboard_surah_list_item, parent, false);
        SurahHolder vh = new SurahHolder(v);
        //Log.d(getClass().getSimpleName(),"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SurahHolder surahHolder = (SurahHolder) holder;
        surahHolder.bindSurah(surahList.get(position));
        Log.d(getClass().getSimpleName(),"onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return surahList.size();
    }



class SurahHolder extends RecyclerView.ViewHolder {
    public TextView surahNo;
    public TextView surahName;
    public TextView surahNameSecondary;
    public TextView surahDuration;
    public TextView verseCount;
    Utility utility = new Utility();
    String surahNumberHeader;
    String durationTextHeader;
    String verseTextHeader;

    public SurahHolder(View v) {
        super(v);
        Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.solaimanlipi);
        surahNo = (TextView) v.findViewById(R.id.surahListSurahNumber);
        surahName = (TextView) v.findViewById(R.id.surahListSurahName);
        surahName.setTypeface(typeface);
        surahNameSecondary = (TextView) v.findViewById(R.id.surahListSurahNameSecondary);
        surahNameSecondary.setTypeface(typeface);
        surahDuration = (TextView) v.findViewById(R.id.surahDuration);
        verseCount = (TextView) v.findViewById(R.id.verseCount);
        surahNumberHeader = v.getContext().getString(R.string.surah_number);
        verseTextHeader = v.getContext().getString(R.string.verses);
        durationTextHeader = v.getContext().getString(R.string.duration);
        Log.d(getClass().getSimpleName(),"SurahHolder");
    }

    public void bindSurah(SurahInfo surahInfo){
        String number = surahNumberHeader + " " +Utility.getLocalizedInteger(surahInfo.getSurahNumber(),null);
        surahNo.setText(number);
        Locale locale = Utility.getCurrentLocale(context);
        if (Locale.ENGLISH == locale || locale.getLanguage().equals("en")){
            surahName.setText(surahInfo.getSurahName());
            String secondaryName = "(" + surahInfo.getSurahNameSecondary() + ")";
            surahNameSecondary.setText(secondaryName);
        }
        else {
            surahName.setText(surahInfo.getSurahNameSecondary());
            String secondaryName = "(" + surahInfo.getSurahName() + ")";
            surahNameSecondary.setText(secondaryName);
        }
        Locale current = Utility.getCurrentLocale(null); // Null will return english locale
        String durationtext = durationTextHeader + " " + utility.getFormatedTimeFromMilisecond(surahInfo.getSurahDuration(),current);
        surahDuration.setText(durationtext);
        String verseText =  verseTextHeader + " " +Utility.getLocalizedInteger(surahInfo.getVerseCount(),null);
        verseCount.setText(verseText);
    }
}
}
