package com.spellingtrip.example.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.AllPublishActivity;
import com.spellingtrip.example.activity.BuyVIPActivity;
import com.spellingtrip.example.activity.IdeaBackActivity;
import com.spellingtrip.example.activity.MerchantRZActivity;
import com.spellingtrip.example.activity.MineCollectTourismActivity;
import com.spellingtrip.example.activity.MyCodeActivity;
import com.spellingtrip.example.activity.RenZhengActivity;
import com.spellingtrip.example.activity.SetInfoActivity;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.activity.UserWalletActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.view.ShapedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的
 */
public class MineFramgment extends BaseFragment {
    public static final String TAG= "MineFramgment" ;
    @BindView(R.id.mytop_bg)
    ImageView mytopBg;
    @BindView(R.id.myopen_vip)
    ImageView myopenVip;
    @BindView(R.id.myhead)
    ShapedImageView myhead;
    @BindView(R.id.myname)
    TextView myname;
    @BindView(R.id.myaddress)
    TextView myaddress;
    @BindView(R.id.myage)
    TextView myage;
    @BindView(R.id.mysex)
    TextView mysex;
    @BindView(R.id.my_wdqb)
    RadioButton myWdqb;
    @BindView(R.id.my_sjrz)
    RadioButton mySjrz;
    @BindView(R.id.my_smrz)
    RadioButton mySmrz;
    @BindView(R.id.my_mrqd)
    RadioButton myMrqd;
    @BindView(R.id.my_wdxc)
    RadioButton myWdxc;
    @BindView(R.id.my_wdxh)
    RadioButton myWdxh;
    @BindView(R.id.my_yqhy)
    RadioButton myYqhy;
    @BindView(R.id.my_swhz)
    RadioButton mySwhz;
    @BindView(R.id.my_fkjy)
    RadioButton myFkjy;
    @BindView(R.id.my_wdsz)
    RadioButton myWdsz;
    Unbinder unbinder;
    @BindView(R.id.img_isvip)
    ImageView imgIsvip;
//    public ImageView ivSetFragmentRight;
//    public ShapedImageView mineUserHeader;
//    public TextView tvMineUserNick;
//    @BindView(R.id.tvMineUserId)
//    public TextView tvMineUserId;
//    public TextView tvMineUserPesc;
//    @BindView(R.id.rlNewMineNoLogine)
    public RelativeLayout rlMineNoLogine;
//    public RelativeLayout rlMineLabel;
//    @BindView(R.id.rlMineXingCheng)
//    public RelativeLayout rlMineXingCheng;
//    @BindView(R.id.rlMinePublish)
//    public RelativeLayout rlMinePublish;
//    @BindView(R.id.rlMineCollectActivity)
//    public RelativeLayout rlMineCollectActivity;
//    public RelativeLayout rlMineCollectTourism;
//    @BindView(R.id.rlMineAuthor)
//    public RelativeLayout rlMineAuthor;
//    @BindView(R.id.ivNewLogo)
//    public ImageView ivNewLogo;
//    @BindView(R.id.rlMIneUserInfo)
//    public RelativeLayout rlMIneUserInfo;
//    @BindView(R.id.llNewMineUSerInfp)
//    public LinearLayout llNewMineUSerInfp;
//    @BindView(R.id.rlMineJieBan)
//    public RelativeLayout rlMineJieBan;
//    @BindView(R.id.newMineRefreshLayout)
//    public SmartRefreshLayout refreshLayout;
//    @BindView(R.id.newMinervTourism)
//    public XRecyclerView xListView;
//    private TextView tvMineUserLocation;
//    private RelativeLayout rlHeaderMinSu,llMIneJianYi,rlHeaderMineAllPublish,ivMineBuyVip,rlMIneWallet,rlShangHe,rlMineHeaderTopShow;
//    private RelativeLayout rlMineShiMing;
//    private RelativeLayout rlMineHeaderXingCheng;
//    private RelativeLayout rlMIneHeaderChouJiang;
//    private RelativeLayout rlMineHeaderCode;
//    private ImageView tvMineUserVip,ivHeaderMineVIP;
//    private TextView tvHeaderMineOpenVip;

    @Override
    protected int getLayoutId() {
//        return R.layout.fragment_newmine;
        return R.layout.fragment_mine;
    }

