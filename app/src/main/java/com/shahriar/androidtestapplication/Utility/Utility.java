package com.shahriar.androidtestapplication.Utility;

import android.util.Log;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by H. M. Shahriar on 2/21/2018.
 */

public class Utility {

    public String getFormatedTimeFromMilisecond (long millisecond, Locale locale){
        Log.d(getClass().getSimpleName(),"Millisecond " + millisecond);
        String time = String.format(locale,"%02d:%02d",0,0);
        if (millisecond >=3600000){
            time = String.format(locale,"%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisecond),
                    TimeUnit.MILLISECONDS.toMinutes(millisecond) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecond)),
                    TimeUnit.MILLISECONDS.toSeconds(millisecond) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecond)));
        }
        else if (millisecond > 0) {
            time = String.format(locale,"%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisecond),
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

    public Integer[] getIntArray(int startRange, int endRange){
        int duration = Math.abs(endRange-startRange);
        Log.d(getClass().getSimpleName(),"Duration "+duration + " start " + startRange + " End " + endRange);
        Integer[] intArray = new Integer[duration+1];
        if (endRange > startRange){
            for (int i = 0;i <= duration; ++i,++startRange){
                intArray[i] = startRange;
            }
        }
        else {
            for (int i = 0;i <= duration; ++i,--startRange){
                intArray[i] = startRange;
            }
        }
        return intArray;
    }
}
