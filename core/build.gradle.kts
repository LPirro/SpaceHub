import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.lpirro.spacehub.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "LAUNCH_LIBRARY_BASE_URL", "\"https://ll.thespacedevs.com\"")
            buildConfigField("String", "LAUNCH_LIBRARY_API_VERSION", "\"2.2.0\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_BASE_URL", "\"https://api.spaceflightnewsapi.net\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_API_VERSION", "\"v4\"")
        }
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "LAUNCH_LIBRARY_BASE_URL", "\"https://lldev.thespacedevs.com\"")
            buildConfigField("String", "LAUNCH_LIBRARY_API_VERSION", "\"2.2.0\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_BASE_URL", "\"https://api.spaceflightnewsapi.net\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_API_VERSION", "\"v4\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

dependencies {

    implementation(project(":common:models"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.okhttp.interceptor)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.joda.time)

    // Hilt
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
