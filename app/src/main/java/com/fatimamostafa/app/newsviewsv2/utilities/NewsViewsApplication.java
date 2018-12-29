package com.fatimamostafa.app.newsviewsv2.utilities;

import android.app.Application;
import android.content.Context;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.network.RetrofitNewsApiClient;
import com.fatimamostafa.app.newsviewsv2.network.RetrofitSearchApiClient;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;


public class NewsViewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitNewsApiClient.createClient(this);
        RetrofitSearchApiClient.createClient(this);

        SharedPreferenceManager.getInstance(this);

        calligraphy();
    }

    private void calligraphy() {

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/serif.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }


}
