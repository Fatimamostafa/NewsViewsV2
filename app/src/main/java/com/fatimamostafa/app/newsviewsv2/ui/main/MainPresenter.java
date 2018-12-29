package com.fatimamostafa.app.newsviewsv2.ui.main;

import android.content.Context;


import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.ui.BasePresenterImpl;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter  extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    @Override
    public void prepareNavItems(Context context) {

        List<NavItem> navItemList = new ArrayList<>();

        navItemList.add(new NavItem(context.getString(R.string.home), R.drawable.ic_home,true, Constants.Fragments.HOME));
        navItemList.add(new NavItem(context.getString(R.string.about), R.drawable.ic_about,false,Constants.Fragments.ABOUT));

        view.openFragment(navItemList.get(0));
        view.onNavItemPrepared(navItemList);

    }

    @Override
    public void onNavItemClicked(NavItem item) {
        view.openFragment(item);
    }
}
