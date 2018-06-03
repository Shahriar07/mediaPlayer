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
import com.shahriar.surahshikkha.Utility.Utility;

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
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.dashboard_surah_list_item, parent, false);
        SurahHolder vh = new SurahHolder(v);
        //Log.d(getClass().getSimpleName(),"onCreateViewHolder");
        return vh;
    }

    public void setContext(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SurahHolder surahHolder = (SurahHolder) holder;
        surahHolder.bindSurah(surahList.get(position));
        Log.d(getClass().getSimpleName(),"onBindViewHolder");
        holder.itemView.setBackgroundResource(R.drawable.surah_verse_border);
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
        surahNo.setTypeface(typeface);
        surahName = (TextView) v.findViewById(R.id.surahListSurahName);
        surahName.setTypeface(typeface);
        surahNameSecondary = (TextView) v.findViewById(R.id.surahListSurahNameSecondary);
        surahNameSecondary.setTypeface(typeface);
        surahDuration = (TextView) v.findViewById(R.id.surahDuration);
        surahDuration.setTypeface(typeface);
        verseCount = (TextView) v.findViewById(R.id.verseCount);
        verseCount.setTypeface(typeface);
        Log.d(getClass().getSimpleName(),"SurahHolder");
    }

    public void bindSurah(SurahInfo surahInfo){
        surahNumberHeader =context.getString(R.string.surah_number);
        verseTextHeader = context.getString(R.string.verses);
        durationTextHeader = context.getString(R.string.duration);
        Locale current = Utility.getCurrentLocale(context); // Null will return english locale
        //Locale current = Utility.getCurrentLocale(null); // Null will return english locale
        String number = surahNumberHeader + " " +Utility.getLocalizedInteger(surahInfo.getSurahNumber(),current);
        surahNo.setText(number);
        surahName.setText(surahInfo.getSurahName());
        String secondaryName = "(" + surahInfo.getSurahNameSecondary() + ")";
        surahNameSecondary.setText(secondaryName);


        String durationtext = durationTextHeader + " " + utility.getFormatedTimeFromMilisecond(surahInfo.getSurahDuration(),current);
        surahDuration.setText(durationtext);
        String verseText =  verseTextHeader + " " +Utility.getLocalizedInteger(surahInfo.getVerseCount(),current);
        verseCount.setText(verseText);
    }
}
}
