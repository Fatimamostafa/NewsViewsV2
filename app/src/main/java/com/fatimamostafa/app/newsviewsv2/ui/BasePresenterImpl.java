package com.fatimamostafa.app.newsviewsv2.ui;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenterImpl<V> {

    protected V view;
    protected CompositeDisposable disposable = new CompositeDisposable();

    public void attachView(V view) {
        this.view = view;

    }

    public void detachView() {
        view = null;
        disposable.dispose();
    }
}
