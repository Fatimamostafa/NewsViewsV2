package com.fatimamostafa.app.newsviewsv2.ui.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.models.ArticlesItem;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private Context context;
    private List<ArticlesItem> articlesItemList;
    private ListViewContract.OnClickListener callback;
    private String type;
    RequestOptions requestOptions = new RequestOptions();

    public ListViewAdapter(Context context, String type, List<ArticlesItem> articlesItemList, ListViewContract.OnClickListener callback) {
        this.context = context;
        this.articlesItemList = articlesItemList;
        this.callback = callback;
        this.type = type;
        requestOptions.placeholder(R.drawable.placeholder).error(R.drawable.placeholder);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticlesItem item = articlesItemList.get(position);
        holder.tvAuthor.setText("Author: " + item.getAuthor());
        holder.tvDate.setText(Utilities.dateConverter(item.getPublishedAt()));
        holder.tvNewsTitle.setText(item.getTitle());
        if(type.contains("Tech")) {
            holder.tvType.setText("#TECH");
        }
        else {
            holder.tvType.setText("#US");
        }
        Glide.with(context)
                .asBitmap()
                .load(item.getUrlToImage())
                .apply(requestOptions)
                .into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return articlesItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_news_title)
        TextView tvNewsTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_date)
        TextView tvDate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onNewsArticleClicked(articlesItemList.get(getAdapterPosition()));
                }
            });
        }
    }
}
