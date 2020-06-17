package com.spellingtrip.example.bean;

import com.spellingtrip.example.retrofit.bean.BaseBean;

import java.util.List;

public class MyPublishBean {

    /**
     * code : 0
     * count : 0
     * data : [{"nick":"游友100010","travelType":2,"images":[{},{}],"createTime":"2019-03-12 09:42:50","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":54,"position":"杭州","title":"杭州","content":"杭州","favoriteCount":0},{"nick":"游友100010","travelType":2,"images":[{},{}],"createTime":"2019-03-12 09:42:11","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":53,"position":"济南","title":"济南","content":"济南","favoriteCount":0},{"nick":"游友100010","travelType":2,"images":[{},{},{}],"createTime":"2019-03-12 09:37:50","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":52,"position":"杭州","title":"杭州西湖","content":"杭州西湖","favoriteCount":0},{"nick":"游友100010","travelType":2,"images":[{},{}],"createTime":"2019-03-12 09:26:33","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":51,"position":"南京","title":"南京一日游","content":"南京一日游","favoriteCount":0},{"nick":"游友100010","travelType":2,"images":[{},{},{}],"createTime":"2019-03-12 09:20:40","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":50,"position":"选择位置","title":"南京","content":"南京","favoriteCount":0},{"nick":"游友100010","travelType":2,"createTime":"2019-03-12 08:21:24","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":49,"position":"杭州","title":"靓丽","content":"靓丽","favoriteCount":0},{"nick":"游友100010","travelType":2,"createTime":"2019-03-12 08:19:39","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":48,"position":"安阳","title":"测试","content":"测试","favoriteCount":0},{"nick":"游友100010","travelType":1,"createTime":"2019-03-11 18:02:30","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":47,"position":"选择位置","title":"测试建","content":"测试建","favoriteCount":0},{"nick":"游友100010","travelType":1,"createTime":"2019-03-11 17:39:36","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":46,"position":"阿拉善","title":"测试","content":"测试","favoriteCount":0},{"nick":"游友100010","travelType":1,"createTime":"2019-03-11 15:47:40","headUrl":"http://test.upload.pinpinlx.cn/avatar.png","likeCount":0,"id":45,"position":"聊城","title":"枫林金色","content":"枫林金色","favoriteCount":0}]
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
         * nick : 游友100010
         * travelType : 2
         * images : [{},{}]
         * createTime : 2019-03-12 09:42:50
         * headUrl : http://test.upload.pinpinlx.cn/avatar.png
         * likeCount : 0
         * id : 54
         * position : 杭州
         * title : 杭州
         * content : 杭州
         * favoriteCount : 0
         */

        private String nick;
        private int travelType;
        private String createTime;
        private String headUrl;
        private int likeCount;
        private int id;
        private String position;
        private String title;

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        private String content;
        private int favoriteCount;
        private VideoBean video;
        private List<ImagesBean> images;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public int getTravelType() {
            return travelType;
        }

        public void setTravelType(int travelType) {
            this.travelType = travelType;
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

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }
        public static class VideoBean {
            /**
             * size : 204835
             * url : http://test.upload.pinpinlx.cn/20190227/1551235680529.mp4
             */

            private int size;
            private String url;

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
        public static class ImagesBean {
            /**
             * originHeight : 1152
             * originPath : http://test.upload.pinpinlx.cn/20190227/1551248288839.origin.jpg
             * thumbnailLength : 74561
             * originWidth : 864
             * thumbnailPath : http://test.upload.pinpinlx.cn/20190227/1551248288839.thumb.jpg
             * thumbnailWidth : 691
             * thumbnailHeight : 922
             * originLength : 110163
             */

            private int originHeight;
            private String originPath;
            private int thumbnailLength;
            private int originWidth;
            private String thumbnailPath;
            private int thumbnailWidth;
            private int thumbnailHeight;
            private int originLength;

            public int getOriginHeight() {
                return originHeight;
            }

            public void setOriginHeight(int originHeight) {
                this.originHeight = originHeight;
            }

            public String getOriginPath() {
                return originPath;
            }

            public void setOriginPath(String originPath) {
                this.originPath = originPath;
            }

            public int getThumbnailLength() {
                return thumbnailLength;
            }

            public void setThumbnailLength(int thumbnailLength) {
                this.thumbnailLength = thumbnailLength;
            }

            public int getOriginWidth() {
                return originWidth;
            }

            public void setOriginWidth(int originWidth) {
                this.originWidth = originWidth;
            }

            public String getThumbnailPath() {
                return thumbnailPath;
            }

            public void setThumbnailPath(String thumbnailPath) {
                this.thumbnailPath = thumbnailPath;
            }

            public int getThumbnailWidth() {
                return thumbnailWidth;
            }

            public void setThumbnailWidth(int thumbnailWidth) {
                this.thumbnailWidth = thumbnailWidth;
            }

            public int getThumbnailHeight() {
                return thumbnailHeight;
            }

            public void setThumbnailHeight(int thumbnailHeight) {
                this.thumbnailHeight = thumbnailHeight;
            }

            public int getOriginLength() {
                return originLength;
            }

            public void setOriginLength(int originLength) {
                this.originLength = originLength;
            }

        }
    }
}
