package com.spellingtrip.example;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyphenate.EMClientListener;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.spellingtrip.example.activity.BaseActivity;
import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.activity.PhoneLogineActivity;
import com.spellingtrip.example.activity.PublishPinActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.UpdateBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.fragment.ConversationListFragment;
import com.spellingtrip.example.fragment.HomeFragment;
import com.spellingtrip.example.fragment.MineFramgment;
import com.spellingtrip.example.fragment.TourismFragment;
import com.spellingtrip.example.huanxin.DemoHelper;
import com.spellingtrip.example.huanxin.runtimepermissions.PermissionsManager;
import com.spellingtrip.example.huanxin.runtimepermissions.PermissionsResultAction;
import com.spellingtrip.example.task.UpdateTask;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.NotifiationUtils;
import com.spellingtrip.example.utils.RoatAnimUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity extends BaseActivity{
    //    public TextView unread_msg_number;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_home)
    RadioButton tabHome;
    @BindView(R.id.tab_circle)
    RadioButton tabCircle;
    @BindView(R.id.tab_add)
    RadioButton tabAdd;
    @BindView(R.id.tab_message)
    RadioButton tabMessage;
    @BindView(R.id.tab_my)
    RadioButton tabMy;
    @BindView(R.id.tab_radiogroup)
    RadioGroup tabRadiogroup;
    private Fragment[] fragments = new Fragment[4];
    private boolean mIsExit;
    private HomeFragment homeFragment;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private TourismFragment tourismFragment;
    private ConversationListFragment conversationListFragment;
    private MineFramgment mineFragment;
    private int currentTabIndex;
    public boolean isConflict = false;
    private boolean isCurrentAccountRemoved = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.spellingtrip.example.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    int x = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        init();
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        requestPermissions();

        tabRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ft = fm.beginTransaction();
                switch (i) {
                    case R.id.tab_home:
                        for (Fragment fragment : fragments) {
                            if (fragment != null && fragment != homeFragment)
                                ft.hide(fragment);
                        }
                        ft.show(homeFragment).commit();
                        break;
                    case R.id.tab_circle:
                        if (tourismFragment == null) {
                            tourismFragment = new TourismFragment();
                            fragments[1] = tourismFragment;
                            ft.add(R.id.frameLayout, tourismFragment);
                        }
                        for (Fragment fragment : fragments) {
                            if (fragment != null && fragment != tourismFragment)
                                ft.hide(fragment);
                        }
                        ft.show(tourismFragment).commit();
                        break;
                    case R.id.tab_add:
//                            RoatAnimUtil.startAnimationIn(tabAdd);
                            CommonDialog.publicPin(MainActivity.this,"fb");

//                        ActivityUtils.skipActivity(MainActivity.this, PublishPinActivity.class, 0, "");
                   /* if (UserTask.getInstance().getUserInfoData()==null||UserTask.getInstance().getUserInfoData().getSex()==null) {
                        CommonDialog.getDialog(MainActivity.this, "提示", "请先去完善个人资料(性别/年龄)", "取消", "去完善", new OnButtonClick() {
                            @Override
                            public void button01ClickListener() {
                            }
                            @Override
                            public void button02ClickListener() {
                                ActivityUtils.skipActivity(MainActivity.this, UserInfoActivity.class, 0, "");
                            }
                        });
                    } else {
                        ActivityUtils.skipActivity(MainActivity.this, PublishPinActivity.class, 0, "");
                    }*/
                        break;
                    case R.id.tab_message:
                        if (conversationListFragment == null) {
                            conversationListFragment = new ConversationListFragment();
                            fragments[2] = conversationListFragment;
                            ft.add(R.id.frameLayout, conversationListFragment);
                        }
                        for (Fragment fragment : fragments) {
                            if (fragment != null && fragment != conversationListFragment)
                                ft.hide(fragment);
                        }
                        ft.show(conversationListFragment).commit();
                        break;
                    case R.id.tab_my:
                        if (mineFragment == null) {
                            mineFragment = new MineFramgment();
                            fragments[3] = mineFragment;
                            ft.add(R.id.frameLayout, mineFragment);
                        }
                        for (Fragment fragment : fragments) {
                            if (fragment != null && fragment != mineFragment)
                                ft.hide(fragment);
                        }
                        ft.show(mineFragment).commit();
                        break;
                }
            }
        });
    }

    private void init() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int width = CommonUtil.getScreenWidth(MainActivity.this);
        int spec = CommonUtil.dipToPx(MainActivity.this, 10);
        int mWidth = width / 4 * 3 - spec;
        layoutParams.setMargins(mWidth, 0, 0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NotificationManagerCompat notification = NotificationManagerCompat.from(MainActivity.this);
                boolean isEnabled = notification.areNotificationsEnabled();
                if (!isEnabled) {
                    openNotifi();
                }
            }
        }, 5000);

    }

    private void openNotifi() {
        //未打开通知
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("请在“通知”中打开通知权限")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        NotifiationUtils.openPermissionSetting(activity);

                    }
                })
                .create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    @Override
    protected void getData() {
        homeFragment = new HomeFragment();
        fragments[0] = homeFragment;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.frameLayout, homeFragment);
        ft.show(homeFragment).commit();
    }


    @Override
    protected void setData() {
        // checkUpdate();
    }

    @OnClick({R.id.tab_add})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tab_add://圈子发布
