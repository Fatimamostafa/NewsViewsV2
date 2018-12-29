package com.fatimamostafa.app.newsviewsv2.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    public static final String TAG = "SearchActivity";
    boolean isPresenterOn;

    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_search_hint)
    TextView tvSearchHint;
    @BindView(R.id.tv_search_result)
    TextView tvSearchResult;
    SearchContract.Presenter presenter;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        presenter = new SearchPresenter();
        presenter.attachView(this);

        searchFieldListener();
    }

    private void searchFieldListener() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery(query);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }


        });


        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d(TAG, "onClose: ");
                clearSearchSuggestion();
                return true;
            }
        });


    }

    private void searchQuery(String newText) {
        if(!isPresenterOn){
            loadingIndicator.show();
            presenter.search(newText);
            isPresenterOn = true;
        }

    }

    private void clearSearchSuggestion() {
        tvSearchResult.setVisibility(View.GONE);
        tvSearchHint.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onSuccess(String response) {
        isPresenterOn = false;
        loadingIndicator.hide();
        tvSearchResult.setVisibility(View.VISIBLE);
        tvSearchHint.setVisibility(View.GONE);
        tvSearchResult.setText(response);
    }

    @Override
    public void onError(String errorText) {
        isPresenterOn = false;
        loadingIndicator.hide();
        Utilities.showShortToast(this, errorText);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.showBackwardTransition(this);
    }
}
