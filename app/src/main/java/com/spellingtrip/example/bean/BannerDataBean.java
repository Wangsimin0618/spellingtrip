package com.spellingtrip.example.bean;

import java.util.List;

public class BannerDataBean {

    /**
     * code : 0
     * count : 0
     * data : {"total":3,"rows":[{"summary":"八仙花","nick":null,"datetime":"2019-04-02 09:32:22","filepath":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190402/1112890450080956416.jpg","artycleType":4,"id":3,"title":"轮播图"}]}
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
         * total : 3
         * rows : [{"summary":"八仙花","nick":null,"datetime":"2019-04-02 09:32:22","filepath":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190402/1112890450080956416.jpg","artycleType":4,"id":3,"title":"轮播图"}]
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
             * summary : 八仙花
             * nick : null
             * datetime : 2019-04-02 09:32:22
             * filepath : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190402/1112890450080956416.jpg
             * artycleType : 4
             * id : 3
             * title : 轮播图
             */

            private String summary;
            private Object nick;
            private String datetime;
            private String filepath;
            private int artycleType;
            private int id;
            private String title;

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public Object getNick() {
                return nick;
            }

            public void setNick(Object nick) {
                this.nick = nick;
            }

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getFilepath() {
                return filepath;
            }

            public void setFilepath(String filepath) {
                this.filepath = filepath;
            }

            public int getArtycleType() {
                return artycleType;
            }

            public void setArtycleType(int artycleType) {
                this.artycleType = artycleType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
