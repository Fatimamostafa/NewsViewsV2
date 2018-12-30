package com.fatimamostafa.app.newsviewsv2.ui.listview;

import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;

import java.util.List;

public class ListViewContract {
    interface View {

    }
    interface Presenter {

    }
    public interface OnClickListener {
        void onNewsArticleClicked(ArticlesItem item);
    }
}
