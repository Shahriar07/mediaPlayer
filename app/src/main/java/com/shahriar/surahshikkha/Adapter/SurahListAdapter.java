package com.shahriar.surahshikkha.Adapter;

import android.content.Context;
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

    private ArrayList<SurahInfo> surahList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    public SurahListAdapter(ArrayList<SurahInfo> surahList, Context context) {
        this.surahList = surahList;
        this.context = context.getApplicationContext();
        inflater = LayoutInflater.from(this.context);
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
}


class SurahHolder extends RecyclerView.ViewHolder {
    public TextView surahNo;
    public TextView surahName;
    public TextView surahDuration;
    public TextView verseCount;
    Utility utility = new Utility();

    public SurahHolder(View v) {
        super(v);
        surahNo = (TextView) v.findViewById(R.id.surahListSurahNumber);
        surahName = (TextView) v.findViewById(R.id.surahListSurahName);
        surahDuration = (TextView) v.findViewById(R.id.surahDuration);
        verseCount = (TextView) v.findViewById(R.id.verseCount);
        Log.d(getClass().getSimpleName(),"SurahHolder");
    }

    public void bindSurah(SurahInfo surahInfo){
        Context context = ApplicationContextManager.getInstance(null).getAppContext();
        String number = Utility.getLocalizedInteger(surahInfo.getSurahNumber(),null);
//        +context.getString(context.getResources().getIdentifier("s"+,"id",context.getPackageName()))
        surahNo.setText(context.getString(R.string.surah_number) + " " +number);
        surahName.setText(surahInfo.getSurahName());
        Locale current = Utility.getCurrentLocale(null);
        surahDuration.setText(utility.getFormatedTimeFromMilisecond(surahInfo.getSurahDuration(),current));
        verseCount.setText(Utility.getLocalizedInteger(surahInfo.getVerseCount(),null));
    }
}

