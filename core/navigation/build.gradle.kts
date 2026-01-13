plugins {
    id("spacehub.android.library")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.spacehub.core.navigation"
}

dependencies {
    implementation(project(":common:models"))

    implementation(libs.kotlinx.serialization.json)
}
