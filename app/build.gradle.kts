import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.project.android.application) // android application(configuration)
    alias(libs.plugins.project.android.application.compose) //compose application
    alias(libs.plugins.project.compose.component) // all jetpack compose component
    //alias(libs.plugins.project.android.lint) // lint
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

    // --- Load signing configuration from keystore.properties ---
    // keystore.properties should contain: storeFile, storePassword, keyAlias, keyPassword
    val keystoreProps = Properties().apply {
        val f = rootProject.file("keystore.properties")
        if (f.exists()) load(FileInputStream(f))
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProps["storeFile"] ?: error("storeFile missing"))
            storePassword = (keystoreProps["storePassword"] ?: error("storePassword missing")).toString()
            keyAlias = (keystoreProps["keyAlias"] ?: error("keyAlias missing")).toString()
            keyPassword = (keystoreProps["keyPassword"] ?: error("keyPassword missing")).toString()
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }


    buildTypes {
        release {
            // Disable code shrinking for now; enable when ready for production
            isMinifyEnabled = false
            // ProGuard / R8 rules for release builds
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Apply release signing configuration
            signingConfig = signingConfigs.getByName("release")
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