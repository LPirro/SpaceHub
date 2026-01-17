plugins {
    id("spacehub.android.application")
    id("spacehub.android.compose")
    id("spacehub.android.hilt")
}

android {
    namespace = "com.spacehub.app"

    defaultConfig {
        applicationId = "com.spacehub.app"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":features:launches"))
    implementation(project(":features:launcheslist"))
    implementation(project(":features:launchdetail"))
    implementation(project(":features:news"))
    implementation(project(":core:design"))
    implementation(project(":core:navigation"))
    implementation(project(":common:models"))

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
