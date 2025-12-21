plugins {
    id("spacehub.android.library")
}

android {
    namespace = "com.spacehub.common.models"
}

dependencies {
    implementation(libs.retrofit.converter.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
