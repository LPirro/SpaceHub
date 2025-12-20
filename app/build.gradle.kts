plugins {
    id("spacehub.android.application")
    id("spacehub.android.compose")
    id("spacehub.android.hilt")
}

android {
    namespace = "com.lpirro.spacehub"

    defaultConfig {
        applicationId = "com.lpirro.spacehub"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":launches"))
    implementation(project(":launchdetail"))
    implementation(project(":news"))
    implementation(project(":core"))

    implementation(libs.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.browser)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
