package com.fatimamostafa.app.newsviewsv2.utilities;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorationTop extends RecyclerView.ItemDecoration {
    private int space;


    public ItemDecorationTop(int space) {
        this.space = space;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.right = 0;
        outRect.top = 0;
        outRect.bottom = 0;
        outRect.right = 0;

        if (parent.getChildLayoutPosition(view) == parent.getAdapter().getItemCount() - 1) {
            outRect.left = space;
            outRect.right = space;
        } else {
            outRect.left = space;
            outRect.right = 0;
        }
    }
}