    @Override
    protected void findView(View view) {
        EventBus.getDefault().register(this);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setEnableLoadMore(false);
//        refreshLayout.setDisableContentWhenRefresh(true);
//        refreshLayout.setEnableOverScrollDrag(true);
//        LinearLayoutManager mPerfectCourse = new LinearLayoutManager(getActivity());
//        mPerfectCourse.setOrientation(LinearLayoutManager.VERTICAL);
//        xListView.setLayoutManager(mPerfectCourse);
//        xListView.setPullRefreshEnabled(false);
//        xListView.setLoadingMoreEnabled(false);
//        ClassicsHeader   mClassicsHeader = (ClassicsHeader)refreshLayout.getRefreshHeader();
//        mClassicsHeader.setEnableLastTime(false);
//       View mineHeader= LayoutInflater.from(getActivity()).inflate(R.layout.header_mine,null);
//        mineUserHeader= mineHeader.findViewById(R.id.mineUserHeader);
//        tvMineUserNick=mineHeader.findViewById(R.id.tvMineUserNick);
//        tvMineUserPesc= mineHeader.findViewById(R.id.tvMineUserPesc);
//        ivSetFragmentRight=mineHeader.findViewById(R.id.ivSetFragmentRight);
//        rlMineHeaderTopShow=mineHeader.findViewById(R.id.rlMineHeaderTopShow);
//        tvMineUserLocation=mineHeader.findViewById(R.id.tvMineUserLocation);
//        rlMineCollectTourism=mineHeader.findViewById( R.id.rlMineCollectTourism);
//        rlHeaderMinSu=mineHeader.findViewById(R.id.rlHeaderMinSu);
//        rlMineLabel=mineHeader.findViewById(R.id.rlMineLabel);
//        tvMineUserVip=mineHeader.findViewById(R.id.tvMineUserVip);
//        rlShangHe= mineHeader.findViewById(R.id.rlShangHe);
//        rlHeaderMineAllPublish= mineHeader.findViewById(R.id.rlHeaderMineAllPublish);
//        llMIneJianYi=mineHeader.findViewById(R.id.llMIneJianYi);
//        rlMIneWallet=mineHeader.findViewById(R.id.rlMIneWallet);
//        ivMineBuyVip=mineHeader.findViewById(R.id.ivMineBuyVip);
//        rlMineShiMing=mineHeader.findViewById(R.id.rlMineShiMing);
//        ivHeaderMineVIP=mineHeader.findViewById(R.id.ivHeaderMineVIP);
//        rlMineHeaderXingCheng=mineHeader.findViewById(R.id.rlMineHeaderXingCheng);
//        rlMIneHeaderChouJiang=mineHeader.findViewById(R.id.rlMIneHeaderChouJiang);
//        rlMineHeaderCode=mineHeader.findViewById(R.id.rlMineHeaderCode);
//        tvHeaderMineOpenVip=mineHeader.findViewById(R.id.tvHeaderMineOpenVip);
//        rlMineLabel.setOnClickListener(this);
//        rlHeaderMineAllPublish.setOnClickListener(this);
//        ivSetFragmentRight.setOnClickListener(this);
//        rlMineCollectTourism.setOnClickListener(this);
//        rlHeaderMinSu.setOnClickListener(this);
//        rlMineHeaderTopShow.setOnClickListener(this);
//        ivMineBuyVip.setOnClickListener(this);
//        rlMIneWallet.setOnClickListener(this);
//        llMIneJianYi.setOnClickListener(this);
//        rlMineShiMing.setOnClickListener(this);
//        rlMIneHeaderChouJiang.setOnClickListener(this);
//        rlMineHeaderXingCheng.setOnClickListener(this);
//        rlMineHeaderCode.setOnClickListener(this);
//        rlShangHe.setOnClickListener(this);
//        xListView.addHeaderView(mineHeader);
//        UserMineAdapter adapter=new UserMineAdapter(getActivity());
//        xListView.setAdapter(adapter);
    }

    @Override
    protected void getData() {
        if (UserTask.getInstance().isLogin()) {
            HttpRequest.getUserInfo();
        }
    }

