plugins {
    id("spacehub.android.library")
    id("spacehub.android.compose")
}

android {
    namespace = "com.spacehub.core.design"
}

dependencies {
    implementation(project(":common:models"))
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.compose)
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.constraintlayout.compose)
}
