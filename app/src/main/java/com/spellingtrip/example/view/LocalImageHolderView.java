package com.spellingtrip.example.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.spellingtrip.example.R;


/**
 * @summary 说明：本地图片加载ViewPager
 */
public class LocalImageHolderView implements Holder<Integer> {
    ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_viewpager_image, null);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
    }
}
