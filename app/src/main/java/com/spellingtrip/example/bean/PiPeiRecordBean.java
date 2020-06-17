package com.spellingtrip.example.bean;

import java.util.List;

public class PiPeiRecordBean {

    /**
     * code : 0
     * count : 0
     * data : [{"nick":"外星人","fromArea":"阿坝","labelScore":"0%","imUserName":"1060889076632125440","infoId":82,"toArea":"白山","endDate":"2019.04.05","sex":"男","headUrl":"http://test.upload.pinpinlx.cn/20190303/1551605309141.jpg","totalCount":5,"userId":16,"startDate":"2019.04.02"},{"nick":"外星人","fromArea":"阿坝","labelScore":"0%","imUserName":"1060889076632125440","infoId":60,"toArea":"北京","endDate":"2019.04.17","sex":"男","headUrl":"http://test.upload.pinpinlx.cn/20190303/1551605309141.jpg","totalCount":5,"userId":16,"startDate":"2019.04.10"},{"nick":"外星人","fromArea":"北京","labelScore":"0%","imUserName":"1060889076632125440","infoId":57,"toArea":"沧州","endDate":"2019.05.09","sex":"男","headUrl":"http://test.upload.pinpinlx.cn/20190303/1551605309141.jpg","totalCount":5,"userId":16,"startDate":"2019.05.01"},{"nick":"外星人","fromArea":"北京","labelScore":"0%","imUserName":"1060889076632125440","infoId":56,"toArea":"安庆","endDate":"2019.06.08","sex":"男","headUrl":"http://test.upload.pinpinlx.cn/20190303/1551605309141.jpg","totalCount":5,"userId":16,"startDate":"2019.06.05"},{"nick":"三秋","fromArea":"重庆","labelScore":"0%","imUserName":"1060194615124033536","infoId":41,"toArea":"阿克苏","endDate":"2019.03.31","sex":"男","headUrl":"http://test.upload.pinpinlx.cn/20181115/1542281868903.jpg","totalCount":1,"userId":15,"startDate":"2019.03.27"},{"nick":"三秋","fromArea":"阿克苏","labelScore":"0%","imUserName":"1060194615124033536","infoId":39,"toArea":"阿拉善盟","endDate":"2019.03.29","sex":"男","headUrl":"http://test.upload.pinpinlx.cn/20181115/1542281868903.jpg","totalCount":1,"userId":15,"startDate":"2019.03.28"}]
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
         * nick : 外星人
         * fromArea : 阿坝
         * labelScore : 0%
         * imUserName : 1060889076632125440
         * infoId : 82
         * toArea : 白山
         * endDate : 2019.04.05
         * sex : 男
         * headUrl : http://test.upload.pinpinlx.cn/20190303/1551605309141.jpg
         * totalCount : 5
         * userId : 16
         * startDate : 2019.04.02
         */

        private String nick;
        private String fromArea;
        private String labelScore;
        private String imUserName;
        private int infoId;
        private String toArea;
        private String endDate;
        private String sex;
        private String headUrl;
        private int totalCount;
        private int userId;
        private String startDate;
        private String tripScore;

        public String getTripScore() {
            return tripScore;
        }

        public void setTripScore(String tripScore) {
            this.tripScore = tripScore;
        }

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

        public int getInfoId() {
            return infoId;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    }
}
