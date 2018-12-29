package com.fatimamostafa.app.newsviewsv2.network;

import java.util.List;


import io.reactivex.Flowable;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("{month}/{day}/date")
    Flowable<ResponseBody> getDateResponse(@Path("month") String month,
                                       @Path("day") String date);

    @GET("{number}")
    Flowable<ResponseBody> getNumberResponse(@Path("number") String number);

}
