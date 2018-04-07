package com.shahriar.androidtestapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahriar.androidtestapplication.Data.SurahInfo;
import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.ApplicationContextManager;
import com.shahriar.androidtestapplication.Utility.Utility;

import java.util.ArrayList;

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
        Log.d(getClass().getSimpleName(),"onCreateViewHolder");
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
    Utility utility = new Utility();

    public SurahHolder(View v) {
        super(v);
        surahNo = (TextView) v.findViewById(R.id.surahListSurahNumber);
        surahName = (TextView) v.findViewById(R.id.surahListSurahName);
        surahDuration = (TextView) v.findViewById(R.id.surahDuration);
        Log.d(getClass().getSimpleName(),"SurahHolder");
    }

    public void bindSurah(SurahInfo surahInfo){
        surahNo.setText(ApplicationContextManager.getInstance(null).getAppContext().getString(R.string.surah_number) + " " +surahInfo.getSurahNumber());
        surahName.setText(surahInfo.getSurahName());
        surahDuration.setText(utility.getFormatedTimeFromMilisecond(surahInfo.getSurahDuration()));
    }
}

