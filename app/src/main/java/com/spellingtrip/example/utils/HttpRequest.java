package com.spellingtrip.example.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.activity.JieBanActivityDetailActivity;
import com.spellingtrip.example.bean.ChatNumberBean;
import com.spellingtrip.example.bean.ConstantsBean;

import com.spellingtrip.example.bean.DataBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UserBean;

import com.spellingtrip.example.bean.UserInfoBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.huanxin.DemoHelper;
import com.spellingtrip.example.liaotian.UserCacheManager;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpRequest {

    public static final String LOGINE_ACTIVITY = "LogineActivity";

    /**
     * 登录接口
     *
     * @param tell
     * @param code
     * @param password
     * @param type     1 密码  2手机号
     */
    public static void getLogine(Activity activity, final String tell, String code, final String password, String type) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.Logine).addParams("tell", tell)
                .addParams("code", code).addParams("v", "1")
                .addParams("password", password).addParams("type", type)
                .build().connTimeOut(50000).readTimeOut(50000).writeTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show("连接超时");
            }

            @Override
            public void onResponse(String s, int i) {
                Log.v("aaaaaaaa",s);

                if (!TextUtils.isEmpty(s)) {
                    UserBean userBean = (UserBean) JsonUtil.fromJson(s, UserBean.class);
                    if (userBean.getCode() == 0) {
                        LitePal.deleteAll(UserBean.class);
                        LitePal.deleteAll(DataBean.class);
                        DataBean dataBean = userBean.getData();
                        userBean.save();
                        boolean succeed = dataBean.save();
                        Log.v("aaaaaaaa","数据保存="+succeed);
                        if (succeed) {
                            String regId = JPushInterface.getRegistrationID(activity);
                            Log.v("aaaaaaaa","regId="+regId);
                            if (!TextUtils.isEmpty(regId)) {
                                setRegId(regId, String.valueOf(dataBean.getUserId()));
                            }
                            Log.v("aaaaaaaa","regId="+regId);
                            if (type.equals("1")) {
                                setHuanXin(dataBean.getImUsername(), password);
                            } else {
//                                getUserInfo();
//                                EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.IsLogine));
                                setRegister(dataBean.getImUsername(), dataBean.getImPassword());

                            }
                        }
                    } else {
                        ToastUtil.show(userBean.getMsg());
                    }
                }
            }


        });
    }

    private static void setRegister(String imUsername, String imPassword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(imUsername,imPassword);
                    DemoHelper.getInstance().setCurrentUserName(imUsername);
                    Log.v("aaaaaaaa","注册成功");
                    setHuanXin(imUsername, imPassword);

                } catch (HyphenateException e) {
                    Log.v("aaaaaaa","--------e=="+e.getErrorCode());
                    switch (e.getErrorCode()){
                        // 网络错误
                        case EMError.NETWORK_ERROR:
                            Log.v("aaaaaaa","网络错误");

                            break;
                        // 用户已存在
                        case EMError.USER_ALREADY_EXIST:
                            Log.v("aaaaaaa","用户已存在");
                            setHuanXin(imUsername, imPassword);

                            break;
                        // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                        case EMError.USER_ILLEGAL_ARGUMENT:
                            Log.v("aaaaaaa","参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册");

                            break;
                        // 服务器未知错误
                        case EMError.SERVER_UNKNOWN_ERROR:
                            Log.v("aaaaaaa","服务器未知错误");

                            break;
                        case EMError.USER_REG_FAILED://208
                            Log.v("aaaaaaa","账号注册失败");
                            break;
                        default:
                            break;
                    }
                }
            }
        }).start();


    }

    private static void setHuanXin(String imUsername, String imPassword) {
     Log.v("111111","----------username=="+imUsername);
        EMClient.getInstance().login(imUsername, imPassword, new EMCallBack() {
            @Override
            public void onSuccess() {
                getUserInfo();
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.IsLogine));
                Log.e(LOGINE_ACTIVITY,
                        "login:成功");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                Log.e(LOGINE_ACTIVITY,
                        "login: onError: " + code+"message"+message);
                if(code == 204){
                    setRegister(imUsername, imPassword);
                }
                LitePal.deleteAll(UserBean.class);
                LitePal.deleteAll(DataBean.class);
//                ToastUtil.show("登录失败");
            }
        });
    }


    public static void getRegister(Activity activity, String url, final String tell, String code, final String password) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + url).addParams("tell", tell).addParams("code", code)
                .addParams("password", password).build().connTimeOut(10000).readTimeOut(10000).writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show("数据解析错误");
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    if (url.contains("password")) {
                        ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                        if (reMoveBean.getCode() == 0) {
                            ToastUtil.show(reMoveBean.getMsg());
                            EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.IsLogine));
                        } else {
                            ToastUtil.show(reMoveBean.getMsg());
                        }
                    } else {
                        UserBean userBean = (UserBean) JsonUtil.fromJson(s, UserBean.class);
                        if (userBean.getCode() == 0) {
                            LitePal.deleteAll(UserBean.class);
                            LitePal.deleteAll(DataBean.class);
                            DataBean dataBean = userBean.getData();
                            boolean succeed = userBean.save();
                            if (succeed) {
                                getLogine(activity, tell, "", password, "1");
                            } else {
                                ToastUtil.show(userBean.getMsg());
                            }
                        } else {
                            ToastUtil.show(userBean.getMsg());
                        }
                    }
                }
            }
        });
    }

    public static void getUserInfo() {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.USERINFO + UserTask.getInstance().getUser().getUserId()).build()
                .connTimeOut(10000).readTimeOut(10000).writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    UserInfoBean userInfoBean = (UserInfoBean) JsonUtil.fromJson(s, UserInfoBean.class);
                    if (userInfoBean.getCode() == 0) {
                        LitePal.deleteAll(UserInfoBean.class);
                        LitePal.deleteAll(UserInfoDataBean.class);
                        UserInfoDataBean userInfoDataBean = userInfoBean.getData();
                        if (userInfoDataBean.isIdcardAuth()) {
                            PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.ISRENZHENGING, ConstantsBean.RENZHENGEND);
                        }else {

                        }
                        userInfoBean.save();
                        PreferenceUtil.putLabels(ConstantsBean.Spell, ConstantsBean.LABELS, userInfoDataBean.getLabels());
                        boolean success = userInfoDataBean.save();
                        if (success) {
                            PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.FROM_NICHENG, userInfoDataBean.getNick());
                            PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.FROM_AVATER, userInfoDataBean.getHeadUrl());
                            PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.FROM_ID, userInfoDataBean.getImUsername());
                            // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
                            UserCacheManager.save(userInfoDataBean.getImUsername(), userInfoDataBean.getNick(), userInfoDataBean.getHeadUrl());
                            EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.GETUSER));
                        }
                    } else {

                    }
                }
            }
        });
    }

    /**
     * 移除
     *
     * @param position
     */
    public static void setReMove(int position) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.REMOVE).addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("targetUserId", String.valueOf(position)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        EventBus.getDefault().post(new EventType(Constant.UrlOrigin.BlackList));
                        ToastUtil.show("移除成功");
                    } else {
                        ToastUtil.show("失败");
                    }
                }
            }
        });
    }

    /**
     * 聊天次数
     *
     * @param userid
     * @param uuid
     */
    public static void getChatNum(Activity activity, int userid, String uuid, String nick, String headurl, String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantsBean.USERID, userid);
            jsonObject.put("chatUserIm", uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(ConstantsBean.BASE_PATH + ConstantsBean.CHECK).content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {
                            ChatNumberBean chatNumberBean = (ChatNumberBean) JsonUtil.fromJson(s, ChatNumberBean.class);
                            if (chatNumberBean.getCode() == 0) {
                                if (chatNumberBean.getData().isChat()) {
                                    UserCacheManager.save(uuid
                                            , nick, headurl);
                                    HttpRequest.sendMessage(uuid);
                                    activity.startActivity(new Intent(activity, ChatActivity.class)
                                            .putExtra(EaseConstant.EXTRA_USER_ID, uuid)
                                            .putExtra("nick", nick));
                                } else {
                                    setNumTiShi(activity, type);
                                    //  ToastUtil.show(chatNumberBean.getData().getMsg());
                                }
                            } else {

                            }

                        }
                    }
                });

    }

    private static void setNumTiShi(Activity activity, String type) {
        if (type.equals("userShow")) {
            CommonDialog.getJIeBAnDialog(activity, ConstantsBean.CHATJISU);
        } else {
            CommonDialog.getJIeBAnDialog(activity, ConstantsBean.CHATJISU);
        }
    }

    public static void setRegId(String regId, String userid) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.REGID).addParams(ConstantsBean.USERID, userid)
                .addParams("regId", regId).build().connTimeOut(10000).readTimeOut(10000).writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("setRegIdonError", JsonUtil.toJson(e));
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {

                    } else {

                    }
                }
            }
        });
    }

    public static void sendMessage(String uuid) {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        String username=JsonUtil.toJson(conversations);
        if (!username.contains(uuid)){
            EMMessage message = EMMessage.createTxtSendMessage(ConstantsBean.CHATNAME, uuid);
            UserCacheManager.setMsgExt(message);
            EMClient.getInstance().chatManager().sendMessage(message);
        }

    }

}
