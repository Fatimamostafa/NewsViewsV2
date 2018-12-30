package com.fatimamostafa.app.newsviewsv2.ui.home.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.ui.home.HomeContract;
import com.fatimamostafa.app.newsviewsv2.utilities.ItemDecorationTop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fatimamostafa.app.newsviewsv2.utilities.Utilities.dip2px;

public class HomeMainAdapter extends RecyclerView.Adapter {

    Context context;
    List<ArticlesItem> articlesItemUs;
    List<ArticlesItem> articlesItemTech;
    ItemDecorationTop itemDecorationTop;
    private int VIEW_TOP_US = 1;
    private int VIEW_TOP_TECH = 2;
    private HomeContract.OnClickListener callback;


    public HomeMainAdapter(Context context, List<ArticlesItem> articlesItemUs, List<ArticlesItem> articlesItemTech, HomeContract.OnClickListener callback) {
        this.context = context;
        this.articlesItemUs = articlesItemUs;
        this.articlesItemTech = articlesItemTech;
        this.callback = callback;
        itemDecorationTop = new ItemDecorationTop(dip2px(20, context));
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TOP_US) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_news_item, parent, false);
            return new ViewHolderUS(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_tech_item, parent, false);
            return new ViewHolderTech(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderUS) {
            prepareUSViewHolder((ViewHolderUS) holder, position);
        } else if (holder instanceof ViewHolderTech) {
            prepareTechViewHolder((ViewHolderTech) holder, position);
        }
    }

    private void prepareTechViewHolder(ViewHolderTech holder, int position) {
        holder.adapter = new TechAdapter(context, articlesItemTech, callback);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(holder.adapter);
    }

    private void prepareUSViewHolder(ViewHolderUS holder, int position) {
        holder.adapter = new NewsAdapter(context, articlesItemUs, callback);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.removeItemDecoration(itemDecorationTop);
        holder.recyclerView.addItemDecoration(itemDecorationTop);
        holder.recyclerView.setAdapter(holder.adapter);
        holder.recyclerView.getItemAnimator().setChangeDuration(0);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TOP_US;
        } else {
            return VIEW_TOP_TECH;
        }
    }

    public class ViewHolderUS extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.rv_us)
        RecyclerView recyclerView;
        NewsAdapter adapter;

        public ViewHolderUS(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onMoreClicked(articlesItemUs, "Top US News");
                }
            });
        }
    }

    public class ViewHolderTech extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.rv_tech)
        RecyclerView recyclerView;
        TechAdapter adapter;

        public ViewHolderTech(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onMoreClicked(articlesItemTech, "Top Tech News");
                }
            });
        }
    }
}
