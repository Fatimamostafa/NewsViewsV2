package com.fatimamostafa.app.newsviewsv2.utilities;

import android.app.Application;

public class NewsViewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceManager.getInstance(this);
    }
}
