package com.spellingtrip.example.retrofit.bean;

import java.util.List;

public class SearchBean {

    /**
     * code : 0
     * count : 0
     * data : {"total":2,"rows":[{"summary":"京城中心，体验胡同里的老北京味儿。","nick":null,"datetime":"2018-12-14 16:15:03","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775171830.thumb.jpg","articleType":1,"id":314,"title":"北京市辛安里胡同74号院"},{"summary":"苍山雪，洱海月，  大理，一生不能不到的地方。","nick":null,"datetime":"2018-12-14 16:09:23","filepath":"http://test.upload.pinpinlx.cn/20181214/1544773126132.thumb.jpg","articleType":3,"id":313,"title":"只为与你有一次美丽的邂逅\u2014\u2014大理印象"}]}
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
         * total : 2
         * rows : [{"summary":"京城中心，体验胡同里的老北京味儿。","nick":null,"datetime":"2018-12-14 16:15:03","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775171830.thumb.jpg","articleType":1,"id":314,"title":"北京市辛安里胡同74号院"},{"summary":"苍山雪，洱海月，  大理，一生不能不到的地方。","nick":null,"datetime":"2018-12-14 16:09:23","filepath":"http://test.upload.pinpinlx.cn/20181214/1544773126132.thumb.jpg","articleType":3,"id":313,"title":"只为与你有一次美丽的邂逅\u2014\u2014大理印象"}]
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
             * summary : 京城中心，体验胡同里的老北京味儿。
             * nick : null
             * datetime : 2018-12-14 16:15:03
             * filepath : http://test.upload.pinpinlx.cn/20181214/1544775171830.thumb.jpg
             * articleType : 1
             * id : 314
             * title : 北京市辛安里胡同74号院
             */

            private String summary;
            private Object nick;
            private String datetime;
            private String filepath;
            private int articleType;
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

            public int getArticleType() {
                return articleType;
            }

            public void setArticleType(int articleType) {
                this.articleType = articleType;
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
