package com.shahriar.surahshikkha.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shahriar.surahshikkha.Data.SurahInfo;
import com.shahriar.surahshikkha.Interfaces.DashboardListItemListener;
import com.shahriar.surahshikkha.Interfaces.DashboardListItemUpdateListener;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class SurahListAdapter extends RecyclerView.Adapter implements Filterable {

    private Context context;
    private LayoutInflater inflater;
    private DashboardListItemListener listItmeListener;
    private ArrayList<SurahInfo> surahList;
    private ArrayList<SurahInfo> filteredData;
    private ItemFilter mFilter = new ItemFilter();
    DashboardListItemUpdateListener updateListener;
    int updatePosition = 0;

    public SurahListAdapter(ArrayList<SurahInfo> surahList, Context context, DashboardListItemListener listItemListener) {
        this.surahList = surahList;
        this.filteredData = surahList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        listItmeListener = listItemListener;
    }

    public void setSurahList(ArrayList<SurahInfo> surahList) {
        this.surahList = surahList;
        this.filteredData = surahList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(getClass().getSimpleName(), "onCreateViewHolder");
        View v = inflater.inflate(R.layout.dashboard_surah_list_item, parent, false);
        SurahHolder vh = new SurahHolder(v);
        Log.d(getClass().getSimpleName(),"onCreateViewHolder");
        return vh;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(getClass().getSimpleName(), "onBindViewHolder");
        SurahHolder surahHolder = (SurahHolder) holder;
        surahHolder.bindSurah(filteredData.get(position), position);
        holder.itemView.setBackgroundResource(R.drawable.surah_verse_border);
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public ArrayList<SurahInfo> getFilteredData() {
        return filteredData;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<SurahInfo> list = surahList;

            int count = list.size();
            final ArrayList<SurahInfo> nlist = new ArrayList<SurahInfo>(count);

            SurahInfo filterableSurahInfo;

            for (int i = 0; i < count; i++) {
                filterableSurahInfo = list.get(i);
                if (filterableSurahInfo.getSurahName().contains(filterString) || filterableSurahInfo.getSurahNameSecondary().contains(filterString)) {
                    nlist.add(filterableSurahInfo);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<SurahInfo>) results.values;
            notifyDataSetChanged();
        }

    }

    class SurahHolder extends RecyclerView.ViewHolder {
        public TextView surahNo;
        public TextView surahName;
        public TextView surahNameSecondary;
        public TextView surahDuration;
        public TextView verseCount;
        public ProgressBar progressBar;
        public Button playPauseButton;
        Utility utility = new Utility();
        String surahNumberHeader;
        String durationTextHeader;
        String verseTextHeader;
        RelativeLayout surahInformation;
        boolean playingState;

        public SurahHolder(View v) {
            super(v);
            Log.d(getClass().getSimpleName(), "SurahHolder");
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
            progressBar = (ProgressBar) v.findViewById(R.id.playProgressBar);
            playPauseButton = (Button) v.findViewById(R.id.playPauseButton);
            surahInformation = (RelativeLayout) v.findViewById(R.id.SurahInformation);
            playingState = false;
        }

        public void bindSurah(final SurahInfo surahInfo,final int position) {
            Log.d(getClass().getSimpleName(),"BindSurah called for possition " + position + " surah Info " + surahInfo.getSurahNumber());
            surahNumberHeader = context.getString(R.string.surah_number);
            verseTextHeader = context.getString(R.string.verses);
            durationTextHeader = context.getString(R.string.duration);
            Locale current = Utility.getCurrentLocale(context); // Null will return english locale
            //Locale current = Utility.getCurrentLocale(null); // Null will return english locale
            String number = surahNumberHeader + " " + Utility.getLocalizedInteger(surahInfo.getSurahNumber(), current);
            surahNo.setText(number);
            surahName.setText(surahInfo.getSurahName());
            String secondaryName = "(" + surahInfo.getSurahNameSecondary() + ")";
            surahNameSecondary.setText(secondaryName);


            String durationtext = durationTextHeader + " " + utility.getFormatedTimeFromMilisecond(surahInfo.getSurahDuration(), current);
            surahDuration.setText(durationtext);
            String verseText = verseTextHeader + " " + Utility.getLocalizedInteger(surahInfo.getVerseCount(), current);
            verseCount.setText(verseText);

            // progressbar
            progressBar.setMax(100);
            progressBar.setProgress(surahInfo.getAudioPercent());
            if (surahInfo.isPlaying()) {
                Log.d(getClass().getSimpleName(), "Set Button to pause " + surahInfo.getSurahNumber());
                playPauseButton.setBackgroundResource(R.drawable.rounded_button_background_pause);
                playingState = true;
            }
            else {
                Log.d(getClass().getSimpleName(), "Set Button to play " + surahInfo.getSurahNumber());
                playPauseButton.setBackgroundResource(R.drawable.rounded_play_button_background);
                playingState = false;
            }

            playPauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(getClass().getSimpleName(), "******************* PlayPause button clicked *********************");
                    updatePlayPauseButton(surahInfo,position);
                }
            });
            if (surahInformation != null) {
                surahInformation.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (listItmeListener != null)
                            listItmeListener.listItemPressed(surahInfo.getSurahNumber());
                    }
                });
            }
            if (updateListener != null) {
                updateListener.listItemUpdated(updatePosition);
                updatePosition = 0;
                updateListener = null;
            } else {
                Log.d(getClass().getSimpleName(), "Update Listener null");
            }
        }


        public void updatePlayPauseButton(final SurahInfo surahInfo,final int possition){
            if (listItmeListener != null) {
                Log.d(getClass().getSimpleName(), "Playing State " + playingState + " Surah Number " + surahInfo.getSurahNumber() + " Possition " + possition);
                if (playingState)
                {
                    playPauseButton.setBackgroundResource(R.drawable.rounded_play_button_background);
                }
                else{
                    playPauseButton.setBackgroundResource(R.drawable.rounded_button_background_pause);
                }
                playingState = !playingState;
                surahInfo.setPlaying(playingState);
                listItmeListener.playPauseButtonPressed(surahInfo, possition);
            }
        }
    }

    // Update the view of single item from list which holds the surahinfo
    public synchronized void refresh(int position, SurahInfo item, DashboardListItemUpdateListener updateListener){
        this.updateListener = updateListener;
        if (this.filteredData.size() > position) {
            this.filteredData.set(position, item);
            Log.i(getClass().getSimpleName(), " Refresh list item with playing status " + item.isPlaying() + " position " + position);
            this.updatePosition = position;
            notifyItemChanged(position);
        }
    }
}
