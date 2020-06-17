package com.spellingtrip.example.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesAllItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesAllItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = 0;
        outRect.top = space;
        // Add top margin only for the first item to avoid double space between items
       /* if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }*/
    }
}

