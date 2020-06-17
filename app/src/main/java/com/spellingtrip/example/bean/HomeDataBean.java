package com.spellingtrip.example.bean;

import com.spellingtrip.example.retrofit.bean.BaseBean;

import java.util.List;

public class HomeDataBean extends BaseBean {


    /**
     * code : 0
     * count : 0
     * data : {"total":2,"rows":[{"artycleType":4,"banners":[{"summary":"轮播图4","filepath":"http://test.upload.pinpinlx.cn/20181214/1544776061017.thumb.jpg","id":322,"title":"轮播图4"},{"summary":"轮播图3","filepath":"http://test.upload.pinpinlx.cn/20181214/1544776029442.thumb.jpg","id":321,"title":"轮播图3"},{"summary":"轮播图2","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775909130.thumb.jpg","id":317,"title":"轮播图2"},{"summary":"轮播图1","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775862367.thumb.jpg","id":316,"title":"轮播图1"}]},{"summary":"蓝天白云下，青山绿水间，环境清雅舒适......","nick":null,"datetime":"2018-12-14 16:18:20","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775404667.thumb.jpg","artycleType":1,"id":315,"title":"韩国济州岛仙居"},{"summary":"京城中心，体验胡同里的老北京味儿。","nick":null,"datetime":"2018-12-14 16:15:03","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775171830.thumb.jpg","artycleType":1,"id":314,"title":"北京市辛安里胡同74号院"},{"summary":"膏满肉肥，金黄油亮，犹如咸鸭蛋黄，香味扑鼻。","nick":null,"datetime":"2018-12-14 21:42:40","filepath":"http://test.upload.pinpinlx.cn/20181214/1544794894375.thumb.jpg","artycleType":2,"id":323,"title":"和乐蟹"},{"summary":"毛色光泽，肉质滑嫩，皮薄骨酥，香味甚浓，肥而不腻 。","nick":null,"datetime":"2018-12-13 22:02:20","filepath":"http://test.upload.pinpinlx.cn/20181213/1544709451183.thumb.jpg","artycleType":2,"id":269,"title":"文昌鸡"},{"summary":"苍山雪，洱海月，  大理，一生不能不到的地方。","nick":null,"datetime":"2018-12-14 16:09:23","filepath":"http://test.upload.pinpinlx.cn/20181214/1544773126132.thumb.jpg","artycleType":3,"id":313,"title":"只为与你有一次美丽的邂逅\u2014\u2014大理印象"},{"summary":"距离上次去日本一转眼已经三年了，也算是成为社畜后第一次出国远行。","nick":null,"datetime":"2018-12-14 09:29:09","filepath":"http://test.upload.pinpinlx.cn/20181214/1544750827879.thumb.jpg","artycleType":3,"id":289,"title":"品味日本风情，关西七天行！"}]}
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
         * rows : [{"artycleType":4,"banners":[{"summary":"轮播图4","filepath":"http://test.upload.pinpinlx.cn/20181214/1544776061017.thumb.jpg","id":322,"title":"轮播图4"},{"summary":"轮播图3","filepath":"http://test.upload.pinpinlx.cn/20181214/1544776029442.thumb.jpg","id":321,"title":"轮播图3"},{"summary":"轮播图2","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775909130.thumb.jpg","id":317,"title":"轮播图2"},{"summary":"轮播图1","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775862367.thumb.jpg","id":316,"title":"轮播图1"}]},{"summary":"蓝天白云下，青山绿水间，环境清雅舒适......","nick":null,"datetime":"2018-12-14 16:18:20","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775404667.thumb.jpg","artycleType":1,"id":315,"title":"韩国济州岛仙居"},{"summary":"京城中心，体验胡同里的老北京味儿。","nick":null,"datetime":"2018-12-14 16:15:03","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775171830.thumb.jpg","artycleType":1,"id":314,"title":"北京市辛安里胡同74号院"},{"summary":"膏满肉肥，金黄油亮，犹如咸鸭蛋黄，香味扑鼻。","nick":null,"datetime":"2018-12-14 21:42:40","filepath":"http://test.upload.pinpinlx.cn/20181214/1544794894375.thumb.jpg","artycleType":2,"id":323,"title":"和乐蟹"},{"summary":"毛色光泽，肉质滑嫩，皮薄骨酥，香味甚浓，肥而不腻 。","nick":null,"datetime":"2018-12-13 22:02:20","filepath":"http://test.upload.pinpinlx.cn/20181213/1544709451183.thumb.jpg","artycleType":2,"id":269,"title":"文昌鸡"},{"summary":"苍山雪，洱海月，  大理，一生不能不到的地方。","nick":null,"datetime":"2018-12-14 16:09:23","filepath":"http://test.upload.pinpinlx.cn/20181214/1544773126132.thumb.jpg","artycleType":3,"id":313,"title":"只为与你有一次美丽的邂逅\u2014\u2014大理印象"},{"summary":"距离上次去日本一转眼已经三年了，也算是成为社畜后第一次出国远行。","nick":null,"datetime":"2018-12-14 09:29:09","filepath":"http://test.upload.pinpinlx.cn/20181214/1544750827879.thumb.jpg","artycleType":3,"id":289,"title":"品味日本风情，关西七天行！"}]
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
             * artycleType : 4
             * banners : [{"summary":"轮播图4","filepath":"http://test.upload.pinpinlx.cn/20181214/1544776061017.thumb.jpg","id":322,"title":"轮播图4"},{"summary":"轮播图3","filepath":"http://test.upload.pinpinlx.cn/20181214/1544776029442.thumb.jpg","id":321,"title":"轮播图3"},{"summary":"轮播图2","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775909130.thumb.jpg","id":317,"title":"轮播图2"},{"summary":"轮播图1","filepath":"http://test.upload.pinpinlx.cn/20181214/1544775862367.thumb.jpg","id":316,"title":"轮播图1"}]
             * summary : 蓝天白云下，青山绿水间，环境清雅舒适......
             * nick : null
             * datetime : 2018-12-14 16:18:20
             * filepath : http://test.upload.pinpinlx.cn/20181214/1544775404667.thumb.jpg
             * id : 315
             * title : 韩国济州岛仙居
             */

            private int artycleType;
            private String summary;
            private Object nick;
            private String datetime;
            private String filepath;
            private int id;
            private String title;
            private List<BannersBean> banners;

            public int getArtycleType() {
                return artycleType;
            }

            public void setArtycleType(int artycleType) {
                this.artycleType = artycleType;
            }

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

            public List<BannersBean> getBanners() {
                return banners;
            }

            public void setBanners(List<BannersBean> banners) {
                this.banners = banners;
            }

            public static class BannersBean {
                /**
                 * summary : 轮播图4
                 * filepath : http://test.upload.pinpinlx.cn/20181214/1544776061017.thumb.jpg
                 * id : 322
                 * title : 轮播图4
                 */

                private String summary;
                private String filepath;
                private int id;
                private String title;

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
                }

                public String getFilepath() {
                    return filepath;
                }

                public void setFilepath(String filepath) {
                    this.filepath = filepath;
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
}
