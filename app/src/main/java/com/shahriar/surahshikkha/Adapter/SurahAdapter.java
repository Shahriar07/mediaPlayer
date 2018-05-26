package com.shahriar.surahshikkha.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
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
import com.shahriar.surahshikkha.Utility.Constants;
import com.shahriar.surahshikkha.Utility.SharedPreferenceController;
import com.shahriar.surahshikkha.Utility.Utility;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
            holder.itemView.setBackgroundResource(R.drawable.surah_verse_border_selected);
        else
            holder.itemView.setBackgroundResource(R.drawable.surah_verse_border);
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
        //public TextView banglaTransliteration;
        public TextView banglaTranslation;
        public TextView englishTranslation;
        SharedPreferenceController controller;

        public VerseHolder(View v) {
            super(v);
            verseNo = (TextView) v.findViewById(R.id.verse_no);
            Typeface typeface = ResourcesCompat.getFont(context, R.font.solaimanlipi);
            verseNo.setTypeface(typeface);
            //banglaTransliteration = (TextView) v.findViewById(R.id.verse_transliteration_in_bangla);
            // banglaTransliteration.setTypeface(typeface);

            controller = new SharedPreferenceController(context);
            banglaTranslation = (TextView) v.findViewById(R.id.verse_translation_in_bangla);
            banglaTranslation.setTypeface(typeface);
            englishTranslation = (TextView) v.findViewById(R.id.verse_translation_in_english);
            surahVersesArabic = (ImageView) v.findViewById(R.id.arabic_verse);
            Log.d(getClass().getSimpleName(), "VerseHolder");
        }

        public void bindVerse(Verse verse) {
            banglaTranslation.setText(verse.getVerseTranslationInBangla());
            Locale currentLocale = Utility.getCurrentLocale(context);
            String text = verseNo.getContext().getString(R.string.surah_and_verse) + " " + Utility.getLocalizedInteger(verse.getVerseNo(),currentLocale);
            verseNo.setText(text);
            surahVersesArabic.setImageResource(verse.getSurahVersesArabicResourceId());
//            banglaTransliteration.setText(verse.getVerseTransliterationInBangla());

            if(controller.readBooleanWithKey(Constants.MENU_ENGLISH_TRANSLATION)){
                englishTranslation.setText(verse.getVerseTranslationInEnglish());
                englishTranslation.setVisibility(View.VISIBLE);
            }
            else
            {
                englishTranslation.setVisibility(View.GONE);
            }
            if(controller.readBooleanWithKey(Constants.MENU_BANGLA_TRANSLATION)){
                banglaTranslation.setText(verse.getVerseTranslationInBangla());
                banglaTranslation.setVisibility(View.VISIBLE);
            }
            else
            {
                banglaTranslation.setVisibility(View.GONE);
            }
        }
    }
}
