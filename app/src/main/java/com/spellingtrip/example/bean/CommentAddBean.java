package com.spellingtrip.example.bean;

public class CommentAddBean {

    /**
     * code : 0
     * count : 0
     * data : {"content":"评论内容","createTime":1572615449186,"headUrl":"","id":1190261579438882816,"nick":"","tourId":3,"userId":1,"voteCount":0}
     * msg : 评论成功
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
         * content : 评论内容
         * createTime : 1572615449186
         * headUrl :
         * id : 1190261579438882816
         * nick :
         * tourId : 3
         * userId : 1
         * voteCount : 0
         */

        private String content;
        private long createTime;
        private String headUrl;
        private long id;
        private String nick;
        private long tourId;
        private int userId;
        private int voteCount;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
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
    }
}
