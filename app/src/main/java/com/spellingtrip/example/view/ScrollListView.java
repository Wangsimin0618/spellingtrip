package com.spellingtrip.example.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @summary 说明：解决ScrollView与ListView冲突
 */

public class ScrollListView extends ListView {
    private boolean isAutoSetNumColumns = true;
    private int horizontalSpacing = 0;
    private int columnWidth = 0;
    private boolean isScroll = false;

    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (isScroll) {
            int gridViewWidth = 0;
            int adapterCount = adapter.getCount();
            if (columnWidth == 0) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    View rowView = adapter.getView(i, null, null);
                    measureView(rowView);
                    gridViewWidth += rowView.getMeasuredWidth();
                }
            } else {
                gridViewWidth += columnWidth * adapterCount;
            }

            if (isAutoSetNumColumns) {
                gridViewWidth += (this.getPaddingLeft() + this.getPaddingRight());
                gridViewWidth += this.horizontalSpacing * (adapterCount - 1);
            }

            this.setLayoutParams(new LinearLayout.LayoutParams(gridViewWidth, LayoutParams.WRAP_CONTENT));

        }

        super.setAdapter(adapter);
    }


    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0,
                0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }


}
