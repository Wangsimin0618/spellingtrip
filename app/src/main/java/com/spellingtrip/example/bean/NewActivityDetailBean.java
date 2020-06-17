package com.spellingtrip.example.bean;

import java.util.List;

public class NewActivityDetailBean {


    /**
     * code : 0
     * count : 0
     * data : {"userInfo":{"age":27,"bgImages":"[]","birthday":"1992-07-09","city":"阿拉善盟","createTime":"2019-09-25 10:26:38","deleted":false,"description":"等待就是浪费","distance":0,"distanceText":"","geoCode":"","headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176693539023618048.jpg","id":468,"idcard":"","idcardAuth":false,"lat":0,"lng":0,"loginTime":"2019-11-21 00:26:49","nick":"BEYOND","pingId":100487,"points":33,"realName":"","sex":"男","tell":"","temp":false,"username":"15726600783","userpass":"","uuid":"1176684406237036544","vip":false,"vipExpireTime":null},"tour":{"age":0,"createTime":"2019-09-25 10:30:12","description":"十一国庆小长假北京看香山红叶，有一起去的小伙伴么？","endDate":"2019-09-28","fidList":["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685234905677824.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235450937344.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235308331008.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235224444928.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685247341789184.jpg"],"fromArea":"保山","headUrl":"","hotCount":0,"id":1,"imUserName":"","labelList":["北京","香山","红叶"],"labels":"北京,香山,红叶","likeCount":1,"likeState":false,"matchUsers":[],"nick":"","selfSex":"男","startDate":"2019-09-26","status":0,"toArea":"北京","tourLikeList":[{"createTime":"2019-11-21 10:34:53","headUrl":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1194859777214119937.png","id":1197342591352307712,"tourId":1,"userId":1}],"userId":468,"uuid":"1176685302920511488"}}
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
         * userInfo : {"age":27,"bgImages":"[]","birthday":"1992-07-09","city":"阿拉善盟","createTime":"2019-09-25 10:26:38","deleted":false,"description":"等待就是浪费","distance":0,"distanceText":"","geoCode":"","headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176693539023618048.jpg","id":468,"idcard":"","idcardAuth":false,"lat":0,"lng":0,"loginTime":"2019-11-21 00:26:49","nick":"BEYOND","pingId":100487,"points":33,"realName":"","sex":"男","tell":"","temp":false,"username":"15726600783","userpass":"","uuid":"1176684406237036544","vip":false,"vipExpireTime":null}
         * tour : {"age":0,"createTime":"2019-09-25 10:30:12","description":"十一国庆小长假北京看香山红叶，有一起去的小伙伴么？","endDate":"2019-09-28","fidList":["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685234905677824.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235450937344.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235308331008.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235224444928.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685247341789184.jpg"],"fromArea":"保山","headUrl":"","hotCount":0,"id":1,"imUserName":"","labelList":["北京","香山","红叶"],"labels":"北京,香山,红叶","likeCount":1,"likeState":false,"matchUsers":[],"nick":"","selfSex":"男","startDate":"2019-09-26","status":0,"toArea":"北京","tourLikeList":[{"createTime":"2019-11-21 10:34:53","headUrl":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1194859777214119937.png","id":1197342591352307712,"tourId":1,"userId":1}],"userId":468,"uuid":"1176685302920511488"}
         */

        private UserInfoBean userInfo;
        private TourBean tour;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public TourBean getTour() {
            return tour;
        }

        public void setTour(TourBean tour) {
            this.tour = tour;
        }

        public static class UserInfoBean {
            /**
             * age : 27
             * bgImages : []
             * birthday : 1992-07-09
             * city : 阿拉善盟
             * createTime : 2019-09-25 10:26:38
             * deleted : false
             * description : 等待就是浪费
             * distance : 0
             * distanceText :
             * geoCode :
             * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176693539023618048.jpg
             * id : 468
             * idcard :
             * idcardAuth : false
             * lat : 0
             * lng : 0
             * loginTime : 2019-11-21 00:26:49
             * nick : BEYOND
             * pingId : 100487
             * points : 33
             * realName :
             * sex : 男
             * tell :
             * temp : false
             * username : 15726600783
             * userpass :
             * uuid : 1176684406237036544
             * vip : false
             * vipExpireTime : null
             */

            private int age;
            private String bgImages;
            private String birthday;
            private String city;
            private String createTime;
            private boolean deleted;
            private String description;
            private int distance;
            private String distanceText;
            private String geoCode;
            private String headUrl;
            private int id;
            private String idcard;
            private boolean idcardAuth;
            private float lat;
            private float lng;
            private String loginTime;
            private String nick;
            private int pingId;
            private int points;
            private String realName;
            private String sex;
            private String tell;
            private boolean temp;
            private String username;
            private String userpass;
            private String uuid;
            private boolean vip;
            private Object vipExpireTime;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getBgImages() {
                return bgImages;
            }

