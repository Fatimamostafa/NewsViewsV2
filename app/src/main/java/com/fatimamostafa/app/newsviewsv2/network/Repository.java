package com.fatimamostafa.app.newsviewsv2.network;


import com.fatimamostafa.app.newsviewsv2.models.News;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    private static APIService getNewsClient() {
        return RetrofitNewsApiClient.getClient().create(APIService.class);
    }

    private static APIService getNumbersClient() {
        return RetrofitSearchApiClient.getClient().create(APIService.class);
    }


    public static Flowable<ResponseBody> getNumberResponse(String numbers) {
        return getNumbersClient().getNumberResponse(numbers);
    }

    public static Flowable<ResponseBody> getDateResponse(String month, String day) {
        return getNumbersClient().getDateResponse(month, day);
    }

    public static Flowable<News> getTopUsNews() {
        return getNewsClient().getTopUsNews("us", "business", Constants.API.NEWS_API_KEY);
    }

    public static Flowable<News> getTechNews() {
        return getNewsClient().getTechNews("techcrunch", Constants.API.NEWS_API_KEY);
    }
}
