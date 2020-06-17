package com.spellingtrip.example.utils;

import android.text.TextUtils;
import android.util.Log;

import com.spellingtrip.example.bean.ChatUserBean;
import com.spellingtrip.example.bean.ConstantsBean;

import java.util.List;

public class ChatUserInfoUtils {
    public static void setChatUserInfo(String hxid,String name,String url){
       String key= PreferenceUtil.getString(ConstantsBean.Spell,ConstantsBean.ChatUserInfo);
       if (!TextUtils.isEmpty(key)){
           ChatUserBean chatUserBean= (ChatUserBean) JsonUtil.fromJson(key,ChatUserBean.class);
           if (chatUserBean!=null&&chatUserBean.getUsers().size()>0){
               if (isHave(hxid,chatUserBean)){
               }else {
                   ChatUserBean.ChatUserInfoBean userInfoBean=new ChatUserBean.ChatUserInfoBean();
                   userInfoBean.setHxname(hxid);
                   userInfoBean.setName(name);
                   userInfoBean.setUrl(url);
                   PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.ChatUserInfo,JsonUtil.toJson(userInfoBean));
               }
           }else {
               ChatUserBean.ChatUserInfoBean userInfoBean=new ChatUserBean.ChatUserInfoBean();
               userInfoBean.setHxname(hxid);
               userInfoBean.setName(name);
               userInfoBean.setUrl(url);
               PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.ChatUserInfo,JsonUtil.toJson(userInfoBean));
           }
       }else {
           ChatUserBean.ChatUserInfoBean userInfoBean=new ChatUserBean.ChatUserInfoBean();
           userInfoBean.setHxname(hxid);
           userInfoBean.setName(name);
           userInfoBean.setUrl(url);
           PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.ChatUserInfo,JsonUtil.toJson(userInfoBean));
       }

    }
    public static boolean isHave(String hxid, ChatUserBean list) {
        List<ChatUserBean.ChatUserInfoBean>userBeans=list.getUsers();
        for (int i = 0; i < userBeans.size(); i++) {
            if (userBeans.get(i).getHxname().equals(hxid)) {
                return true;
            }

        }
        return false;
    }
    public static ChatUserBean.ChatUserInfoBean gethatUserInfo(String hxid){
        String keys= PreferenceUtil.getString(ConstantsBean.Spell,ConstantsBean.ChatUserInfo);
        Log.e("gethatUserInfo",keys+"");
        if (!TextUtils.isEmpty(keys)){
            ChatUserBean chatUserBean= (ChatUserBean) JsonUtil.fromJson(keys,ChatUserBean.class);
            if (chatUserBean!=null&&chatUserBean.getUsers()!=null){
                List<ChatUserBean.ChatUserInfoBean>userBeans=chatUserBean.getUsers();
                for (int i = 0; i < userBeans.size(); i++) {
                    if (userBeans.get(i).getHxname().equals(hxid)){
                        ChatUserBean.ChatUserInfoBean bean= (ChatUserBean.ChatUserInfoBean) JsonUtil.fromJson(JsonUtil.toJson(userBeans.get(i))
                                ,ChatUserBean.ChatUserInfoBean.class);
                        return bean;
                    }
                }
            }
        }

        return null;
    }
}
