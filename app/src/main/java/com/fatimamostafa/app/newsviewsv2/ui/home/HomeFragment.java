package com.fatimamostafa.app.newsviewsv2.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.models.News;
import com.fatimamostafa.app.newsviewsv2.ui.home.adapters.HomeMainAdapter;
import com.fatimamostafa.app.newsviewsv2.ui.listview.ListViewActivity;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View {

    public static final String TAG = "HomeFragment";
    Unbinder unbinder;
    HomeMainAdapter adapter;
    List<ArticlesItem> articlesItemUs = new ArrayList<>();
    List<ArticlesItem> articlesItemTech = new ArrayList<>();
    HomePresenter presenter;
    Disposable disposable;
    boolean dataLoaded;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.rv)
    RecyclerView recyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initPresenter() {
        Log.d(TAG, "initPresenter: ");
        loadingIndicator.show();
        presenter = new HomePresenter();
        presenter.attachView(this);
        presenter.getTopUsNews();

    }

    private void initView() {
        Log.d(TAG, "initView: ");
        adapter = new HomeMainAdapter(getActivity(), articlesItemUs, articlesItemTech,
                new HomeContract.OnClickListener() {
                    @Override
                    public void onNewsArticleClicked(ArticlesItem item) {
                        // showDialog(item);
                    }

                    @Override
                    public void onMoreClicked(List<ArticlesItem> articlesItemList, String newsType) {
                        presenter.onMoreClicked(articlesItemList, newsType);
                    }
                });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @Override
    public void onError(String errorText) {
        loadingIndicator.hide();
        Utilities.showShortToast(getActivity(), errorText);
    }

    @Override
    public void onTechNewsLoaded(News news) {
        loadingIndicator.hide();
        dataLoaded = true;
        recyclerView.setVisibility(View.VISIBLE);
        articlesItemTech.clear();
        articlesItemTech.addAll(news.getArticles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        disposable =
                ReactiveNetwork.observeInternetConnectivity()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean isConnectedToInternet) {
                                        // do something with isConnectedToInternet value
                                        if (isConnectedToInternet && !dataLoaded) {
                                            initPresenter();
                                        }
                                    }
                                });
    }

    @Override
    public void onUsNewsLoaded(News news) {
        articlesItemUs.clear();
        articlesItemUs.addAll(news.getArticles());
        // adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToListView(List<ArticlesItem> articlesItemList, String newsType) {
        Intent intent = new Intent(getActivity(), ListViewActivity.class);
        intent.putExtra(Constants.Intents.ARTICLE_LIST, new Gson().toJson(articlesItemList));
        intent.putExtra(Constants.Intents.NEWS_TYPE, newsType);
        startActivity(intent);
        Utilities.showForwardTransition(getActivity());
    }
}
