package com.fatimamostafa.app.newsviewsv2.ui.listview;

import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;

import java.util.List;

public class ListViewContract {
    interface View {

        void navigateToWebView(ArticlesItem item);

        void navigateToMobileBrowser(ArticlesItem item);
    }
    interface Presenter {

        void onNoClicked(ArticlesItem item);

        void onYesClicked(ArticlesItem item);
    }
    public interface OnClickListener {
        void onNewsArticleClicked(ArticlesItem item);
    }
}
