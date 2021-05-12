plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    // TODO : Bintray
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        content {
            includeGroup("org.jetbrains.kotlin")
        }
    }
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.0-rc01")
}