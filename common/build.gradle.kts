plugins {
    id("spacehub.android.library")
    id("spacehub.android.hilt")
}

android {
    namespace = "com.spacehub.common"
}

dependencies {
    implementation(project(":common:models"))
    implementation(project(":core"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
