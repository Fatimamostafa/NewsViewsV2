package com.fatimamostafa.app.newsviewsv2.ui.main;

import android.content.Context;


import com.fatimamostafa.app.newsviewsv2.ui.BasePresenter;

import java.util.List;

public class MainContract {
    interface View {
        void onNavItemPrepared(List<NavItem> navItemList);

        void openFragment(NavItem item);
    }

    interface Presenter extends BasePresenter<View> {
        void prepareNavItems(Context context);

        void onNavItemClicked(NavItem item);
    }

    public interface OnClickListener {
        void onNavItemClicked(NavItem item);
    }

}
