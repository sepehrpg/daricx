plugins {

    //Test
    //alias(libs.plugins.project.android.library)
    //alias(libs.plugins.project.android.library.compose)

    alias(libs.plugins.project.android.feature)

    //alias(libs.plugins.project.android.hilt)
}

android {
    namespace = "com.daricx.settings"
}

dependencies {
    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}