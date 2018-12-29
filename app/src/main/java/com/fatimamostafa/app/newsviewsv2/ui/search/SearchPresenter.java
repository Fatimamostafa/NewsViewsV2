package com.fatimamostafa.app.newsviewsv2.ui.search;

import android.util.Log;

import com.fatimamostafa.app.newsviewsv2.network.Repository;
import com.fatimamostafa.app.newsviewsv2.ui.BasePresenterImpl;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class SearchPresenter extends BasePresenterImpl<SearchContract.View> implements SearchContract.Presenter {
    public static final String TAG = "SearchPresenter";

    @Override
    public void search(String searchText) {
        if(searchText.contains("/")) {
            int pos = searchText.indexOf("/");
            String month = searchText.substring(0, pos);
            String day = searchText.substring(pos+1, searchText.length());

            disposable.add(Repository.getDateResponse(month, day)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onError));
        }
        else {
            disposable.add(Repository.getNumberResponse(searchText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onError));
        }
    }

    private void onSuccess(ResponseBody responseBody) {
        try {
            view.onSuccess( responseBody.string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void onError(Throwable throwable) {
        if (throwable instanceof IOException) {
            //view.onError("Please check your internet connection.");
        } else if (throwable instanceof HttpException) {
            try {
                String errorText = ((HttpException) throwable).response().errorBody().string();
                view.onError(errorText);

            } catch (Exception e) {
                e.printStackTrace();
                view.onError("Please try again later." + throwable.getMessage());
            }
        } else {
            view.onError("Please try again later." + throwable.getMessage());

        }
    }
}
