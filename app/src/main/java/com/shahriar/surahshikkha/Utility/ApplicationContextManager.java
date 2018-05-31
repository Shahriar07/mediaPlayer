package com.shahriar.surahshikkha.Utility;

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
        if (context != null)
            instance.context = context.getApplicationContext();
        return instance;
    }

    private ApplicationContextManager(Context context){
        if (context != null)
            this.context = context.getApplicationContext();
    }

    public Context getAppContext(){
        return context;
    }
}