    @OnClick({R.id.myhead, R.id.myopen_vip, R.id.my_wdqb, R.id.my_sjrz, R.id.my_smrz, R.id.my_mrqd, R.id.my_wdxc, R.id.my_wdxh, R.id.my_yqhy, R.id.my_swhz, R.id.my_fkjy, R.id.my_wdsz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myhead://头像进入个人页
                if (UserTask.getInstance() != null && UserTask.getInstance().getUser() != null) {
                    int userid = UserTask.getInstance().getUser().getUserId();
                    ActivityUtils.skipActivity(getActivity(), TourismUserShowActivity.class, 0, String.valueOf(userid));
                }
                break;
            case R.id.myopen_vip://开通VIP
                ActivityUtils.skipActivity(getActivity(), BuyVIPActivity.class, 0, "");
                break;
            case R.id.my_wdqb://我的钱包
                ActivityUtils.skipActivity(getActivity(), UserWalletActivity.class, 0, "");
                break;
            case R.id.my_sjrz://商家认证
                ActivityUtils.skipActivity(getActivity(), MerchantRZActivity.class, 0, "");
                break;
            case R.id.my_smrz://实名认证
                ActivityUtils.skipActivity(getActivity(), RenZhengActivity.class, 0, "");
                break;
            case R.id.my_mrqd://每日签到
                CommonDialog.getQianDaoDialog(getActivity());

                break;
            case R.id.my_wdxc://我的行程
                ActivityUtils.skipActivity(getActivity(), AllPublishActivity.class, 0, "");
                break;
            case R.id.my_wdxh://我的喜欢
                ActivityUtils.skipActivity(getActivity(), MineCollectTourismActivity.class, 0, "");
                break;
            case R.id.my_yqhy://邀请好友
                ActivityUtils.skipActivity(getActivity(), MyCodeActivity.class, 0, "");
                break;
            case R.id.my_swhz://商务合作
                break;
            case R.id.my_fkjy://反馈建议
                ActivityUtils.skipActivity(getActivity(), IdeaBackActivity.class, 21, ConstantsBean.IDEABACK);
                break;
            case R.id.my_wdsz://我的设置
                ActivityUtils.skipActivity(getActivity(), SetInfoActivity.class, 0, "");
                break;
        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.rlMineXingCheng:
//                if (UserTask.getInstance().isLogin()){
//                    ActivityUtils.skipActivity(getActivity(), MyPinYouActivity.class, 0, "");
//                }else {
//                    ActivityUtils.skipActivity(getActivity(), LogineActivity.class, 0, "");
//                }
//                break;
//            case R.id.rlMineJieBan:
//                if (UserTask.getInstance().isLogin()){
//                    ActivityUtils.skipActivity(getActivity(), MyJieBanActivity.class, 0, "");
//                }else {
//                    ActivityUtils.skipActivity(getActivity(), LogineActivity.class, 0, "");
//                }
//                break;
//                /*收藏的活动*/
//            case R.id.rlMineCollectActivity:
//                ActivityUtils.skipActivity(getActivity(),MineCollectActivity.class,0,"");
//                break;
//            /*收藏的游记*/
//            case R.id.rlMineCollectTourism:
//                ActivityUtils.skipActivity(getActivity(),MineCollectTourismActivity.class,0,"");
//                break;
//            case R.id.rlHeaderMinSu:
//                ActivityUtils.skipActivity(getActivity(),MinSuListActivity.class,0,"");
//                break;
//            case R.id.rlHeaderMineAllPublish:
//                ActivityUtils.skipActivity(getActivity(),AllPublishActivity.class,0,"");
//                break;
//            case R.id.ivSetFragmentRight:
//                ActivityUtils.skipActivity(getActivity(), SetInfoActivity.class, 0, "");
//                break;
//            case R.id.ivNewLogo:
//                ActivityUtils.skipActivity(getActivity(), LogineActivity.class, 0, "");
//                break;
//            case R.id.rlMineHeaderTopShow:
//                if (UserTask.getInstance()!=null&&UserTask.getInstance().getUser()!=null){
//                    int userid=UserTask.getInstance().getUser().getUserId();
//                    ActivityUtils.skipActivity(getActivity(), TourismUserShowActivity.class, 0, String.valueOf(userid));
//                }
//
//                break;
//            case R.id.rlMIneUserInfo:
//                if (!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getHeadUrl())){
//                    ActivityUtils.skipActivity(getActivity(), TourismUserShowActivity.class, 0, String.valueOf(UserTask.getInstance().getUser().getUserId()));
//                }
//                break;
//            case R.id.rlMineLabel:
//                if (UserTask.getInstance().isLogin()){
//                    ActivityUtils.skipActivity(getActivity(), SetLabelActivity.class, 0, "");
//                }else {
//                    ActivityUtils.skipActivity(getActivity(), LogineActivity.class, 0, "");
//                }
//                break;
//            case R.id.rlMinePublish:
//                if (UserTask.getInstance().isLogin()){
//                    ActivityUtils.skipActivity(getActivity(), MinePublishActivity.class, 0, "");
//                }else {
//                    ActivityUtils.skipActivity(getActivity(), LogineActivity.class, 0, "");
//                }
//                break;
//            case R.id.rlMineAuthor:
//                if (UserTask.getInstance().getUserInfoData()!=null){
//                    if (UserTask.getInstance().getUserInfoData().isIdcardAuth()){
//                        ToastUtil.show("已认证");
//                    }else if (UserTask.getInstance().getUserInfoData().getAuthStatus()==1){
//                        ToastUtil.show("正在审核中");
//                    }else if (!UserTask.getInstance().getUserInfoData().isIdcardAuth()){
//                        ActivityUtils.skipActivity(getActivity(),RenZhengActivity.class,0,"");
//                    }
//                }else {
//                    ActivityUtils.skipActivity(getActivity(),RenZhengActivity.class,0,"");
//                }
//                break;
//            case R.id.ivMineBuyVip:
//                ActivityUtils.skipActivity(getActivity(),BuyVIPActivity.class,0,"");
//                break;
//                //钱包
//            case R.id.rlMIneWallet:
//                ActivityUtils.skipActivity(getActivity(),UserWalletActivity.class,0,"");
//                break;
//                //反馈
//            case R.id.llMIneJianYi:
//                ActivityUtils.skipActivity(getActivity(),IdeaBackActivity.class,21,ConstantsBean.IDEABACK);
//                break;
//            case R.id.rlMineShiMing:
//                ActivityUtils.skipActivity(getActivity(),RenZhengActivity.class,0,"");
//                break;
//            case R.id.rlMineHeaderXingCheng:
//                ToastUtil.show("功能暂未开放");
//                break;
//            case R.id.rlMIneHeaderChouJiang:
//                ToastUtil.show("功能暂未开放");
//                break;
//            case R.id.rlMineHeaderCode:
//                ActivityUtils.skipActivity(getActivity(),MyCodeActivity.class,0,"");
//                break;
//            case R.id.rlShangHe:
//                ActivityUtils.skipActivity(getActivity(),IdeaBackActivity.class,22,ConstantsBean.SHANGHE);
//                break;
//
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        Log.v(TAG,"--------data=="+data.getType());
        if (data.getType().equals(Constant.UrlOrigin.USERINFO)) {
            HttpRequest.getUserInfo();
        } else if (data.getType().equals(Constant.UrlOrigin.GETUSER)) {
            String nick = UserTask.getInstance().getUserInfoData().getNick();
            String birthday = UserTask.getInstance().getUserInfoData().getBirthday();
            String sex = UserTask.getInstance().getUserInfoData().getSex();
            String city = UserTask.getInstance().getUserInfoData().getCity();
            if (UserTask.getInstance().getUserInfoData().isVip()) {
                myname.setTextColor(getResources().getColor(R.color.image_selector_red));
                imgIsvip.setBackground(getResources().getDrawable(R.drawable.vip));
            }else {
                myname.setTextColor(getResources().getColor(R.color.history_text));
                imgIsvip.setBackground(getResources().getDrawable(R.drawable.vipgray));
            }
            if (!TextUtils.isEmpty(nick)) {
                myname.setText(nick);
            } else {
                myname.setText("未命名");
            }
            if (!TextUtils.isEmpty(birthday)) {
                myage.setText(birthday);
            } else {
                myage.setText("未设置");
            }
            if (!TextUtils.isEmpty(sex)) {
                mysex.setText(sex);
            } else {
                mysex.setText("未设置");
            }
            if (!TextUtils.isEmpty(city)) {
                myaddress.setText(city);
            } else {
                myaddress.setText("北京·东城");
            }
            if (UserTask.getInstance().getUserInfoData().getBgImages() != null && UserTask.getInstance().getUserInfoData().getBgImages().size() > 0) {
                String imgurl = UserTask.getInstance().getUserInfoData().getBgImages().get(0) + "?x-oss-process=style/320_320";
                Glide.with(getActivity()).load(imgurl).into(mytopBg);

            } else if (!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getHeadUrl())) {
                    String imgurl = UserTask.getInstance().getUserInfoData().getHeadUrl() + "?x-oss-process=style/320_320";
                    Glide.with(getActivity()).load(imgurl).into(myhead);
            }
        } else if (data.getType().equals(Constant.UrlOrigin.IsLogine)) {
            HttpRequest.getUserInfo();

        }
        if (data.getType().equals(ConstantsBean.HAVENETWORK)) {
            getData();
        }
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
