package com.shahriar.androidtestapplication.Interfaces;

import android.view.View;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public interface OnRecycleViewClicked {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}