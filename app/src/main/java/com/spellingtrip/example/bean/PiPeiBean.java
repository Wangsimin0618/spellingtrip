package com.spellingtrip.example.bean;

import java.util.List;

public class PiPeiBean {

    /**
     * code : 0
     * count : 0
     * data : [{"nick":"三秋","fromArea":"深圳市","labelScore":"兴趣爱好匹配度0%","imUserName":"1060194615124033536","toArea":"北京市","endDate":"2018-12-25","headUrl":"https://static.pinpinlx.cn/20181115/1542281868903.jpg","tripScore":"行程匹配度100%","startDate":"2018-08-02"},{"nick":"脱缰野马","fromArea":"深圳市","labelScore":"兴趣爱好匹配度0%","imUserName":"1060180524531712000","toArea":"北京市","endDate":"2018-12-25","headUrl":"https://static.pinpinlx.cn/20181113/1542083576789.jpg","tripScore":"行程匹配度99%","startDate":"2018-08-04"},{"nick":"青春是首不老的歌","fromArea":"深圳市","labelScore":"兴趣爱好匹配度0%","imUserName":"1060171593126248448","toArea":"北京市","endDate":"2018-12-08","headUrl":"https://static.pinpinlx.cn/20181113/1542075013374.jpg","tripScore":"行程匹配度93%","startDate":"2018-08-04"},{"nick":"系统官方","fromArea":"深圳市","labelScore":"兴趣爱好匹配度0%","imUserName":"1060173228825116672","toArea":"北京市","endDate":"2018-12-08","headUrl":"https://static.pinpinlx.cn/20181113/1542095701348.jpg","tripScore":"行程匹配度93%","startDate":"2018-08-04"},{"nick":"脱缰野马","fromArea":"深圳市","labelScore":"兴趣爱好匹配度0%","imUserName":"1060180524531712000","toArea":"北京市","endDate":"2018-12-08","headUrl":"https://static.pinpinlx.cn/20181113/1542083576789.jpg","tripScore":"行程匹配度93%","startDate":"2018-08-04"}]
     * msg : success
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public static class DataBean {
        /**
         * nick : 三秋
         * fromArea : 深圳市
         * labelScore : 兴趣爱好匹配度0%
         * imUserName : 1060194615124033536
         * toArea : 北京市
         * endDate : 2018-12-25
         * headUrl : https://static.pinpinlx.cn/20181115/1542281868903.jpg
         * tripScore : 行程匹配度100%
         * startDate : 2018-08-02
         */

        private String nick;
        private String fromArea;
        private String labelScore;
        private String imUserName;
        private String toArea;
        private String endDate;
        private String headUrl;
        private String tripScore;
        private String startDate;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getFromArea() {
            return fromArea;
        }

        public void setFromArea(String fromArea) {
            this.fromArea = fromArea;
        }

        public String getLabelScore() {
            return labelScore;
        }

        public void setLabelScore(String labelScore) {
            this.labelScore = labelScore;
        }

        public String getImUserName() {
            return imUserName;
        }

        public void setImUserName(String imUserName) {
            this.imUserName = imUserName;
        }

        public String getToArea() {
            return toArea;
        }

        public void setToArea(String toArea) {
            this.toArea = toArea;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getTripScore() {
            return tripScore;
        }

        public void setTripScore(String tripScore) {
            this.tripScore = tripScore;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    }
}
