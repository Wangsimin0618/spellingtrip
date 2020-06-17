package com.spellingtrip.example.bean;

import java.util.List;

public class BlackListBean {

    /**
     * code : 0
     * count : 0
     * data : {"total":1,"rows":[{"nick":"三秋","headUrl":"http://test.upload.pinpinlx.cn/20181115/1542281868903.jpg","targetUserId":15,"userId":22}]}
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
         * total : 1
         * rows : [{"nick":"三秋","headUrl":"http://test.upload.pinpinlx.cn/20181115/1542281868903.jpg","targetUserId":15,"userId":22}]
         */

        private int total;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * nick : 三秋
             * headUrl : http://test.upload.pinpinlx.cn/20181115/1542281868903.jpg
             * targetUserId : 15
             * userId : 22
             */

            private String nick;
            private String headUrl;
            private int targetUserId;
            private int userId;

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getTargetUserId() {
                return targetUserId;
            }

            public void setTargetUserId(int targetUserId) {
                this.targetUserId = targetUserId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
