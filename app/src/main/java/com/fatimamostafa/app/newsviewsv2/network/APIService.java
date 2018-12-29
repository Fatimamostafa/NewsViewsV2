package com.fatimamostafa.app.newsviewsv2.network;

import android.media.Image;

import com.fatimamostafa.app.newsviewsv2.models.News;

import java.util.List;


import io.reactivex.Flowable;


import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("{month}/{day}/date")
    Flowable<ResponseBody> getDateResponse(@Path("month") String month,
                                       @Path("day") String date);

    @GET("{number}")
    Flowable<ResponseBody> getNumberResponse(@Path("number") String number);

    @GET("top-headlines")
    Flowable<News> getTopUsNews(@Query("country") String country,
                               @Query("category") String category,
                               @Query("apiKey") String key);

    @GET("top-headlines")
    Flowable<News> getTechNews(@Query("sources") String country,
                                @Query("apiKey") String key);


}
