package com.spellingtrip.example.bean;

import java.util.List;

public class HotCityBean {

    /**
     * code : 0
     * count : 0
     * data : [{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon01.png","city":"青岛"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon02.png","city":"成都"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon03.png","city":"重庆"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon04.png","city":"上海市静安区.上海站"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon05.png","city":"丽江"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon06.png","city":"广州"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon07.png","city":"杭州"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon08.png","city":"添加目的地"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon09.png","city":"沈阳"},{"cityIcon":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon10.png","city":"山东省青岛市即墨区.青岛"}]
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
         * cityIcon : https://pinpinlx.oss-cn-beijing.aliyuncs.com/icons/icon01.png
         * city : 青岛
         */

        private String cityIcon;
        private String city;

        public String getCityIcon() {
            return cityIcon;
        }

        public void setCityIcon(String cityIcon) {
            this.cityIcon = cityIcon;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
