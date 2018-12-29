package com.fatimamostafa.app.newsviewsv2.ui.home;

import com.fatimamostafa.app.newsviewsv2.models.News;

public class HomeContract {
    interface View {

        void onError(String errorText);

        void onTechNewsLoaded(News news);

        void onUsNewsLoaded(News news);
    }
    interface Presenter {

        void getTopUsNews();

        void getTechNews();
    }
}
