plugins {
    id("spacehub.android.library")
    id("spacehub.android.hilt")
}

android {
    namespace = "com.spacehub.core.network"

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
    implementation(project(":core:common"))

    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.hilt.navigation.compose)
}
