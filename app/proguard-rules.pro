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

# Custom ProGuard Rules

# Prevent obfuscation of the QuestionModel class
-keepclassmembers class com.hmzii.brainquiz.QuestionModel {
    <fields>;
    <methods>;
}

-keepnames class com.hmzii.brainquiz.QuestionModel {
    <init>(...);
}

# Prevent obfuscation of Firebase and Google Play services classes
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Preserve Firestore annotations
-keepnames @com.google.firebase.firestore.PropertyName class * { *; }
-keepnames @com.google.firebase.firestore.Exclude class * { *; }
-keepnames @com.google.firebase.firestore.ServerTimestamp class * { *; }

# Ensure classes extending Firestore exclusions are not obfuscated
-keepnames class * extends com.google.firebase.firestore.Exclude { *; }