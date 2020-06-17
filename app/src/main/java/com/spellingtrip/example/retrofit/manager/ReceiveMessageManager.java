package com.spellingtrip.example.retrofit.manager;
import android.util.Log;

import com.spellingtrip.example.bean.ArticleBean;
import com.spellingtrip.example.bean.BlackBean;
import com.spellingtrip.example.bean.DianZan;
import com.spellingtrip.example.bean.HomeDataBean;
import com.spellingtrip.example.bean.HotPinBean;
import com.spellingtrip.example.bean.MyCollectBean;
import com.spellingtrip.example.bean.MyPublishBean;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.bean.UserBean;
import com.spellingtrip.example.retrofit.bean.BaseBean;
import com.spellingtrip.example.retrofit.bean.SearchBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.utils.JsonUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 消息接收管理
 * Created by yangle on 2017/6/19.
 */

public class ReceiveMessageManager {

    private static ReceiveMessageManager manager;
    private SendMessageData sendMessageData;

    public static ReceiveMessageManager getInstance() {
        return manager == null ? manager = new ReceiveMessageManager() : manager;
    }

    private ReceiveMessageManager() {
    }

    /**
     * 分发消息
     *
     * @param baseBean  Bean基类
     * @param urlOrigin 请求地址
     */
    public void dispatchMessage(BaseBean baseBean, String urlOrigin) {
        switch (urlOrigin) {
            case Constant.UrlOrigin.getHomeData:
                sendData();
                sendMessageData.setType(Constant.UrlOrigin.getHomeData);
                HomeDataBean homeDataBean = (HomeDataBean) baseBean;
                sendMessageData.setBaseBean(homeDataBean);
                EventBus.getDefault().post(sendMessageData);
                break;
            case Constant.UrlOrigin.getArticle:
                sendData();
                sendMessageData.setType(Constant.UrlOrigin.getArticle);
                ArticleBean articleBean = (ArticleBean) baseBean;
                sendMessageData.setBaseBean(articleBean);
                EventBus.getDefault().post(sendMessageData);
                break;
            case Constant.UrlOrigin.getHotPin:
                sendData();
                sendMessageData.setType(Constant.UrlOrigin.getHotPin);
                HotPinBean hotPinBean = (HotPinBean) baseBean;
                sendMessageData.setBaseBean(hotPinBean);
                EventBus.getDefault().post(sendMessageData);
                break;

            case Constant.UrlOrigin.getLike:
                sendData();
                sendMessageData.setType(Constant.UrlOrigin.getLike);
                DianZan dianZan = (DianZan) baseBean;
                sendMessageData.setBaseBean(dianZan);
                EventBus.getDefault().post(sendMessageData);
                break;


            default:
                break;
        }
    }

    private void sendData() {
        if (sendMessageData==null){
            sendMessageData = new SendMessageData();
        }

    }

}
