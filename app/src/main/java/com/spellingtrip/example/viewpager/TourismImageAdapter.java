package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.design.shape.CornerTreatment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.dialog.GoodsInfoImgFragment;
import com.spellingtrip.example.utils.AsyncImageLoader;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.GlideRoundImage;
import com.spellingtrip.example.view.ShapedImageView;


import java.util.ArrayList;
import java.util.List;

public class TourismImageAdapter extends BaseAdapter {
    private List<TourisnBean.DataBean.ImagesBean> images;
    private Activity activity;
    private boolean isSrcolling;
    private int spec;

    public TourismImageAdapter(Activity activity, List<TourisnBean.DataBean.ImagesBean> images,boolean isSrcolling) {
        this.images=images;
        this.activity=activity;
        this.isSrcolling=isSrcolling;
        int width=CommonUtil.getScreenWidth(activity);
        int hight=CommonUtil.dipToPx(activity,76);
        this. spec=(width-hight)/3;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImgViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ImgViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tourismimage, null);
            viewHolder.itemImage= convertView.findViewById(R.id.itemImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImgViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) viewHolder.itemImage.getLayoutParams();
        layoutParams.width=spec;
        layoutParams.height=spec;
        viewHolder.itemImage.setLayoutParams(layoutParams);
        if (images!=null&&!isSrcolling){
            String  imgurl=images.get(position).getOriginPath()+"?x-oss-process=style/320_320";
            Glide.with(activity).load(imgurl).placeholder(R.mipmap.repalec)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.itemImage);
        }else {
            Glide.with(activity).load(R.mipmap.repalec).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.itemImage);
        }
        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsInfoImgFragment     fragment = new GoodsInfoImgFragment();
                fragment.setData( images, position);
                fragment.show(activity.getFragmentManager(), "DialogFragment");
            }
        });
        return convertView;
    }
    class ImgViewHolder {
        ShapedImageView itemImage;

    }
}