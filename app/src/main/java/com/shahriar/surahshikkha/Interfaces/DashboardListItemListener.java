package com.shahriar.surahshikkha.Interfaces;

import com.shahriar.surahshikkha.Data.SurahInfo;

/**
 * Created by H. M. Shahriar on 8/16/2018.
 */
public interface DashboardListItemListener {
    public void playPauseButtonPressed(SurahInfo surahInfo, int position);
    public void listItemPressed(int surahNumber);
}
