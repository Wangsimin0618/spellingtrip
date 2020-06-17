package com.spellingtrip.example.bean;

import java.util.List;

public class CityPeopleBean {

    /**
     * code : 0
     * count : 0
     * data : [{"nick":"青春是首不老的歌","userId":1,"applyTime":"2019-04-06 22:23","headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20181113/1542075013374.jpg","memberId":1}]
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
         * nick : 青春是首不老的歌
         * userId : 1
         * applyTime : 2019-04-06 22:23
         * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20181113/1542075013374.jpg
         * memberId : 1
         */

        private String nick;
        private int userId;
        private String applyTime;
        private String headUrl;
        private int memberId;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }
    }
}
