plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.hilt)
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:network"))

    
    implementation(libs.kotlinx.datetime)

    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

}