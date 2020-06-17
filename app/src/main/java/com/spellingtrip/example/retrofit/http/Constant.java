package com.spellingtrip.example.retrofit.http;

/**
 * 常量
 *
 */

public class Constant {
    //发布同城活动
    public static final String PUBLISH_ACTIVITY = "PUBLISH_ACTIVITY";
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";

    /**
     * 接口请求地址
     */
    public static class UrlOrigin {
        /**
         * 获取任务
         */
        public  static  final String getHomeData="getHomeData";
        /**
         * 获取文章详情
         */
        public  static  final String getArticle="getArticle";
        /**
         * 热门拼娃
         */
        public static final String  getHotPin="getHotPin";
        public static final String getTourism="getTourism";
        /**
         * 搜索
         */

        public static final String City="getCity";
        public static final String getSearch="getSearch";
        public static final String publish="publish";
        public static final String IsLogine="IsLogine";
        public static final String logine="logine";
        public static final String register="register";
        public static final String getLike="getLike";
        public static final String getMinePublish="getMinePublish";
        public static final String getMineCollect="getMineCollect";
        public static final String BlackList="blacklist";

        public static final String GETUSER = "getUserinfo";
        public static final String USERINFO = "userinfo";
        public static final String IsLABEL = "IsLABEL";
        public static final String ADDLABEL = "ADDLABEL";

        public static final String REMOVE_PEOPLE = "移除成员";
        public static final String COLLECT_ACTIVITY = "收藏活动";
        public static final String DETELE_ADDAPPLY = "通过拒绝申请";
        public static final String ISEDITPASS = "editpass";
        public static final String NOSEX = "nosex";
    }
}
