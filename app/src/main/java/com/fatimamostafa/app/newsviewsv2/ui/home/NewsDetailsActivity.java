package com.fatimamostafa.app.newsviewsv2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView webView;

    ArticlesItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        getData();
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String article = getIntent().getStringExtra(Constants.Intents.ARTICLE);
            item = new Gson().fromJson(article, new TypeToken<ArticlesItem>() {
            }.getType());
        }

        webView.loadUrl(item.getUrl());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.showBackwardTransition(this);
    }
}
