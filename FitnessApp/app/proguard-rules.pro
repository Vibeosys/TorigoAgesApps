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
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep interface android.support.v4.** { *; }
-keep class com.vibeosys.fitnessapp.FeedbackActivity
-keep class com.vibeosys.fitnessapp.WorkoutReportActivity
-keepclasseswithmembernames class com.vibeosys.fitnessapp.WorkoutReportActivity
-keepclasseswithmembernames class com.vibeosys.fitnessapp.WorkoutGraphActivity
-keepclasseswithmembernames class com.vibeosys.fitnessapp.utils.JSSEProvider
-keepclasseswithmembernames class com.vibeosys.fitnessapp.utils.GMailSender
-keepclasseswithmembernames class com.vibeosys.fitnessapp.** { *;}
-dontwarn android.support.v7.**
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepattributes Signature