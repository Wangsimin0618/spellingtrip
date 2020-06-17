package com.spellingtrip.example.bean;

public class AliDetailBean {

    /**
     * code : 0
     * count : 0
     * data : {"payMoney":1,"orderId":1190916123244101632,"notifyUrl":"http://test.pinpinlx.cn/pay/ali/notify","orderName":"拼旅行-开通会员卡"}
     * msg : success
     * objId : 0
     */

    private int code;
    private int count;
    private DataBean data;
    private String msg;
    private int objId;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * payMoney : 1
         * orderId : 1190916123244101632
         * notifyUrl : http://test.pinpinlx.cn/pay/ali/notify
         * orderName : 拼旅行-开通会员卡
         */

        private int payMoney;
        private long orderId;
        private String notifyUrl;
        private String orderName;

        public int getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(int payMoney) {
            this.payMoney = payMoney;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }
    }
}
