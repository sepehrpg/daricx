plugins {
    alias(libs.plugins.project.android.library)
}

android {
    namespace = "com.example.model"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}