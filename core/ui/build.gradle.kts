plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.library.compose)
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Chart Vico
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m2)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.multiplatform)
    implementation(libs.vico.views)
}