package com.spellingtrip.example.task;

import android.text.TextUtils;

import com.spellingtrip.example.bean.DataBean;
import com.spellingtrip.example.bean.UserInfoBean;
import com.spellingtrip.example.bean.UserInfoDataBean;

import org.litepal.LitePal;




/**
 * @summary 说明：用户任务类
 */

public class UserTask {
    private UserTask() {
    }

    private static class UserHolder {
        private static final UserTask USER_TASK = new UserTask();
    }

    public static UserTask getInstance() {
        return UserHolder.USER_TASK;
    }

    /**
     * 判断是否登陆
     *
     * @return
     */
    public boolean isLogin() {
        DataBean user = getUser();
        if (user != null && !TextUtils.isEmpty(String.valueOf(user.getUserId()))) {
            return true;
        } else {
            return false;
        }
    }

    public DataBean getUser() {
        return LitePal.findFirst(DataBean.class);
    }

    public UserInfoDataBean getUserInfoData() {
        return LitePal.findFirst(UserInfoDataBean.class);
    }

}
