# Proguard rules that are applied to your test apk/code.
-ignorewarnings

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-dontnote junit.framework.**
-dontnote junit.runner.**

-dontwarn android.test.**
-dontwarn androidx.test.**