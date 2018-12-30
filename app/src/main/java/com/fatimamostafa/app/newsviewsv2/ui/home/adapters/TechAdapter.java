package com.fatimamostafa.app.newsviewsv2.ui.home.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.ui.home.HomeContract;
import com.fatimamostafa.app.newsviewsv2.utilities.GlideApp;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TechAdapter extends RecyclerView.Adapter<TechAdapter.ViewHolder> {
    Context context;
    List<ArticlesItem> list;
    RequestOptions requestOptions = new RequestOptions();
    private HomeContract.OnClickListener callback;

    public TechAdapter(Context context, List<ArticlesItem> articlesItemTech, HomeContract.OnClickListener callback) {
        this.context = context;
        this.list = articlesItemTech;
        this.callback = callback;
        requestOptions.placeholder(R.drawable.placeholder).error(R.drawable.placeholder);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tech, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvAuthor.setText("Author: " + list.get(position).getAuthor());
        holder.tvDate.setText(Utilities.dateConverter(list.get(position).getPublishedAt()));
        holder.tvNewsTitle.setText(list.get(position).getTitle());

        GlideApp.with(context)
                .load(list.get(position).getUrlToImage())
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(requestOptions)
                .into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_news_title)
        TextView tvNewsTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onNewsArticleClicked(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
