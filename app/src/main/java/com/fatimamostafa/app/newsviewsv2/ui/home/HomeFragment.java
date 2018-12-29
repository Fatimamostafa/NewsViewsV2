package com.fatimamostafa.app.newsviewsv2.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.models.News;
import com.fatimamostafa.app.newsviewsv2.ui.home.adapters.HomeMainAdapter;
import com.fatimamostafa.app.newsviewsv2.ui.home.adapters.NewsAdapter;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View {


    @BindView(R.id.rv)
    RecyclerView recyclerView;
    Unbinder unbinder;
    HomeMainAdapter adapter;
    List<ArticlesItem> articlesItemUs = new ArrayList<>();
    List<ArticlesItem> articlesItemTech = new ArrayList<>();
    HomePresenter presenter;

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
        initPresenter();

        return view;
    }

    private void initPresenter() {
        presenter = new HomePresenter();
        presenter.attachView(this);
        presenter.getTopUsNews();
        presenter.getTechNews();
    }

    private void initView() {
        adapter = new HomeMainAdapter(getActivity(), articlesItemUs, articlesItemTech);
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
        Utilities.showShortToast(getActivity(), errorText);
    }

    @Override
    public void onTechNewsLoaded(News news) {
        articlesItemTech.clear();
        articlesItemTech.addAll(news.getArticles());

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUsNewsLoaded(News news) {
        articlesItemUs.clear();
        articlesItemUs.addAll(news.getArticles());

        adapter.notifyDataSetChanged();
    }
}
