package com.spellingtrip.example.bean;

import java.util.List;

public class AddApplayBean {

    /**
     * code : 0
     * count : 0
     * data : [{"nick":"yyt","applyId":22,"activityName":"姜堤乐园 - 约拍","headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118759081478193152.png","applyTime":"2019-04-28 14:54","userId":3},{"nick":"yyt","applyId":16,"activityName":"聊城市在经开南苑新城附近 - 咖啡","headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118759081478193152.png","applyTime":"2019-04-27 09:47","userId":3}]
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
         * nick : yyt
         * applyId : 22
         * activityName : 姜堤乐园 - 约拍
         * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118759081478193152.png
         * applyTime : 2019-04-28 14:54
         * userId : 3
         */

        private String nick;
        private int applyId;
        private String activityName;
        private String headUrl;
        private String applyTime;
        private int userId;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public int getApplyId() {
            return applyId;
        }

        public void setApplyId(int applyId) {
            this.applyId = applyId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
