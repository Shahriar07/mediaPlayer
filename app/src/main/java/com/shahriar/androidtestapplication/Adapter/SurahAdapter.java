package com.shahriar.androidtestapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahriar.androidtestapplication.Data.Surah;
import com.shahriar.androidtestapplication.Data.Verse;
import com.shahriar.androidtestapplication.R;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class SurahAdapter extends RecyclerView.Adapter {

    private Surah surah;
    Context context;

    public SurahAdapter(Surah surah) {
        this.surah = surah;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class VerseHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView verseNo;
        public TextView surahVersesArabic;
        public TextView surahVersesTranslated;

        public View layout;

        public VerseHolder(View v) {
            super(v);
            layout = v;
            verseNo = (TextView) v.findViewById(R.id.verse_no);
            surahVersesArabic = (TextView) v.findViewById(R.id.arabic_verse);
            surahVersesTranslated = (TextView) v.findViewById(R.id.second_language_verse);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.verse_of_surah, parent, false);
        // set the view's size, margins, paddings and layout parameters
        VerseHolder vh = new VerseHolder(v);
        context = parent.getContext().getApplicationContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VerseHolder verseHolder = (VerseHolder) holder;
        final Verse verse = surah.getVerses().get(position);
        verseHolder.verseNo.setText(context.getString(R.string.surah_and_verse) + " " +verse.getVerseNo());
        verseHolder.surahVersesArabic.setText(verse.getSurahVersesArabic());
        verseHolder.surahVersesTranslated.setText(verse.getSurahVersesTranslated());
    }

    @Override
    public int getItemCount() {
        if (surah != null) {
            return surah.getVerses().size();
        }
        return 0;
    }
}
