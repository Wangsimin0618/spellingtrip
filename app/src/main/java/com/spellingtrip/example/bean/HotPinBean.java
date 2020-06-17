package com.spellingtrip.example.bean;

import com.spellingtrip.example.retrofit.bean.BaseBean;

import java.util.List;

public class HotPinBean extends BaseBean{

    /**
     * code : 0
     * count : 0
     * data : [{"headUrl":"http://test.upload.pinpinlx.cn/20181113/1542083576789.jpg","userId":14},{"headUrl":"http://test.upload.pinpinlx.cn/20181115/1542281868903.jpg","userId":15},{"headUrl":"http://test.upload.pinpinlx.cn/20181113/1542089180059.jpg","userId":16},{"headUrl":"http://test.upload.pinpinlx.cn/20181113/1542083300649.jpg","userId":17}]
     * msg : success
     * objId : 0
     */

    private int code;
    private int count;
    private String msg;
    private int objId;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * headUrl : http://test.upload.pinpinlx.cn/20181113/1542083576789.jpg
         * userId : 14
         */

        private String headUrl;
        private int userId;

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
