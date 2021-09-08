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

-keep class eu.electronicid.sdk.modules.api.model.** { *; }
-keep class eu.electronicid.sdk.domain.model.terms.** { *; }
-keep class eu.electronicid.sdk.domain.model.videoid.event.** { *; }
-keep class eu.electronicid.sdk.domain.model.errorreport.** { *; }
-keep class eu.electronicid.sdk.domain.model.scan.** { *; }
-keep class eu.electronicid.sdk.domain.model.Rectangle { *; }
-keep class eu.electronicid.sdk.domain.model.Size { *; }
-keep class eu.electronicid.sdk.h264encoder.X264Encoder { *; }
-keep class eu.electronicid.sdk.yuvutils.YuvUtils { *; }
-keep class eu.electronicid.sdk.videoid.model.** { *; }
-keep class eu.electronicid.sdk.videoid.control.model.* { *; }
-keep class eu.electronicid.sdk.videoid.adhoc.model.FrameCaptureStart { *; }
-keep class eu.electronicid.sdk.videoid.webrtc.model.* { *; }
-keep class org.webrtc.**  { *; }
-keep class eu.electronicid.sdk.discriminator.api.model.Bandwidth { *; }
-keep class eu.electronicid.sdk.domain.model.Protocol { *; }
-keep class eu.electronicid.sdk.domain.model.nfc.NFCTag { *; }