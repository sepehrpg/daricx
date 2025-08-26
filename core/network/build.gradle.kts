plugins {
    alias(libs.plugins.project.android.library) /** android library convention */
    alias(libs.plugins.project.android.hilt) /** hilt  convention */
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.network"

    defaultConfig {
        // Specify the custom Hilt test runner
        testInstrumentationRunner = "com.example.network.HiltTestRunner"
    }
}

dependencies {
    api(project(":core:common"))
    api(project(":core:model"))


    /** Core */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    //test runner
    implementation(libs.androidx.test.runner)
}