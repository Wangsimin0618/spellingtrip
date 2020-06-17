package com.spellingtrip.example.bean;

import java.util.List;

public class LiveListBean {

    /**
     * code : 0
     * count : 0
     * data : [{"priceUnit":"一天一晚","originalPrice":380,"distance":"467.64831213513503m","price":280,"intro":"环境优美，美女出入","coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190330/1111503123299434496.jpg","location":"北京小院5巷","hotelId":2,"title":"北汽鼓巷民宿","roomType":["精装修","古风","200平米"]}]
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
         * priceUnit : 一天一晚
         * originalPrice : 380
         * distance : 467.64831213513503m
         * price : 280
         * intro : 环境优美，美女出入
         * coverImage : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190330/1111503123299434496.jpg
         * location : 北京小院5巷
         * hotelId : 2
         * title : 北汽鼓巷民宿
         * roomType : ["精装修","古风","200平米"]
         */

        private String priceUnit;
        private int originalPrice;
        private String distance;
        private int price;
        private String intro;
        private String coverImage;
        private String location;
        private int hotelId;
        private String title;
        private List<String> roomType;

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }

        public int getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(int originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getHotelId() {
            return hotelId;
        }

        public void setHotelId(int hotelId) {
            this.hotelId = hotelId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getRoomType() {
            return roomType;
        }

        public void setRoomType(List<String> roomType) {
            this.roomType = roomType;
        }
    }
}