//                    RoatAnimUtil.startAnimationIn(tabAdd);
                    CommonDialog.publicPin(MainActivity.this,"fb");

//                if (UserTask.getInstance().isLogin()) {
//                    ActivityUtils.skipActivity(this, PublishPinActivity.class, 0, "");
//                } else {
//                    ActivityUtils.skipActivity(this, LogineActivity.class, 0, "");
//                }
                break;
        }

    }

    /**
     * 返回键处理事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                ToastUtil.show("再按一次退出");
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        Log.e("EMError.USER_REMOVED", EMError.USER_REMOVED + "");
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        Log.e("error", "error");
                        CommonDialog.getOutLoginDialog(MainActivity.this);
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //EventBus.getDefault().post(new SendMessageData(ConstantsBean.HAVENETWORK));
                        } else {
                            //  EventBus.getDefault().post(new SendMessageData(ConstantsBean.NONETWORK));
                        }

                    }
                }
            });
        }
    }

    public void checkUpdate() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.APPVERSION).addParams("platform", "ANDROID").build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    UpdateBean updateBean = (UpdateBean) JsonUtil.fromJson(s, UpdateBean.class);
                    if (updateBean.getCode() == 0) {
                        needUpdate(updateBean);
                    } else {
                    }
                }
            }
        });
    }

    private void needUpdate(UpdateBean updateBean) {
        if (updateBean == null) {
            return;
        }
        String version = CustomApplication.getInstance().getAppVersion();
        if (Integer.valueOf(version.replace(".", "")) < Integer.valueOf(updateBean.getData().getAppVersion().replace(".", ""))) {
            showUpdateDialog(updateBean);
        }
    }

    private void showUpdateDialog(final UpdateBean updateBean) {
        if (updateBean == null) {
            return;
        }
        String msg = null;
        String AppDesc = updateBean.getData().getLogInfo();
      /*  CommonDialog.getUpdateDialog(this, AppDesc, "暂不升级", "马上升级", new OnButtonClick() {
            @Override
            public void button01ClickListener() {
            }

            @Override
            public void button02ClickListener() {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            101);
                } else {
                    updata(updateBean);
                }

            }
        });*/
    }

    private void updata(UpdateBean updateBean) {
        // EventBus.getDefault().post(new EventType("update"));
        UpdateTask.getInstance().startUpdate(this, updateBean);
        ToastUtil.show("正在升级");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict && !isCurrentAccountRemoved) {
//            updateUnreadLabel();
        }
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EMClient.getInstance().removeClientListener(clientListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().vibrateAndPlayTone(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
//                updateUnreadLabel();
                if (currentTabIndex == 0) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
            }
        });
    }

    /**
     * update unread message count
     */
//    public void updateUnreadLabel() {
//        int count = getUnreadMsgCountTotal();
//        if (count > 0) {
//            unread_msg_number.setText(String.valueOf(count));
//            unread_msg_number.setVisibility(View.VISIBLE);
//        } else {
//            unread_msg_number.setVisibility(View.INVISIBLE);
//        }
//    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
            if (success) {
                refreshUIWithMessage();
            }
        }
    };


}
