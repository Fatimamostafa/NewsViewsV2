package com.fatimamostafa.app.newsviewsv2.network;

import android.content.Context;

import com.fatimamostafa.app.newsviewsv2.utilities.Constants;
import com.google.gson.GsonBuilder;


import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNewsApiClient {

    private static final String BASE_URL = Constants.API.NEWS_API_BASE_URL;
    private static Retrofit retrofit = null;
    private static GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    private RetrofitNewsApiClient() {

    }

    public static synchronized Retrofit getClient() {
        return retrofit;
    }

    public static synchronized void createClient(final Context context) {

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(cache); // 10 MB
        httpClient.addNetworkInterceptor(new ResponseCacheInterceptor(context));
        httpClient.addInterceptor(new OfflineResponseCacheInterceptor(context));


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(loggingInterceptor);

        OkHttpClient okHttpClient = httpClient.readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(gsonConverterFactory)
                    .build();
        }
    }

}