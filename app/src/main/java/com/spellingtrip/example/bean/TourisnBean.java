package com.spellingtrip.example.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.spellingtrip.example.retrofit.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class TourisnBean  {


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

        private String headUrl;
        private int likeCount;
        private String title;
        private int type;
        private int userId;
        private String content;
        private boolean liked;
        private String nick;
        private String createTime;
        private int id;
        private String position;
        private int favoriteCount;
        private boolean favorited;
        private VideoBean video;
        private List<ImagesBean> images;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public boolean isFavorited() {
            return favorited;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
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
            private Bitmap bitmap;

            public Bitmap getBitmap() {
                return bitmap;
            }

            public void setBitmap(Bitmap bitmap) {
                this.bitmap = bitmap;
            }

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
