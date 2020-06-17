package com.spellingtrip.example.bean;

public class ConstantsBean {
    //开发基本路径  http://api.pinpinlx.cn  http://60.205.214.116:8888
//    public static final String BASE_PATH = "http://api.pinpinlx.cn";
    public static final String BASE_PATH = "http://test.pinpinlx.cn";
    public static final String BASE_PIN_PATH = "http://www.pinpinlx.cn";

    //用户登录
    public static final String Logine = "/api/user/login";
    //用户注册
    public static final String Register = "/api/user/register";
    public static final String Spell = "Spell";
    public static final String ChatUserInfo="ChatUserInfo";
    public static final String LABELS = "labels";
    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_PASS = "USER_PASS";

    //发送验证码
    public static final String VERIFICATION_CODE = "/api/user/sms/";
    //数据解析错误
    public static final String ERROR = "网络超时";
    //用户点赞
    public static final String DIANZAN = "/api/travel/like";
    //收藏
    public static final String COLLECT = "/api/favorite/collect";
    //拉黑
    public static final String ADDBLACk = "/api/blacklist/add";
    //同城活动  /api/activity/index
    public static final String SAMELIST="/api/activity/index";
    //同城活动详情
    public static final String SAMEDETAIL="/api/activity/detail";
    public static final String AALIST="/tour/list";
    //收藏活动
    public static final String COLLECT_ACTIVITY="/api/activity/collect";
    //申请加入
    public static final String APPLAYADD="/api/activity/member/apply";
    //活动详情
    public static final String CITY_DETAIL="/tour/detail/";

    //发布
    public static final String UPLOAD_PATH = "/api/file/aliyun/upload";
    public static final String PUBLISH = "/api/travel/add";
    public static final String BLACKLIST = "/api/blacklist/mine";
    public static final String REMOVE = "/api/blacklist/remove";
    public static final String RENZHENG = "/api/cardauth/add";
    public static final String UPDATAINFO = "/api/user/update/";
    public static final String SAVELABEL = "/api/label/save";
    public static final String DETELLABEL = "/api/label/delete";
    public static final String MINECOLLECT = "/api/favorite/list/";
    public static final String USERSHOW = "/api/travel/self/";
    //我的拼游
    public static final String MYPIN = "/tour/self/list";
    //我发布的活动列表
    public static final String PUBLISH_ACTIVITY="/api/activity/publish/list";
    public static final String PYAUTO = "/api/pin/auto";
    //匹配详情列表 用户点击自己的
    public static final String RECORD="/api/pin/record";
    //最新拼游
    public static final String NEWPULISH="/api/pin/publish";
    //删除拼游记录
    public static final String DETELPIN = "/api/pin/delete";
    public static final String PIPEI ="pipei" ;
    public static final String NOLOGINE = "当前没有登录的账号";
    public static final String APPVERSION = "/api/app/version/check";
    //首页轮播
    public static final String HOMEDATA = "/api/article/list";
    public static final String MYPUBLISH = "/api/travel/self";
    public static final String SEARCH = "/api/article/search";
    public static final String TOURISM = "/api/travel/list";
    public static final String ISRENZHENGING = "renzheng";
    public static final String RENXHNEG = "认证中";
    public static final String RENZHENGEND = "已认证";

    //商家认证
    public static final String MERCHANTRZ = "/merchant/auth/commit";

