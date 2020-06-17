package com.spellingtrip.example.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.platform.comapi.map.A;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MyPublishBean;
import com.spellingtrip.example.fragment.PublishInfoImgFragment;
import com.spellingtrip.example.photoview.ZoomImageView;

import java.util.List;
import java.util.Locale;

public class UserHeaderImgFragment extends DialogFragment {
    private static final String TAG = "BottomFragment";
    private RelativeLayout rlgoodsinfopager;
    private ViewPager vp_goodspager;
    private OnDismissListener mOnDismissListener;
    private TextView mTitleTv;
    private String paths;
    private Activity activity;
    private boolean useThemestatusBarColor=true;


    public void setData(String paths,Activity activity) {
        this.paths = paths;
        this.activity = activity;

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
        setStatusBar();
        //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return inflater.inflate(R.layout.userinfo_imgviewpage, null);
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (useThemestatusBarColor) {
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.articleTitle));
            } else {
                getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlgoodsinfopager =  view.findViewById(R.id.rl_userinfopager);
        vp_goodspager =  view.findViewById(R.id.vp_userinfopager);
        PublishImgViewPagerAdapter adapter= new PublishImgViewPagerAdapter(paths);
        vp_goodspager.setAdapter(adapter);
        vp_goodspager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().cancel();
            }
        });
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

    public  class PublishImgViewPagerAdapter extends PagerAdapter {
        private String paths;


        public PublishImgViewPagerAdapter(String paths) {
            this.paths = paths;

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ZoomImageView view = new ZoomImageView(container.getContext());
            view.enable();
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(container.getContext()).load(paths).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.RESULT).into(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    useThemestatusBarColor=false;
                    setStatusBar();
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
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}



