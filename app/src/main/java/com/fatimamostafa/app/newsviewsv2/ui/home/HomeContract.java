package com.fatimamostafa.app.newsviewsv2.ui.home;

import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.models.News;

import java.util.List;

public class HomeContract {
    interface View {

        void onError(String errorText);

        void onTechNewsLoaded(News news);

        void onUsNewsLoaded(News news);

        void navigateToListView(List<ArticlesItem> articlesItemList, String newsType);

        void navigateToWebView(ArticlesItem item);

        void navigateToMobileBrowser(ArticlesItem item);
    }
    interface Presenter {

        void getTopUsNews();

        void getTechNews();

        void onMoreClicked(List<ArticlesItem> articlesItemList, String newsType);

        void onNoClicked(ArticlesItem item);

        void onYesClicked(ArticlesItem item);
    }
    public interface OnClickListener {
        void onNewsArticleClicked(ArticlesItem item);
        void onMoreClicked(List<ArticlesItem> articlesItemList, String newsType);
    }
}
