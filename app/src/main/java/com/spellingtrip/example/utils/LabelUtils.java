package com.spellingtrip.example.utils;

import com.spellingtrip.example.activity.SetLabelActivity;
import com.spellingtrip.example.bean.LabelBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class LabelUtils {
    public static void setLabel(int labelId, String labelName) {
        UserInfoDataBean.LabelsBean   labelListBean = new UserInfoDataBean.LabelsBean();
        if (SetLabelActivity.labels.size() == 0) {
            labelListBean.setLabelId(labelId);
            labelListBean.setLabelName(labelName);
            SetLabelActivity.labels.add(labelListBean);
        } else if (!isHave(labelId)) {
            labelListBean.setLabelId(labelId);
            labelListBean.setLabelName(labelName);
            SetLabelActivity.labels.add(labelListBean);
        }
        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.ADDLABEL));
    }

    private static boolean isHave(int task_id) {
        for (int i = 0; i < SetLabelActivity.labels.size(); i++) {
            if (SetLabelActivity.labels.get(i).getLabelId() == task_id) {
                return true;
            }
        }
        return false;
    }
    public static boolean remove(int task_id) {

        for (int i = 0; i < SetLabelActivity.labels.size(); i++) {
            if (SetLabelActivity.labels.get(i).getLabelId() == task_id) {
                SetLabelActivity.labels.remove(i);
                return true;
            }
        }
        return false;
    }
}
