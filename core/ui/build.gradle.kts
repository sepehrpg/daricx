plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.library.compose)
    alias(libs.plugins.project.android.hilt)
    alias(libs.plugins.project.compose.component)
}

android {
    namespace = "com.daricx.ui"
    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(project(":core:designsystem"))
    api(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Chart Vico
    api(libs.vico.compose)
    api(libs.vico.compose.m2)
    api(libs.vico.compose.m3)
    api(libs.vico.multiplatform)
    api(libs.vico.views)
}