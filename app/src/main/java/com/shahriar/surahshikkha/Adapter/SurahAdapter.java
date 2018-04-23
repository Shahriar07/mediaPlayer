package com.shahriar.surahshikkha.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shahriar.surahshikkha.Data.Surah;
import com.shahriar.surahshikkha.Data.Verse;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.Utility;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class SurahAdapter extends RecyclerView.Adapter {

    private ArrayList<Verse> verseArrayList = new ArrayList<>();
    public int selected_position = 0;

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
        View v = inflater.inflate(R.layout.verse_of_surah, parent, false);
        VerseHolder vh = new VerseHolder(v);
        Log.d(getClass().getSimpleName(), "onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VerseHolder verseHolder = (VerseHolder) holder;
        verseHolder.bindVerse(verseArrayList.get(position));
        Log.d(getClass().getSimpleName(), "onBindViewHolder");

        Verse item = verseArrayList.get(position);

        // Here I am just highlighting the background
        if (selected_position == position)
            holder.itemView.setBackgroundResource(R.color.selected_verse_background);
        else
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {
        return verseArrayList.size();
    }

    public void onItemChanged(int position) {
        // Below line is just like a safety check, because sometimes holder could be null,
        // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
        if (position == RecyclerView.NO_POSITION) return;

        // Updating old as well as new positions
        notifyItemChanged(selected_position);
        selected_position = position;
        notifyItemChanged(selected_position);

        // Do your another stuff for your onClick
    }

    class VerseHolder extends RecyclerView.ViewHolder {
        public TextView verseNo;
        public ImageView surahVersesArabic;
        public ImageView surahVersesTranslated;

        public VerseHolder(View v) {
            super(v);
            verseNo = (TextView) v.findViewById(R.id.verse_no);
            surahVersesArabic = (ImageView) v.findViewById(R.id.arabic_verse);
            surahVersesTranslated = (ImageView) v.findViewById(R.id.second_language_verse);
            Log.d(getClass().getSimpleName(), "VerseHolder");
        }

        public void bindVerse(Verse verse) {
            String text = verseNo.getContext().getString(R.string.surah_and_verse) + " " + Utility.getLocalizedInteger(verse.getVerseNo(),null);
            verseNo.setText(text);
            surahVersesArabic.setImageResource(verse.getSurahVersesArabicResourceId());
            surahVersesTranslated.setImageResource(verse.getSurahVersesTranslatedResourceId());
        }
    }
}