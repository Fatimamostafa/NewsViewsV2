package com.fatimamostafa.app.newsviewsv2.ui;

public interface BasePresenter<V> {
    public void attachView(V view);

    public void detachView();
}
