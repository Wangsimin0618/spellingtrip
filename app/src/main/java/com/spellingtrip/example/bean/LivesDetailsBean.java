package com.spellingtrip.example.bean;

import java.util.List;

public class LivesDetailsBean {

    /**
     * code : 0
     * count : 0
     * data : {"priceUnit":"一天一晚","images":["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190330/1111503123299434496.jpg"],"originalPrice":380,"lng":116.412528,"tell":"17688202969","hotelId":2,"title":"北汽鼓巷民宿","roomFurniture":["浴室","冰箱","洗衣机","沙发","飘窗"],"price":280,"intro":"环境优美，美女出入","contact":null,"location":"北京小院5巷","roomType":["精装修","古风","200平米"],"lat":39.983733}
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
         * priceUnit : 一天一晚
         * images : ["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190330/1111503123299434496.jpg"]
         * originalPrice : 380
         * lng : 116.412528
         * tell : 17688202969
         * hotelId : 2
         * title : 北汽鼓巷民宿
         * roomFurniture : ["浴室","冰箱","洗衣机","沙发","飘窗"]
         * price : 280
         * intro : 环境优美，美女出入
         * contact : null
         * location : 北京小院5巷
         * roomType : ["精装修","古风","200平米"]
         * lat : 39.983733
         */

        private String priceUnit;
        private int originalPrice;
        private double lng;
        private String tell;
        private int hotelId;
        private String title;
        private int price;
        private String intro;
        private Object contact;
        private String location;
        private double lat;
        private List<String> images;
        private List<String> roomFurniture;
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

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getTell() {
            return tell;
        }

        public void setTell(String tell) {
            this.tell = tell;
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

        public Object getContact() {
            return contact;
        }

        public void setContact(Object contact) {
            this.contact = contact;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<String> getRoomFurniture() {
            return roomFurniture;
        }

        public void setRoomFurniture(List<String> roomFurniture) {
            this.roomFurniture = roomFurniture;
        }

        public List<String> getRoomType() {
            return roomType;
        }

        public void setRoomType(List<String> roomType) {
            this.roomType = roomType;
        }
    }
}
