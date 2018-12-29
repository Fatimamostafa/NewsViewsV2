package com.fatimamostafa.app.newsviewsv2.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;

public class ResponseCacheInterceptor implements Interceptor {
    private Context context;

    public ResponseCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60 * 5) // 5 minutes
                .build();
    }
}