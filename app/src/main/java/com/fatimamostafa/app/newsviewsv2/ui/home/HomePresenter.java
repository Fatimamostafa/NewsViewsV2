package com.fatimamostafa.app.newsviewsv2.ui.home;

import com.fatimamostafa.app.newsviewsv2.models.News;
import com.fatimamostafa.app.newsviewsv2.network.Repository;
import com.fatimamostafa.app.newsviewsv2.ui.BasePresenterImpl;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter{
    @Override
    public void getTopUsNews() {
        disposable.add(Repository.getTopUsNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUsNewsLoaded, this::onError));
    }

    private void onUsNewsLoaded(News news) {
        getTechNews();
        view.onUsNewsLoaded(news);
    }

    @Override
    public void getTechNews() {
        disposable.add(Repository.getTechNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onTechNewsLoaded, this::onError));
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
