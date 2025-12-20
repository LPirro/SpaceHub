plugins {
    id("spacehub.android.library")
}

android {
    namespace = "com.spacehub.testutil"
}

dependencies {
    implementation(project(":common:models"))
}
