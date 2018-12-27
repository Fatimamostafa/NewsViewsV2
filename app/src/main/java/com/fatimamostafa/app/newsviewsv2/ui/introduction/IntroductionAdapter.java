package com.fatimamostafa.app.newsviewsv2.ui.introduction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatimamostafa.app.newsviewsv2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IntroductionAdapter extends PagerAdapter {

    private Context mContext;
    List<IntroductionItem> items;

    public IntroductionAdapter(Context mContext, List<IntroductionItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_introduction, container, false);


        IntroductionItem item = items.get(position);

        ImageView ivOnboard = (ImageView) itemView.findViewById(R.id.iv_onboard);
        ivOnboard.setImageResource(item.getImageId());
        TextView tvHeader=(TextView)itemView.findViewById(R.id.tv_header);
        tvHeader.setText(item.getTitle());
        TextView tvDesc=(TextView)itemView.findViewById(R.id.tv_desc);
        tvDesc.setText(item.getDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
