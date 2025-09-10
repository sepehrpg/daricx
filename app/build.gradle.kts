plugins {
    alias(libs.plugins.project.android.application) // android application(configuration)
    alias(libs.plugins.project.android.application.compose) //compose application
    alias(libs.plugins.project.compose.component) // all jetpack compose component
    alias(libs.plugins.project.android.lint) // lint
    alias(libs.plugins.project.android.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler) // hilt
}

android {
    namespace = "com.daricx.app"

    defaultConfig {
        applicationId = "com.daricx.app"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    /** Add Module */
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":feature:markets"))
    implementation(project(":feature:settings"))



    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.9")

    implementation(libs.material) //ex:androidx.activity.ComponentActivity
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.adaptive)
    implementation (libs.androidx.adaptive.layout)
    implementation (libs.androidx.adaptive.navigation)
    implementation(libs.kotlinx.datetime)

    // Testing
    /*testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)*/
}