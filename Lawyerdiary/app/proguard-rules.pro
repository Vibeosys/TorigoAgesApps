# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class android.support.v4.app.**{*;}
-keep interface android.support.v4.app.**{*;}
-keep class android.support.v7.app.**{*;}
-keep interface android.support.v7.app.**{*;}
-keep class org.apache.**{*;}
-keep class com.sun.mail.**
-keep public class com.google.android.gms.**
-keep class com.facebook.**
-keep class com.droidninja.**
-keep class com.google.firebase.**
-dontwarn com.vibeosys.lawyerdiary.**
-dontwarn com.facebook.**
-dontwarn com.google.android.gms.**
-dontwarn android.support.v7.**
-dontwarn android.support.v4.**
-dontwarn org.apache.**
-dontwarn com.sun.mail.**
-keepattributes Signature
-dontwarn java.awt.**,javax.security.**,java.beans.**