            public void setBgImages(String bgImages) {
                this.bgImages = bgImages;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public String getDistanceText() {
                return distanceText;
            }

            public void setDistanceText(String distanceText) {
                this.distanceText = distanceText;
            }

            public String getGeoCode() {
                return geoCode;
            }

            public void setGeoCode(String geoCode) {
                this.geoCode = geoCode;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public boolean isIdcardAuth() {
                return idcardAuth;
            }

            public void setIdcardAuth(boolean idcardAuth) {
                this.idcardAuth = idcardAuth;
            }

            public float getLat() {
                return lat;
            }

            public void setLat(float lat) {
                this.lat = lat;
            }

            public float getLng() {
                return lng;
            }

            public void setLng(float lng) {
                this.lng = lng;
            }

            public String getLoginTime() {
                return loginTime;
            }

            public void setLoginTime(String loginTime) {
                this.loginTime = loginTime;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public int getPingId() {
                return pingId;
            }

            public void setPingId(int pingId) {
                this.pingId = pingId;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getTell() {
                return tell;
            }

            public void setTell(String tell) {
                this.tell = tell;
            }

            public boolean isTemp() {
                return temp;
            }

            public void setTemp(boolean temp) {
                this.temp = temp;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUserpass() {
                return userpass;
            }

            public void setUserpass(String userpass) {
                this.userpass = userpass;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public boolean isVip() {
                return vip;
            }

            public void setVip(boolean vip) {
                this.vip = vip;
            }

            public Object getVipExpireTime() {
                return vipExpireTime;
            }

            public void setVipExpireTime(Object vipExpireTime) {
                this.vipExpireTime = vipExpireTime;
            }
        }

        public static class TourBean {
            /**
             * age : 0
             * createTime : 2019-09-25 10:30:12
             * description : 十一国庆小长假北京看香山红叶，有一起去的小伙伴么？
             * endDate : 2019-09-28
             * fidList : ["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685234905677824.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235450937344.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235308331008.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685235224444928.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190925/1176685247341789184.jpg"]
             * fromArea : 保山
             * headUrl :
             * hotCount : 0
             * id : 1
             * imUserName :
             * labelList : ["北京","香山","红叶"]
             * labels : 北京,香山,红叶
             * likeCount : 1
             * likeState : false
             * matchUsers : []
             * nick :
             * selfSex : 男
             * startDate : 2019-09-26
             * status : 0
             * toArea : 北京
             * tourLikeList : [{"createTime":"2019-11-21 10:34:53","headUrl":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1194859777214119937.png","id":1197342591352307712,"tourId":1,"userId":1}]
             * userId : 468
             * uuid : 1176685302920511488
             */

            private int age;
            private String createTime;
            private String description;
            private String endDate;
            private String fromArea;
            private String headUrl;
            private int hotCount;
            private long id;
            private String imUserName;
            private String labels;
            private int likeCount;
            private boolean likeState;
            private String nick;
            private String selfSex;
            private String startDate;
            private int status;
            private String toArea;
            private int userId;
            private String uuid;
            private List<String> fidList;
            private List<String> labelList;
            private List<?> matchUsers;
            private List<TourLikeListBean> tourLikeList;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getFromArea() {
                return fromArea;
            }

            public void setFromArea(String fromArea) {
                this.fromArea = fromArea;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getHotCount() {
                return hotCount;
            }

            public void setHotCount(int hotCount) {
                this.hotCount = hotCount;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getImUserName() {
                return imUserName;
            }

            public void setImUserName(String imUserName) {
                this.imUserName = imUserName;
            }

            public String getLabels() {
                return labels;
            }

            public void setLabels(String labels) {
                this.labels = labels;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public boolean isLikeState() {
                return likeState;
            }

            public void setLikeState(boolean likeState) {
                this.likeState = likeState;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getSelfSex() {
                return selfSex;
            }

            public void setSelfSex(String selfSex) {
                this.selfSex = selfSex;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getToArea() {
                return toArea;
            }

            public void setToArea(String toArea) {
                this.toArea = toArea;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public List<String> getFidList() {
                return fidList;
            }

            public void setFidList(List<String> fidList) {
                this.fidList = fidList;
            }

            public List<String> getLabelList() {
                return labelList;
            }

            public void setLabelList(List<String> labelList) {
                this.labelList = labelList;
            }

            public List<?> getMatchUsers() {
                return matchUsers;
            }

            public void setMatchUsers(List<?> matchUsers) {
                this.matchUsers = matchUsers;
            }

            public List<TourLikeListBean> getTourLikeList() {
                return tourLikeList;
            }

            public void setTourLikeList(List<TourLikeListBean> tourLikeList) {
                this.tourLikeList = tourLikeList;
            }

            public static class TourLikeListBean {
                /**
                 * createTime : 2019-11-21 10:34:53
                 * headUrl : https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1194859777214119937.png
                 * id : 1197342591352307712
                 * tourId : 1
                 * userId : 1
                 */

                private String createTime;
                private String headUrl;
                private long id;
                private long tourId;
                private int userId;

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

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public long getTourId() {
                    return tourId;
                }

                public void setTourId(long tourId) {
                    this.tourId = tourId;
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
}
