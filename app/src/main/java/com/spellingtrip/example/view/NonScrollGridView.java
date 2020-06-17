package com.spellingtrip.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class NonScrollGridView extends GridView {

    public NonScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollGridView(Context context) {
        super(context);
    }

    public NonScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override

    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {

            return true;  //禁止GridView滑动

        }



        return super.dispatchTouchEvent(ev);

    }
}