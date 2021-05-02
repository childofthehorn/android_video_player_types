import java.text.SimpleDateFormat
import java.util.Date

// Declare Plugins in a Block
plugins {
    id("com.android.application")
    // You can do either kotlin() or id() depending on the Plugin
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id("com.google.firebase.firebase-perf")
}

// Every in Kotlin Gradle DSL parameter will have an = or set()
android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    defaultConfig {
        applicationId = "com.stacydevino.videoplayertypes"
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode = timestampVersionCode() // Dynamically generated Timestamps!
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        // notice that `release` was replaced with `getByName("release")`
        // All Strings must be declared
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    // Must declare this in Android Modules to have it recognize /kotlin folders
    // instead of using /java folders in a kotlin-only project
    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
}

dependencies {

    // Enforce configurations to avoid versioning conflicts with 3rd party libs
    configurations.all {
        // Enforce latest android support annotations for all dependencies
        resolutionStrategy.force("com.android.support:support-annotations:1.0.0")

        //This subdependency is included in the Android SDK and can easily cause conflicts
        exclude(group = "commons-logging", module = "commons-logging")
    }


    //General, JIC you include a local library
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.32")

    //AndroidX
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    // Material
    implementation("com.google.android.material:material:1.3.0")

    //Testing
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    testImplementation("io.mockk:mockk:1.11.0")
    androidTestImplementation("io.mockk:mockk:1.11.0")
    androidTestImplementation("io.mockk:mockk-android:1.11.0")

    //Exoplayer
    implementation("com.google.android.exoplayer:exoplayer-core:2.13.3")
    implementation("com.google.android.exoplayer:exoplayer-hls:2.13.3")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.13.3")
    implementation("com.google.android.exoplayer:extension-mediasession:2.13.3")

    // Firebase BoM https://firebase.google.com/docs/android/learn-more#bom
    // Notice that Platforms can be declared inline
    implementation(platform("com.google.firebase:firebase-bom:27.1.0"))
    implementation("com.google.firebase:firebase-perf-ktx")

    // Debug
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
}

// Helpful error handling and build speed
kapt {
    useBuildCache = true
    correctErrorTypes = true
}

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(0, "seconds")
}

// Kotlin Functions!
fun timestampVersionCode(): Int {
    val dateStampInt = Integer.parseInt(SimpleDateFormat("yyyyMMddHH").format(Date()))
    println("\nDatestamp version code generated: $dateStampInt\n")
    return dateStampInt
}

// Adding Dokka Autogen Docs
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    //Sets output directory to app/dokka instead of the default app/build
    outputDirectory.set(File(project.projectDir, "dokka"))

    // General default Android configuration
    dokkaSourceSets {
        configureEach {
            noAndroidSdkLink.set(false)
        }
    }
}
