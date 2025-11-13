plugins {
    alias(libs.plugins.project.android.library) /** android library convention */
    alias(libs.plugins.project.android.hilt) /** hilt  convention */
    //id("kotlinx-serialization")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.network"

    defaultConfig {
        // Specify the custom Hilt test runner
        testInstrumentationRunner = "com.example.network.HiltTestRunner"
    }
}

dependencies {
    api(project(":core:model"))
    implementation(project(":core:common"))


    /** Core */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)



    /** Unit Test */
    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.okhttp3.core)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.robolectric)
    testImplementation(libs.timber)
    testImplementation(libs.androidx.test.core)

    /** Instrumentation Test */
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Chucker - only debug
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")
}