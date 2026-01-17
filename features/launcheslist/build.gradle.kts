plugins {
    id("spacehub.android.library")
    id("spacehub.android.compose")
    id("spacehub.android.hilt")
}

android {
    namespace = "com.spacehub.launcheslist"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":core:common"))
    implementation(project(":common"))
    implementation(project(":common:models"))

    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.material.icons.core)

    testImplementation(libs.junit)
    testImplementation(project(":common"))
    testImplementation(project(":test-util"))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.androidx.arch.core.testing)
}
