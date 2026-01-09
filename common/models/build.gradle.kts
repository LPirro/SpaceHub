plugins {
    id("spacehub.android.library")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.spacehub.common.models"
}

dependencies {
    implementation(libs.retrofit.converter.gson)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
