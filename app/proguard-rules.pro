# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.bumptech.glide.Glide { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#############################################
#
# 运行错误
#
#############################################
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okio.** { *; }
#ormlite
-keepattributes *Annotation*
-keep class com.j256.ormlite.** { *; }
-dontwarn com.j256.ormlite.android.**
-dontwarn com.j256.ormlite.dao.**
-dontwarn com.j256.ormlite.db.**
-dontwarn com.j256.ormlite.field.**
-dontwarn com.j256.ormlite.logger.**
-dontwarn com.j256.ormlite.misc.**
-dontwarn com.j256.ormlite.stmt.**
-dontwarn com.j256.ormlite.support.**
-dontwarn com.j256.ormlite.table.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.**
#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}

-dontwarn com.amap.api.navi.**
-dontwarn com.autonavi.**
#内置语音 V5.6.0之后
-dontwarn com.amap.api.col.n3.l
-keep class com.alibaba.idst.nls.** {*;}
-keep class com.google.**{*;}
-keep class com.nlspeech.nlscodec.** {*;}
#-dontwarn com.guoxiaoxing.phoenix.**
#-dontwarn com.google.firebase.messaging.**
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-dontwarn com.bumptech.glide.**