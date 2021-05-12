@file:JvmName("Deps") // We want any Java files to be able to see these too for Interop

@Suppress("unused")
object Deps {

    // AndroidX
    @Suppress("unused")
    object AndroidX {
        const val activity = "androidx.activity:activity-ktx:${Versions.AndroidX.activity}"
        const val annotation = "androidx.annotation:annotation:${Versions.AndroidX.annotation}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"
        const val ktxCore = "androidx.core:core-ktx:${Versions.AndroidX.ktxCore}"

        object Test {
            const val test = "androidx.test:core:${Versions.AndroidX.Test.test}"
            const val archTest =
                "androidx.arch.core:core-testing:${Versions.AndroidX.Test.archTest}"
            const val junitExt = "androidx.test.ext:junit:${Versions.AndroidX.Test.junitExt}"
            const val testRules = "androidx.test:rules:${Versions.AndroidX.Test.testRules}"
            const val runner = "androidx.test:runner:${Versions.Test.supportTestRunner}"
        }

        object UI {
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.UI.constraintLayout}"
        }
    }

    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val kotlinCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
        const val kotlinCoroutineTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
    }

    object Exoplayer {
        const val core = "com.google.android.exoplayer:exoplayer-core:${Versions.exoplayer}"
        const val ui = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoplayer}"
        const val hls = "com.google.android.exoplayer:exoplayer-hls:${Versions.exoplayer}"
        const val mediaSession = "com.google.android.exoplayer:extension-mediasession:${Versions.exoplayer}"
    }

    // Android
    const val supportAnnotations =
        "com.android.support:support-annotations:${Versions.AndroidX.supportLibs}"
    const val material = "com.google.android.material:material:${Versions.material}"

    object Firebase {
        const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebasePlatform}"
        const val firebasePerformance = "com.google.firebase:firebase-perf-ktx"
    }


    @Suppress("unused")
    object Test {

        object Robolectric {
            const val core = "org.robolectric:robolectric:${Versions.Test.robolectric}"
            const val shadows = "org.robolectric:shadows-support-v4:${Versions.Test.robolectric}"
            const val shadowsDex = "org.robolectric:shadows-multidex:${Versions.Test.robolectric}"
        }

        object Espresso {
            const val core = "androidx.test.espresso:espresso-core:${Versions.AndroidX.Test.espresso}"
        }

        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val junit = "junit:junit:${Versions.Test.junit}"

        object Mockk {
            const val core = "io.mockk:mockk:${Versions.Test.mockk}"
            const val android = "io.mockk:mockk-android:${Versions.Test.mockk}"
        }
    }

    object DevTools {
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.DevTools.leakCanary}"
        const val leakCanaryNoop =
            "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.DevTools.leakCanary}"
    }
}