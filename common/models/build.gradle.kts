plugins {
    id("spacehub.android.library")
}

android {
    namespace = "com.lpirro.models"
}

dependencies {
    implementation(libs.retrofit.converter.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
