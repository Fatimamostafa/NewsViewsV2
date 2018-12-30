package com.fatimamostafa.app.newsviewsv2.ui.home;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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
public class HomeFragment extends Fragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;


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
        if (adapter == null)
            adapter = new HomeMainAdapter(getActivity(), articlesItemUs, articlesItemTech,
                    new HomeContract.OnClickListener() {
                        @Override
                        public void onNewsArticleClicked(ArticlesItem item) {
                            showDialog(item);
                        }

                        @Override
                        public void onMoreClicked(List<ArticlesItem> articlesItemList, String newsType) {
                            presenter.onMoreClicked(articlesItemList, newsType);
                        }
                    });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorScheme(android.R.color.black,
                android.R.color.black,
                android.R.color.black,
                android.R.color.black);
    }

    private void showDialog(ArticlesItem item) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Display display = ((WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = View.inflate(getActivity(), R.layout.dialog_webview, null);
        dialog.setContentView(view);
        dialog.show();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 5);
        } else {
            dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 4);
        }

        ViewHolder holder = new ViewHolder(view);
        holder.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onNoClicked(item);
                dialog.dismiss();
            }
        });
        holder.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onYesClicked(item);
                dialog.dismiss();
            }
        });
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
        swipeContainer.setRefreshing(false);
        swipeContainer.setVisibility(View.VISIBLE);
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

    @Override
    public void navigateToWebView(ArticlesItem item) {
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(Constants.Intents.ARTICLE, new Gson().toJson(item));
        startActivity(intent);
        Utilities.showForwardTransition(getActivity());
    }

    @Override
    public void navigateToMobileBrowser(ArticlesItem item) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
        startActivity(browserIntent);
    }

    @Override
    public void onRefresh() {
        presenter.getTopUsNews();
    }

    public static class ViewHolder {
        @BindView(R.id.btn_yes)
        TextView btnYes;
        @BindView(R.id.btn_no)
        TextView btnNo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
