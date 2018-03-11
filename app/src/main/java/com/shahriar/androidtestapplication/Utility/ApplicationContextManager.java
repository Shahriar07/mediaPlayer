package com.shahriar.androidtestapplication.Utility;

import android.content.Context;

/**
 * Created by H. M. Shahriar on 3/10/2018.
 */

public class ApplicationContextManager {
    private static ApplicationContextManager instance = null;
    Context context;

    public static ApplicationContextManager getInstance (Context context){
        if (instance == null){
            synchronized (ApplicationContextManager.class){
                if (instance == null){
                    instance = new ApplicationContextManager(context);
                }
            }
        }
        return instance;
    }

    ApplicationContextManager(Context context){
        this.context = context.getApplicationContext();
    }

    public Context getAppContext(){
        return context;
    }
}
