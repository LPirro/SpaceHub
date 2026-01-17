plugins {
    id("spacehub.android.library")
    id("spacehub.android.hilt")
}

android {
    namespace = "com.spacehub.common"
}

dependencies {
    implementation(project(":common:models"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.paging.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
