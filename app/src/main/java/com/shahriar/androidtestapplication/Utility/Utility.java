package com.shahriar.androidtestapplication.Utility;

import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 2/21/2018.
 */

public class Utility {

    public String getFormatedTimeFromMilisecond (long millisecond){
        Log.d(getClass().getSimpleName(),"Millisecond " + millisecond);
        String time = "00:00:00";
        if (millisecond >= 0) {
            time = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisecond),
                    TimeUnit.MILLISECONDS.toMinutes(millisecond) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecond)),
                    TimeUnit.MILLISECONDS.toSeconds(millisecond) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecond)));
        }
        return time;
    }
}