    //选择活动
    public static final String CHOOSE_ACTIVITY = "/api/activity/typelist";
    //发布同城活动
    public static final String PUBLISHACTIVITY = "/api/activity/add";
    //结束活动
    public static final String ACTIVITY_END = "/api/activity/stop";
    //开始活动
    public static final String ACTIVITY_START="/api/activity/start";
    //成员管理
    public static final String CITY_PEOPEL = "/api/activity/member/list";
    //移除成员
    public static final String REMOVE_PEOPLE = "/api/activity/member/remove";
    //申请加入列表
    public static final String APPLYADD_LIST = "/api/activity/member/apply/list";
    //获取信息未读数量
    public static final String NOREAD_MEASSAGE="/api/notify/unread/count";
    //拒绝加入
    public static final String JUJUE_ADDAPPLY = "/api/activity/member/refuse";
    //通过加入
    public static final String TONGYI_ADDAPPLY="/api/activity/member/pass";
    //公告消息
    public static final String NOTICE_MEASSAGE = "/api/notify/list";
    //公告详情
    public static final String NOCTICE_DETAIL = "/api/notify/detail";
    public static final String LIVE = "/api/hotel/list";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String LIVESID = "/api/hotel/detail";
    public static final String UPDATEACTIVITY = "updateActivity";
    public static final String HOTPEOPLE = "hot";
    public static final String TOURISMLISTS = "tourisms";
    public static final String INFOID = "infoId";
    public static final String BOY = "男";
    public static final String GRIL="女";
    public static final Object ONLY = "only";
    public static final String DETAILJIEBAN = "/tour/stop";
    public static final String PIPEIING = "/tour/match/";
    public static final String PINYOU = "/tour/publish";
    public static final String TOURMATCH="/tour/detail/";//"/tour/match/";
    public static final String APP_ID = "wx5b405dc55cfc282f";
    //微信支付
    public static final String WXPAY = "/pay/open/card";
    public static final String MERCHANTPAY = "/pay/merchant/auth";
    public static final String ISVIP = "isvip";
    public static final String HOTCITY = "/tour/hot/city";
    public static final String SETIMAGE = "/api/user/set/images";
    public static final String PINGLUN = "/tour/comment/list/";
    public static final String COOMENTADD = "/tour/comment/add";
    public static final String COMMENTDEL = "/tour/comment/del";
    public static final String DELCOMMENT = "delcomment";
    public static final String TOURLIKE = "/tour/like/add";
    public static final String COMMENTVOTE = "/tour/comment/vote";
    public static final String QQAPPID = "1109167610";
    public static final String CHECK = "/chat/limit/check";
    public static final String SIGNLIST = "/api/signin/list";
    public static final String ADDSIGN = "/api/signin/add";
    public static final String JIFEN = "jifen";
    public static final String REGID = "/jpush/reg";
    public static final String JPUSHMESSAGE = "/jpush/message";
    public static final String CHATJISU = "聊天次数已耗尽，开通会员即可享受无限制聊天；";
    public static final String FABUTISHI = "免费发布次数已耗尽，开通会员即可享受无限制发布；";
    public static final String PINYOUTS = "普通用户有1次免费免费发布拼游信息机会，开通会员即可无次数限制；";
    public static final String CHATJIEBAN = "是否开通会员？";
    public static final String NETWORKTISHI = "当前网络不可用";
    public static final String SENDCONTENT = "你好！看到你的个人信息，对你很感兴趣，可以聊聊吗?";
    public static boolean ISHANENETWORK = true;

