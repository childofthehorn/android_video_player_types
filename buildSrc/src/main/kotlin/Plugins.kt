object Plugins {

    object Id {
        const val app = "com.android.application"
        const val dependencyUpdate = "com.github.ben-manes.versions"
        const val library = "com.android.library"
        const val firebasePerf = "com.google.firebase.firebase-perf"
    }

    const val androidTools = "com.android.tools.build:gradle:${Versions.androidTools}"
    const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    const val firebasePerformance = "com.google.firebase:perf-plugin:${Versions.firebasePerf}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    object Kotlin {
        const val android = "kotlin-android"
        const val androidBase = "android"
        const val core = "jvm"
        const val kapt = "kapt"
        const val dokka = "org.jetbrains.dokka"
    }
}