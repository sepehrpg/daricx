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



    /** Ktor  */
    // Ktor core + OkHttp engine
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.okhttp)
    // ContentNegotiation + JSON
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    // Logging + Retry
    implementation(libs.ktor.client.logging)
    //implementation(libs.ktor.client.http.retry)
    // KtorMonitor (need minSdk>=26)
    //debugImplementation(libs.ktor.monitor.logging)
    //releaseImplementation(libs.ktor.monitor.logging.no.op)
    testImplementation(libs.ktor.client.mock)


}