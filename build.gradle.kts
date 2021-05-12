// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.Id.dependencyUpdate) version Versions.dependencyUpdate
}

buildscript {
    repositories {
        google()
        mavenCentral()
        // TODO : Bintray still tied to jCenter
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
            content {
                includeGroup("org.jetbrains.kotlin")
            }
        }
        maven { url = uri ("https://dl.bintray.com/kotlin/kotlinx")} // TODO : Bintray
        maven{ url = uri("https://dl.bintray.com/kotlin/dokka")} // TODO : Bintray
    }
    dependencies {
        classpath(Plugins.androidTools)
        classpath(Plugins.kotlinGradle)
        classpath(Plugins.firebasePerformance)
        //Dokka Support for autogen Documentation
        classpath(Plugins.dokka)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri ("https://dl.bintray.com/kotlin/kotlinx")} // TODO : Bintray
        maven{ url = uri("https://dl.bintray.com/kotlin/dokka")} // TODO : Bintray
    }
}

// Set each module in the project to use Java 8
// and remove compiler warnings for ExperimentalCoroutinesApi automatically
// (so you don't need to annotate each)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs =
            freeCompilerArgs + "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
