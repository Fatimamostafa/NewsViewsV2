package com.fatimamostafa.app.newsviewsv2.ui.listview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.ui.home.HomeFragment;
import com.fatimamostafa.app.newsviewsv2.ui.home.NewsDetailsActivity;
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
    ListViewPresenter presenter;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);

        presenter = new ListViewPresenter();
        presenter.attachView(this);

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
                showAlertDialog(item);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.getItemAnimator().setChangeDuration(0);
    }

    private void showAlertDialog(ArticlesItem item) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = View.inflate(this, R.layout.dialog_webview, null);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.showBackwardTransition(this);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void navigateToWebView(ArticlesItem item) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(Constants.Intents.ARTICLE, new Gson().toJson(item));
        startActivity(intent);
        Utilities.showForwardTransition(this);
    }

    @Override
    public void navigateToMobileBrowser(ArticlesItem item) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
        startActivity(browserIntent);
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