    public static String USERINFO = "/api/user/info/";
    public static final String UPDATAPASS = "/api/user/forget/password";
    //智能匹配
    public static final String PINSEARCH = "/api/pin/search";
    //拼游详情
    public static final String PININFO="/api/pin/info";
    //更新详情描述
    public static final String UPDATADESC="/api/pin/update/description";
    public static final String FABU = "正在发布";
    public static final String UPDATA = "正在上传";
    public static final String AUTOPI = "autopi";
    public static final String USER_NICK = "usernick";
    public static final String PIPEITEXT = "暂未匹配到任何用户,是否允许后台自动匹配?";
    public static final String STATUS0 = "匹配中";
    public static final String STATUS1 = "冻结";
    public static final String STATUS2 = "关闭";
    public static final String STATUS3 = "过期";
    public static final String STATUS4 = "失效/删除";
    public static final String  STATUS5 ="匹配完成";
    public static final String DETAIL ="detail" ;
    public static final String HAVENETWORK = "HAVENETWORK";
    public static final String NONETWORK = "NONETWORK";
    public static final String PAIZHAO="capture";
    public static final String VIDEO="video";
    public static final String XIANGce="XIANGce";
    public static final String FROM_ID="from_id";
    public static final String USERID="userId";
    public static final String FROM_AVATER="from_avater";
    public static final String FROM_NICHENG="from_nicheng";
    public static final String TO_AVATER="to_avater";
    public static final String TO_NICHENG="to_nicheng";
    public static final String PAGE="page";
    public static final String TYPE="type";
    public static final String SIZE="size";
    public static final String SIZE_NUM="10";
    public static String tourId="tourId";
    public static final String WXKEY = "dc0ce0fb2a5fb632cf8fc873942d03d4";
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCd4IEGmHjsYD65CHupAFH8GVIS0VmO7E9NCc6ebccaUs0OV4WWXkUR2i4p3cjndmR8bbGcTqJWTjqrBO4crzBaFuh1DbSQdg2U46pJ2efYzxRfaZxG8KbIkBEUJ6xN75FM6z7nCXsKz6wvkNiRR8ZpeTnFx5CYtuwzwdOMCHKvh8ea51j53iRx0UnXr1cl0hqs1I6owIEa5XyNX13gCs5iFoHrznV1Ci/BUXLd4SkgZtdw7oRB97edwLJyhkhLYDdPuTX4rZiCyQ45YOs9m9cdu/RE9pf2yHbyaH1MhrcNRKU+KSC47j2Uvr/WfIbjI8HcIJSsGYLtmQvxkG17LyzTAgMBAAECggEAH1TEhliP9ukKyZHDaMClhJBJ124whcqj2jgOBybmEnVIOIZp/nNq5aYU7z4SdszqGD90tf5s/Q+9WN87mfmycM6HB2PW56xl+ZxM2ScjecVu15iP6QwtLTRaD1PoP/dBMLCm5RbekkCwfSgTspE58QibzIJUUdpLeeIHZ4ztpaPNFcMGHJq8A9C2opfsYFYPojlXm8gvPT7re05UnPkdums+LqBqHj3bYe2fklBIM8wmkpO/3b0nN2//AmgGPEDmDhieFmDjmRNL8y42CC7X/mB4r0ac3y3D3Vmm6xHN1LJ2Cu274OY8J9D0quov5dd6z926wZhuEhLxWrnvJARxYQKBgQD3bilslfQxkCpuwEUk1OyVqwrs8HaRFzx5Z62Fp6rUEUxLG7Oi/1Is67hKxRt7CkiFjVF8B0EvGZsQd/y3y/bbG9igbbefQDYcPwYS4vvcDJMykWNXxZkhQ6eFFKmLmdgAGmZpIQQkh69Yb0ZZqaQY4LvGGZ3l4Uhg2IU/tu9eCQKBgQCjWFGCvPGxFtxipM7gd9JFHBCxAuRuRhF5/XdE2I/zoeewEGr1IPacckvZzFNPcql5FXHqyxbOBpVioVAJlVsQZxRtGAfs3D41XSlWb92V8zEzFPE3Lmqtw0XxtWOqyp2GDV2RobbmlQyjhK4mS6iqxDD9CbNS1Rp41VjuHKyq+wKBgQDpQeItauRtwSIvjHTitxReOIQxQLQWLhilvzbXlGw4RiJtgC0GUbrPfF0dp9ozUUtzE/moelJaK8KCcQs+3jrW7tZ2z1hW+jrIwCVisreLCBPQjT1hJngo4tXPYN5BLAR0fG7lVunH4l5MeYKRzppTlbGPnOC9MXeBTT25BiV8uQKBgQCfstduZOA/7UPmxY7lcwCHRNsk1NkU3zhy9lbHXpvO7LoiLssDggjaB/0UoWnKQSZkFY1cqtZO86zV0zT3SajQrpcP2fDVqLbBYaXqtlAELNIrSQxUkPMHk+UsXdVlw9aimyqiGWNCqPLEZBIHquzqmAn1OqiyEVtiS+Q4WJyQRQKBgDz7ZOKOpr1knA90NvWSKVLUyLwko/eBozAD3a8jTm04C9dd33ip+vtQtjShl0S/1+J1qn+CDvySJjLVU9b7BNAnSQnA8nlEQko3+1buNSkQoDbohR7nX9sFQtthbeOpv6KGaGjwjeTa6sUD34rQHCGygoO9kOCYEspcPAu9mtZw";
    public static final String ALIPAYADDID = "2019090967096457";
    public static final String PARTNERID="1561286641";

    public static final String PublishNumber="publishNumber";
    public static final String NoVipPublishTiShi="非会员每天只能发布三次拼游";

    public static final String SpellTime ="SpellTime";
    public static final String CradLABELS="CradLABELS";
    public static final String isSendMeassge="isSendMeassge";
    public static String CHATNAME="你好！看到你的个人信息，对你很感兴趣，可以聊聊吗?";

    //首页顶部webwiew
    public static final String HOME_WEBWIEW="https://mp.weixin.qq.com/s/NPq_-0GKHqnWlSpxZIMjag";
    //反馈
    public static final String IDEABACK="http://pinpinlx.mikecrm.com/nt5uZqD";
    //商务合作
    public static final String SHANGHE="http://pinpinlx.mikecrm.com/RmuPuGA";

    public static final String PAGE_URL="https://a.app.qq.com/dom/micro/open.jsp?pkgname=com.spellingtrip.example&fromcase=40002";

    //app应用信息
    private String address="http://go.appurl.cc/79813278824";

}
