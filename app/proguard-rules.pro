-optimizationpasses 5  # 指定代码的压缩级别
-dontusemixedcaseclassnames  # 是否使用大小写混合
-dontskipnonpubliclibraryclasses  # 是否混淆第三方jar
-dontskipnonpubliclibraryclassmembers
-dontpreverify  # 混淆时是否做预校验

-dontshrink
-dontoptimize
-verbose # 混淆时是否记录日志
 # 然后使用printmapping指定映射文件的名称
-printmapping priguardMapping.txt
-printusage unused.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* # 混淆时所采用的算法

# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*

# 避免混淆泛型
-keepattributes Exceptions,InnerClasses,Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 保留了继承自Activity、Application这些类的子类
# 因为这些子类有可能被外部调用
# 比如第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化的类不能被混淆
-keep class * implements android.os.Parcelable{
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对R文件下的所有类及其方法，都不能被混淆
-keepclassmembers class **.R$* {
    *;
}

# 保持自定义组件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# 下方是共性的排除项目
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除
-keepclasseswithmembers class * {
    ... *JNI*(...);
}
-keepclasseswithmembernames class * {
    ... *JRI*(...);
}
-keep class **JNI* {*;}

# 对于带有回调函数onXXEvent的，不能混淆
-keepclassmembers class * {
    void *(**On*Event);
}

-keep public class javax.**

-dontwarn android.webkit.*
-keep public class android.webkit.**

-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}

-keep class android.support.v4.** {*;}
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepclassmembers class * extends android.support.v4.app.Fragment {
    public void *(android.view.View);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.widget.AdapterView,android.view.View,int,long);
}
-keepclassmembers class * extends android.support.v4.app.Fragment {
    public void *(android.widget.AdapterView,android.view.View,int,long);
}
-keepclassmembers class * extends android.app.Activity {
    public void onFocusChange(android.widget.AdapterView,android.view.View,boolean);
}
-keepclassmembers class * extends android.support.v4.app.Fragment {
    public void onFocusChange(android.widget.AdapterView,android.view.View,boolean);
}

# other
-dontwarn org.springframework.**
-dontwarn android.util.FloatMath

#---------------Begin: proguard configuration for Gson
# Gson specific classes
-keep class sun.misc.Unsafe {*;}
-keep class com.google.gson.stream.** {*;}
-keep class com.google.gson.examples.android.model.** {*;}
-keep class com.google.gson.** {*;}
-keep class com.google.gson.JsonObject {*;}
-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}
# Application classes that will be serialized/deserialized over Gson
-keep class com.antew.redditinpictures.library.imgur.** {*;}
-keep class com.antew.redditinpictures.library.reddit.** {*;}
#---------------End: proguard configuration for Gson
# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# picture
-keep class com.luck.picture.lib.** {*;}

# arouter
-dontwarn javax.lang.model.element.*
-keep public class com.alibaba.android.arouter.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# butterknife
-keep class butterknife.** {*;}
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder {*;}
-keepclasseswithmembernames class * {@butterknife.* <fields>;}
-keepclasseswithmembernames class * {@butterknife.* <methods>;}

# rxjava/rxandroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# legacy
-dontwarn android.net.compatibility.**
-dontwarn android.net.http.**
-dontwarn com.android.internal.http.multipart.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class com.android.internal.http.multipart.**{*;}
-keep class org.apache.commons.**{*;}
-keep class org.apache.http.**{*;}
# lombok
-keep class lombok.** {*;}
-dontwarn lombok.**
# fastjson
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** {*;}
# okgo
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}

# eventBus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-dontwarn net.lingala.zip4j.** # zip
-dontwarn com.serotonin.modbus4j.** # modbus4j
-dontwarn  org.codehaus.mojo.** # retrofit
-keep class  com.github.barteksc.**{*;} # pdf
-keep class com.shockwave.**
#############################################
# 当前app
#############################################
# dontwarn 不提示警告
# keep 保持原样不混淆
-dontwarn org.**
-keep class org.**{ *; }
-dontwarn com.turingoal.bts.common.**
-keep class com.turingoal.bts.common.**{*;}
-keep class com.turingoal.bts.wps.bean.**{*;}
-keep class com.turingoal.bts.wps.app.**{*;} # TgSystemHelper
