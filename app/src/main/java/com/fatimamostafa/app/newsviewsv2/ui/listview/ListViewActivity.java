package com.fatimamostafa.app.newsviewsv2.ui.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ListViewActivity extends AppCompatActivity implements ListViewContract.View {

    @BindView(R.id.tv_news_type)
    TextView tvNewsType;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    List<ArticlesItem> list = new ArrayList<>();
    ListViewAdapter adapter;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);

        getData();

        initRV();
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String articleList = getIntent().getStringExtra(Constants.Intents.ARTICLE_LIST);
            list = new Gson().fromJson(articleList, new TypeToken<List<ArticlesItem>>() {
            }.getType());
            type = getIntent().getStringExtra(Constants.Intents.NEWS_TYPE);
            tvNewsType.setText(type);
        }
    }

    private void initRV() {
        adapter = new ListViewAdapter(this, type, list, new ListViewContract.OnClickListener() {
            @Override
            public void onNewsArticleClicked(ArticlesItem item) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.getItemAnimator().setChangeDuration(0);
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
