import java.text.SimpleDateFormat
import java.util.Date

// Declare Plugins in a Block
plugins {
    id(Plugins.Id.app)
    // You can do either kotlin() or id() depending on the Plugin
    kotlin(Plugins.Kotlin.androidBase)
    kotlin(Plugins.Kotlin.kapt)
    id(Plugins.Kotlin.android)
    id(Plugins.Id.firebasePerf)
    id(Plugins.Kotlin.dokka)
}

// Every in Kotlin Gradle DSL parameter will have an = or set()
android {
    compileSdkVersion(Sdk.compile)
    buildToolsVersion(Versions.buildToolsVersion)

    useLibrary(Versions.UseLibs.testRunner)
    useLibrary(Versions.UseLibs.testBase)
    useLibrary(Versions.UseLibs.testMock)

    defaultConfig {
        applicationId = Versions.appName
        minSdkVersion(Sdk.min)
        targetSdkVersion(Sdk.target)
        versionCode = Utils.timestampVersionCode() // Dynamically generated Timestamps!
        versionName = Versions.appVersion
        testInstrumentationRunner = Deps.Test.instrumentationRunner
    }

    buildTypes {
        // notice that `release` was replaced with `getByName("release")`
        // All Strings must be declared
        getByName(ProductTypes.release) {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName(ProductTypes.debug) {
            isMinifyEnabled = false
            isShrinkResources = false
            isCrunchPngs = false
            applicationIdSuffix = ProductTypes.debugId
            versionNameSuffix = ProductTypes.debugSuffix
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

    testOptions {
        unitTests.isIncludeAndroidResources = true
        //unitTests.isReturnDefaultValues = true // Only for debugging when writing Tests
    }

    // This suppresses an error saying 'More than one file was found with OS independent path'.
    packagingOptions {
        exclude("README.txt")
        exclude("README.md")
    }
}

dependencies {

    // Enforce configurations to avoid versioning conflicts with 3rd party libs
    configurations.all {
        // Enforce latest android support annotations for all dependencies
        resolutionStrategy.force(Deps.supportAnnotations)

        //This subdependency is included in the Android SDK and can easily cause conflicts
        exclude(group = "commons-logging", module = "commons-logging")
    }

    //General, JIC you include a local library
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Kotlin
    implementation(Deps.Kotlin.kotlinStd)
    implementation(Deps.Kotlin.kotlin)

    //AndroidX
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.ktxCore)
    implementation(Deps.AndroidX.UI.constraintLayout)

    // Material
    implementation(Deps.material)

    //Testing
    testImplementation(Deps.AndroidX.Test.archTest)
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.AndroidX.Test.junitExt)
    androidTestImplementation(Deps.Test.Espresso.core)
    testImplementation(Deps.Test.Mockk.core)
    androidTestImplementation(Deps.Test.Mockk.core)
    androidTestImplementation(Deps.Test.Mockk.android)

    //Exoplayer
    implementation(Deps.Exoplayer.core)
    implementation(Deps.Exoplayer.hls)
    implementation(Deps.Exoplayer.ui)
    implementation(Deps.Exoplayer.mediaSession)

    // Firebase BoM https://firebase.google.com/docs/android/learn-more#bom
    // Notice that Platforms can be declared inline
    implementation(platform(Deps.Firebase.firebaseBom))
    implementation(Deps.Firebase.firebasePerformance)

    // Debug
    debugImplementation(Deps.DevTools.leakCanary)
}

// Helpful error handling and build speed
kapt {
    useBuildCache = true
    correctErrorTypes = true
}

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(0, "seconds")
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
