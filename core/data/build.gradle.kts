plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.hilt)
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    api(project(":core:model"))

    
    implementation(libs.kotlinx.datetime)

    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)


    // Paging
    // ............................................................................................
    api(libs.androidx.paging.runtime)
    // alternatively - without Android dependencies for tests
    testImplementation(libs.androidx.paging.common)
    // optional - RxJava2 support
    //implementation(libs.androidx.paging.rxjava2)
    // optional - RxJava3 support
    //implementation(libs.androidx.paging.rxjava3)
    // optional - Guava ListenableFuture support
    //implementation(libs.androidx.paging.guava)
    // optional - Jetpack Compose integration
    api(libs.androidx.paging.compose)
    // ............................................................................................

}