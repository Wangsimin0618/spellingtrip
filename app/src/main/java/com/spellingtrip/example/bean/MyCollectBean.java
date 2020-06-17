package com.spellingtrip.example.bean;

import java.util.List;

public class MyCollectBean{


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
             * travelType : 2
             * favoriteId : 58
             * images : [{"originHeight":864,"originPath":"http://test.upload.pinpinlx.cn/20190115/1547565587995.origin.jpg","thumbnailLength":79226,"originWidth":1152,"thumbnailPath":"http://test.upload.pinpinlx.cn/20190115/1547565587995.thumb.jpg","thumbnailWidth":922,"thumbnailHeight":691,"originLength":117265}]
             * createTime : 2019-03-16 08:13:02
             * travelId : 33
             * headUrl : http://test.upload.pinpinlx.cn/20181115/1542281868903.jpg
             * content : 漫咖啡
             */

            private String nick;
            private int travelType;
            private int favoriteId;
            private String createTime;
            private int travelId;
            private String headUrl;
            private String content;
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

            public int getFavoriteId() {
                return favoriteId;
            }

            public void setFavoriteId(int favoriteId) {
                this.favoriteId = favoriteId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getTravelId() {
                return travelId;
            }

            public void setTravelId(int travelId) {
                this.travelId = travelId;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public VideoBean getVideo() {
                return video;
            }

            public void setVideo(VideoBean video) {
                this.video = video;
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
                 * originHeight : 864
                 * originPath : http://test.upload.pinpinlx.cn/20190115/1547565587995.origin.jpg
                 * thumbnailLength : 79226
                 * originWidth : 1152
                 * thumbnailPath : http://test.upload.pinpinlx.cn/20190115/1547565587995.thumb.jpg
                 * thumbnailWidth : 922
                 * thumbnailHeight : 691
                 * originLength : 117265
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
}
