package com.spellingtrip.example.bean;

import java.util.List;

public class CommentContentBean {

    /**
     * code : 0
     * count : 1
     * data : [{"content":"Fdsfdsfdsfdsfdsfdsfdsfds","createTime":"2019-11-09 21:24:03","headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176693539023618048.jpg","id":1193157304199938049,"nick":"BEYOND","tourId":1,"userId":468,"voteCount":0,"voteStatus":false}]
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
         * content : Fdsfdsfdsfdsfdsfdsfdsfds
         * createTime : 2019-11-09 21:24:03
         * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176693539023618048.jpg
         * id : 1193157304199938049
         * nick : BEYOND
         * tourId : 1
         * userId : 468
         * voteCount : 0
         * voteStatus : false
         */

        private String content;
        private String createTime;
        private String headUrl;
        private long id;
        private String nick;
        private long tourId;
        private int userId;
        private int voteCount;
        private boolean voteStatus;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public long getTourId() {
            return tourId;
        }

        public void setTourId(long tourId) {
            this.tourId = tourId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public boolean isVoteStatus() {
            return voteStatus;
        }

        public void setVoteStatus(boolean voteStatus) {
            this.voteStatus = voteStatus;
        }
    }
}
