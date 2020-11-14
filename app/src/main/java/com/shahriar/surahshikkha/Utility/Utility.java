package com.shahriar.surahshikkha.Utility;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.res.ResourcesCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shahriar.surahshikkha.R;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by H. M. Shahriar on 2/21/2018.
 */

public class Utility {

    public String getFormatedTimeFromMilisecond (long millisecond, Locale locale){
        //Log.d(getClass().getSimpleName(),"Millisecond " + millisecond);
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
//                Log.d(getClass().getSimpleName(),currentPosition + " found at index " +middle);
                break;
            }
            else
            {
                last = middle - 1;
            }
            middle = (first + last)/2;
        }
//        if ( first > last )
           //Log.d(getClass().getSimpleName(),currentPosition + " is not found. Middle " + middle);
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
    public String[] getStringArray(int startRange, int endRange, Locale locale){
        int duration = Math.abs(endRange-startRange);
        Log.d(getClass().getSimpleName(),"Duration "+duration + " start " + startRange + " End " + endRange);
        String[] intArray = new String[duration+1];
        if (endRange > startRange){
            if (locale == null){
                locale = Locale.ENGLISH;
            }
            for (int i = 0;i <= duration; ++i,++startRange){
                intArray[i] = NumberFormat.getInstance(locale).format(startRange);
            }
        }
        else {
            if (locale == null){
                locale = Locale.ENGLISH;
            }
            for (int i = 0;i <= duration; ++i,--startRange){
                intArray[i] = NumberFormat.getInstance(locale).format(startRange);
            }
        }
        return intArray;
    }


    public static String getLanguageText(int selectedLanguage) {
        switch (selectedLanguage){
            case Constants.LANGUAGE_ENGLISH_VALUE:
            {
                return Constants.LANGUAGE_ENGLISH;
            }
            case Constants.LANGUAGE_BANGLA_VALUE:
            {
                return Constants.LANGUAGE_BANGLA;
            }
            default:{
                return Constants.LANGUAGE_ENGLISH;
            }
        }
    }

    public static String getCountry(int selectedLanguage) {
        switch (selectedLanguage){
            case Constants.LANGUAGE_ENGLISH_VALUE:
            {
                return Locale.US.getCountry();
            }
            case Constants.LANGUAGE_BANGLA_VALUE:
            {
                return "";
            }
            default:{
                return Locale.US.getCountry();
            }
        }
    }

    public static String getLanguage(int selectedLanguage) {
        switch (selectedLanguage){
            case Constants.LANGUAGE_ENGLISH_VALUE:
            {
                return Locale.US.getLanguage();
            }
            case Constants.LANGUAGE_BANGLA_VALUE:
            {
                return "bn";
            }
            default:{
                return Locale.US.getLanguage();
            }
        }
    }
    public static String getLocalizedInteger(int maxRepeatCount, Locale locale ) {
//        return  NumberFormat.getInstance(locale).format(maxRepeatCount);
        if (locale != null){
            return NumberFormat.getInstance(locale).format(maxRepeatCount);
        }
        else {
            return NumberFormat.getInstance(Locale.ENGLISH).format(maxRepeatCount);
        }
    }

    /*
     * get the current locale of phone
     * pass null as context will return English locale
     */
    public static Locale getCurrentLocale(Context context)
    {
        Locale current = Locale.ENGLISH;
        if (context != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return context.getResources().getConfiguration().getLocales().get(0);
            } else {
                //noinspection deprecation
                return context.getResources().getConfiguration().locale;
            }
        }
        return current;
    }


    public void rateUs(Context context){
        if (context == null)
            return;
        Uri uri = Uri.parse("market://details?id=" + context.getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName())));
        }
    }

    public static Typeface getTypeFace(Context context)
    {
        return ResourcesCompat.getFont(context, R.font.solaimanlipi);
    }

    public static void  showCustomToast(Context context, String text, int duration){
        Toast toast = Toast.makeText(context, text , duration);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTypeface(Utility.getTypeFace(context));
        toast.show();
    }

    public static int getRawFileFromSurahNumber(int number){
        switch (number)
        {
            case 1:
                return R.raw.s_1;
            case 86:
                return R.raw.s_86;
            case 87:
                return R.raw.s_87;
            case 88:
                return R.raw.s_88;
            case 89:
                return R.raw.s_89;
            case 90:
                return R.raw.s_90;
            case 91:
                return R.raw.s_91;
            case 92:
                return R.raw.s_92;
            case 93:
                return R.raw.s_93;
            case 94:
                return R.raw.s_94;
            case 95:
                return R.raw.s_95;
            case 96:
                return R.raw.s_96;
            case 97:
                return R.raw.s_97;
            case 98:
                return R.raw.s_98;
            case 99:
                return R.raw.s_99;
            case 100:
                return R.raw.s_100;
            case 101:
                return R.raw.s_101;
            case 102:
                return R.raw.s_102;
            case 103:
                return R.raw.s_103;
            case 104:
                return R.raw.s_104;
            case 105:
                return R.raw.s_105;
            case 106:
                return R.raw.s_106;
            case 107:
                return R.raw.s_107;
            case 108:
                return R.raw.s_108;
            case 109:
                return R.raw.s_109;
            case 110:
                return R.raw.s_110;
            case 111:
                return R.raw.s_111;
            case 112:
                return R.raw.s_112;
            case 113:
                return R.raw.s_113;
            case 114:
                return R.raw.s_114;
        }
        return 0;
    }
}
