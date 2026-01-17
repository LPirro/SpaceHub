plugins {
    id("spacehub.android.library")
}

android {
    namespace = "com.spacehub.core.common"
}

dependencies {
    // Date parsing
    implementation(libs.joda.time)

    // Image transformation (for RemovePaddingTransformation)
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.androidx.core.ktx)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
