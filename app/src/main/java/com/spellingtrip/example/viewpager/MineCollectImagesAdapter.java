package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MyCollectBean;

import com.spellingtrip.example.fragment.CollectInfoImgFragment;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.GlideRoundImage;

import java.util.List;

public class MineCollectImagesAdapter extends BaseAdapter {
    private Activity activity;
    private List<MyCollectBean.DataBean.RowsBean.ImagesBean> images;

    public MineCollectImagesAdapter(Activity activity, List<MyCollectBean.DataBean.RowsBean.ImagesBean> images) {
        this.activity=activity;
        this.images=images;
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
            viewHolder.itemImage = convertView.findViewById(R.id.itemImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImgViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) viewHolder.itemImage.getLayoutParams();
        int width= CommonUtil.getScreenWidth(activity);
        int spec=CommonUtil.dipToPx(activity,24);
        layoutParams.width=(width-spec)/3;
        layoutParams.height=(width-spec)/3;
        viewHolder.itemImage.setLayoutParams(layoutParams);
        String  imgurl=images.get(position).getOriginPath()+"?x-oss-process=style/320_320";
        Glide.with(activity).load(imgurl).placeholder(R.mipmap.photozhan).diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.itemImage);
        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectInfoImgFragment fragment = new CollectInfoImgFragment();
                fragment.setData(images, position);
                fragment.show(activity.getFragmentManager(), "DialogFragment");
            }
        });
        return convertView;
    }

    class ImgViewHolder {
        ImageView itemImage;
    }
}

