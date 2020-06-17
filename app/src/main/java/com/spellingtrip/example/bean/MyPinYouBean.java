package com.spellingtrip.example.bean;

import java.util.List;

public class MyPinYouBean {

    /**
     * code : 0
     * count : 0
     * data : {"total":1,"rows":[{"imUserName":"1102871704486543361","toArea":"青岛","endDate":"2019-03-30","headUrl":"http://test.upload.pinpinlx.cn/20190316/1552701821018.","publishSchedule":false,"matchCount":1,"pinId":100,"nick":"海草","fromArea":"青岛","userIcons":["http://test.upload.pinpinlx.cn/avatar.png"],"startDate":"2019-03-23","createDate":"2019-03-21","status":0}]}
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
         * rows : [{"imUserName":"1102871704486543361","toArea":"青岛","endDate":"2019-03-30","headUrl":"http://test.upload.pinpinlx.cn/20190316/1552701821018.","publishSchedule":false,"matchCount":1,"pinId":100,"nick":"海草","fromArea":"青岛","userIcons":["http://test.upload.pinpinlx.cn/avatar.png"],"startDate":"2019-03-23","createDate":"2019-03-21","status":0}]
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
             * imUserName : 1102871704486543361
             * toArea : 青岛
             * endDate : 2019-03-30
             * headUrl : http://test.upload.pinpinlx.cn/20190316/1552701821018.
             * publishSchedule : false
             * matchCount : 1
             * pinId : 100
             * nick : 海草
             * fromArea : 青岛
             * userIcons : ["http://test.upload.pinpinlx.cn/avatar.png"]
             * startDate : 2019-03-23
             * createDate : 2019-03-21
             * status : 0
             */

            private String imUserName;
            private String toArea;
            private String endDate;
            private String headUrl;
            private boolean publishSchedule;
            private int matchCount;
            private int pinId;
            private String nick;
            private String fromArea;
            private String startDate;
            private String createDate;
            private int status;
            private List<String> userIcons;

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

            public boolean isPublishSchedule() {
                return publishSchedule;
            }

            public void setPublishSchedule(boolean publishSchedule) {
                this.publishSchedule = publishSchedule;
            }

            public int getMatchCount() {
                return matchCount;
            }

            public void setMatchCount(int matchCount) {
                this.matchCount = matchCount;
            }

            public int getPinId() {
                return pinId;
            }

            public void setPinId(int pinId) {
                this.pinId = pinId;
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

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<String> getUserIcons() {
                return userIcons;
            }

            public void setUserIcons(List<String> userIcons) {
                this.userIcons = userIcons;
            }
        }
    }
}
