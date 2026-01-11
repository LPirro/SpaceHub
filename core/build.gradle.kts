plugins {
    id("spacehub.android.library")
    id("spacehub.android.compose")
    id("spacehub.android.hilt")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.spacehub.core"

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            buildConfigField("String", "LAUNCH_LIBRARY_BASE_URL", "\"https://ll.thespacedevs.com\"")
            buildConfigField("String", "LAUNCH_LIBRARY_API_VERSION", "\"2.2.0\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_BASE_URL", "\"https://api.spaceflightnewsapi.net\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_API_VERSION", "\"v4\"")
        }
        debug {
            buildConfigField("String", "LAUNCH_LIBRARY_BASE_URL", "\"https://lldev.thespacedevs.com\"")
            buildConfigField("String", "LAUNCH_LIBRARY_API_VERSION", "\"2.2.0\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_BASE_URL", "\"https://api.spaceflightnewsapi.net\"")
            buildConfigField("String", "SPACEFLIGHT_NEWS_API_VERSION", "\"v4\"")
        }
    }
}

dependencies {
    implementation(project(":common:models"))

    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.okhttp.interceptor)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.joda.time)
    implementation(libs.androidx.paging.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
