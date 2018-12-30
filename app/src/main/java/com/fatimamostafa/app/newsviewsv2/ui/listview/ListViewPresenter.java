package com.fatimamostafa.app.newsviewsv2.ui.listview;

import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.ui.BasePresenterImpl;

public class ListViewPresenter extends BasePresenterImpl<ListViewContract.View> implements ListViewContract.Presenter {
    @Override
    public void onNoClicked(ArticlesItem item) {
        view.navigateToWebView(item);
    }

    @Override
    public void onYesClicked(ArticlesItem item) {
        view.navigateToMobileBrowser(item);
    }
}
