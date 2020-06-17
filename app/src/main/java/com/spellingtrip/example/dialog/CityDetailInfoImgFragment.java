package com.spellingtrip.example.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.photoview.ZoomImageView;
import com.spellingtrip.example.utils.JsonUtil;

import java.util.List;
import java.util.Locale;

public class CityDetailInfoImgFragment extends DialogFragment {
    private static final String TAG = "BottomFragment";
    private RelativeLayout rlgoodsinfopager;
    private ViewPager vp_goodspager;
    private OnDismissListener mOnDismissListener;
    private TextView mTitleTv;
    private List<String> list;
    private int mPosition;

    public void setData(List<String> imgpaths, int pos) {
        this.list = imgpaths;
        this.mPosition = pos;
        Log.e("httpPaths", JsonUtil.toJson(imgpaths));
    }

    public interface OnDismissListener {
        void onDismiss();
    }
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog(). requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return inflater.inflate(R.layout.goodsinfo_imgviewpage, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlgoodsinfopager =  view.findViewById(R.id.rl_goodsinfopager);
        vp_goodspager =  view.findViewById(R.id.vp_goodspager);
        mTitleTv = view.findViewById(R.id.main_lineargoodsinfo);
        if (list!=null&&list.size() > 1) {
            updata();
        }
      CityDetailImgViewPagerAdapter adapter= new CityDetailImgViewPagerAdapter( list);
        vp_goodspager.setAdapter(adapter);
        vp_goodspager.setCurrentItem(mPosition);
        vp_goodspager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        vp_goodspager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                updata();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updata() {
        mTitleTv.setText(String.format(Locale.getDefault(), "%d/%d", mPosition + 1, list.size()));
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带theme的构造器，获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDatePickerDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
        dialog.setCanceledOnTouchOutside(true);
        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        return dialog;
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }
    }

    public  class CityDetailImgViewPagerAdapter extends PagerAdapter {
        private List<String> imageurl;
        public CityDetailImgViewPagerAdapter(List<String> imageurl) {
            this.imageurl = imageurl;
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ZoomImageView view = new ZoomImageView(container.getContext());
            view.enable();
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(container.getContext()).load(list.get(position)).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.RESULT).into(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageurl.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}


