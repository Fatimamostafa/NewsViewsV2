package com.fatimamostafa.app.newsviewsv2.ui.search;

import com.fatimamostafa.app.newsviewsv2.ui.BasePresenter;

public class SearchContract {
    interface View {

        void onSuccess(String response);

        void onError(String errorText);
    }

    interface Presenter extends BasePresenter<View> {

        void search(String newText);
    }

}
