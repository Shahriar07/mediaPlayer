package com.shahriar.androidtestapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shahriar.androidtestapplication.Data.Surah;
import com.shahriar.androidtestapplication.Data.Verse;
import com.shahriar.androidtestapplication.R;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class SurahAdapter extends RecyclerView.Adapter {

    private ArrayList<Verse> verseArrayList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    public SurahAdapter(Surah surah, Context context) {
        if (surah != null)
        verseArrayList = surah.getVerses();
        this.context = context.getApplicationContext();
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = inflater.inflate(R.layout.verse_of_surah, parent, false);
        // set the view's size, margins, paddings and layout parameters
        VerseHolder vh = new VerseHolder(v);
        Log.d(getClass().getSimpleName(),"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VerseHolder verseHolder = (VerseHolder) holder;
        verseHolder.bindVerse(verseArrayList.get(position));
        Log.d(getClass().getSimpleName(),"onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return verseArrayList.size();
    }
}


class VerseHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView verseNo;
    public TextView surahVersesArabic;
//    public TextView surahVersesTranslated;
    public ImageView surahVersesTranslated;

//        public View layout;

    public VerseHolder(View v) {
        super(v);
//            layout = v;
        verseNo = (TextView) v.findViewById(R.id.verse_no);
        surahVersesArabic = (TextView) v.findViewById(R.id.arabic_verse);
        surahVersesArabic.setTextDirection(View.TEXT_DIRECTION_RTL);
        surahVersesTranslated = (ImageView) v.findViewById(R.id.second_language_verse);
        Log.d(getClass().getSimpleName(),"VerseHolder");

    }

    public void bindVerse(Verse verse){
        verseNo.setText(verseNo.getContext().getString(R.string.surah_and_verse) + " " +verse.getVerseNo());
        surahVersesArabic.setText(verse.getSurahVersesArabic());
        surahVersesTranslated.setImageResource(verse.getSurahVersesTranslatedResourceId());
    }


}

