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
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}


# support v4 包
-dontwarn android.support.v4.**
-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment


# ************* remove all xlog code START *************
-assumenosideeffects class com.dangbei.xlog.XLog {
      public static *** isLoggable(java.lang.String,int);
      public static *** initialize(...);
      public static *** setDEBUG(...);
      public static *** v(...);
      public static *** i(...);
      public static *** w(...);
      public static *** d(...);
      public static *** e(...);
      public static *** wtf(...);
}
# *************  remove all xlog code END *************


# private app
-keep interface com.dangbei.palaemon.interfaces.**{*;}
-keep class com.dangbei.palaemon.entity.**{*;}
-keep class com.dangbei.palaemon.delegate.PalaemonFocusPaintViewDelegate
-keep class com.dangbei.leanback.component.widget.SearchBar$SearchBarPermissionListener

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# lottie
-keep class com.airbnb.lottie.**{*;}


#不混淆Serializable接口的子类中指定的某些成员变量和方法
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn okhttp3.**
-keep class com.squareup.okhttp.** {*;}
-keep class okhttp3.** {*;}
-keep class okio.** {*;}

# *************  Gson START *************
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class com.dangbei.lerad.screensaver.provider.support.router.**{*;}

# gson 注解
-keep @com.google.gson.annotations.SerializedName class *{*;}
-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @com.google.gson.annotations.SerializedName <methods>;
}
-keepclasseswithmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepclasseswithmembers class * {
    @com.google.gson.annotations.SerializedName <init>(...);
}

# *************  Gson END *************

-assumenosideeffects class com.dangbei.xlog.XLog {
      public static *** isLoggable(java.lang.String,int);
      public static *** initialize(...);
      public static *** setDEBUG(...);
      public static *** v(...);
      public static *** i(...);
      public static *** w(...);
      public static *** d(...);
      public static *** e(...);
      public static *** wtf(...);
}
# *************  remove all xlog code END *************

# *************  Glide START *************
-keep public class com.bumptech.glide.**{}
-keep public class com.dangbei.healingspace.leradlauncher.application.configuration.glide.**{}
-keep public class com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
# *************  Glide END *************

# *************  jiecaovideoplayer START *************
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**
# *************  jiecaovideoplayer END *************

# *************  Retrolamdba START *************
# hide warnings caused by Retrolamdba
-dontwarn java.lang.invoke.*
# *************  Retrolamdba END *************

# *************  UmengSDK START  **************
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}


-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontusemixedcaseclassnames
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn android.graphics.**
-dontwarn android.os.**
-keep class android.os.**
-keep class android.graphics.**{*;}
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable


-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keepattributes Signature


-keepattributes *Annotation*

-keep public class **.R$*{
   public static final int *;
}
# *************  UmengPushSDK END  **************


# *************  android 6.0 SDK 23 START  **************
-dontnote android.webkit.**

# duplicated classes (android.jar & org.apache.http.legacy.jar)
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
# *************  android 6.0 SDK 23 END **************

-keepclassmembers class * implements android.webkit.WebChromeClient{
   		public void openFileChooser(...);
}

#广告
-keep class com.dangbei.euthenia.**{*;}
-keep class com.dangbeimarket.downloader.**{*;}
-keep class com.j256.ormlite.**{*;}
#广告

# 下载器
-keep class com.liulishuo.**{*;}

# 抑制警告
-ignorewarnings

# 局域网androidasync混淆
-keep class * extends com.koushikdutta.async.TapCallback {
  public protected private *;
}

# dal_request
-keep class !com.dangbei.lerad.screensaver.provider.dal.db.model**{*;}
-keep class com.dangbei.lerad.screensaver.provider.dal.net.http.response.**{*;}
-keep class com.dangbei.lerad.screensaver.provider.dal.net.http.entity.**{*;}
#-keep class com.dangbei.lerad.screensaver.provider.bll.interactor.comb.**{*;}
-keep class user.provider.dal.net.entity.**{*;}
-keep class user.provider.dal.net.response.**{*;}


-keep class com.umeng.** {*;}
-keep class org.repackage.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#长链接
-keep class com.dangbei.lerad.screensaver.socket.**{*;}