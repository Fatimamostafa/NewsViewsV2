package com.fatimamostafa.app.newsviewsv2.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.fatimamostafa.app.newsviewsv2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private Context context;
    private List<NavItem> navItemList;
    private MainContract.OnClickListener callBack;

    NavigationAdapter(Context context, List<NavItem> navItemList, MainContract.OnClickListener callBack) {
        this.context = context;
        this.navItemList = navItemList;

        this.callBack = callBack;
    }

    private void updateItem(int i){

        if(navItemList.get(i).isSelected())
            return;

        for (NavItem item: navItemList) {
            item.setSelected(false);
        }
        navItemList.get(i).setSelected(true);
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_bar, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivNav.setBackgroundResource(navItemList.get(position).getImage());
        holder.tvNav.setText(navItemList.get(position).getTitle());
        if(navItemList.get(position).isSelected()){
            holder.tvNav.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }else{
            holder.tvNav.setTextColor(ContextCompat.getColor(context, R.color.colorNotBlack));
        }
    }

    @Override
    public int getItemCount() {
        return navItemList.size();
    }

    public void openHomeFragment() {
        callBack.onNavItemClicked(navItemList.get(0));
        updateItem(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_nav)
        ImageView ivNav;
        @BindView(R.id.tv_nav)
        TextView tvNav;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callBack.onNavItemClicked(navItemList.get(getAdapterPosition()));
                    updateItem(getAdapterPosition());
                }
            });
        }
    }
}
