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

    public int getIndexForLoop(int currentPosition, int[] array){
        int num = array.length;
        int first = 0;
        int last = num - 1;
        int middle = (first + last)/2;

        while( first <= last )
        {
            if ( array[middle] < currentPosition )
                first = middle + 1;
            else if ( array[middle] == currentPosition )
            {
                System.out.println(currentPosition + " found at location " + (middle + 1) + ".");
                break;
            }
            else
            {
                last = middle - 1;
            }
            middle = (first + last)/2;
        }
        if ( first > last )
            System.out.println(currentPosition + " is not found. Middle " + middle +"\n");
        return middle;
    }
}
