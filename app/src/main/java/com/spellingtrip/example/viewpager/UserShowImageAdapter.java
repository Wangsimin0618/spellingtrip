package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MyPublishBean;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.fragment.PublishInfoImgFragment;
import com.spellingtrip.example.utils.GlideRoundImage;

import java.util.List;

class UserShowImageAdapter extends BaseAdapter {
    private List<TourisnBean.DataBean.ImagesBean> images;
    private Activity activity;


    public UserShowImageAdapter(Activity activity, List<TourisnBean.DataBean.ImagesBean> images) {
        this.images=images;
        this.activity=activity;
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
        if (!TextUtils.isEmpty(images.get(position).getThumbnailPath())){
            Glide.with(activity).load(images.get(position).getThumbnailPath()).transform(new CenterCrop(activity),new GlideRoundImage(activity))
                    .priority (Priority.HIGH ).skipMemoryCache(true).into(viewHolder.itemImage);
        }

        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishInfoImgFragment fragment = new PublishInfoImgFragment();
              //  fragment.setData( images, position);
                fragment.show(activity.getFragmentManager(), "DialogFragment");
            }
        });
        return convertView;
    }
    class ImgViewHolder {
        ImageView itemImage;
    }
}

