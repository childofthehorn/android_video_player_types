// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.github.ben-manes.versions") version "0.38.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
        // TODO : Bintray
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
            content {
                includeGroup("org.jetbrains.kotlin")
            }
        }
        maven { url = uri ("https://dl.bintray.com/kotlin/kotlinx")} // TODO : Bintray
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("com.google.firebase:perf-plugin:1.3.5")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri ("https://dl.bintray.com/kotlin/kotlinx")}
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
