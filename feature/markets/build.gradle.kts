plugins {

    //Test
    //alias(libs.plugins.project.android.application)
    //alias(libs.plugins.project.android.application.compose)

    alias(libs.plugins.project.android.feature)
    //alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.daricx.markets"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }


}

dependencies {
    implementation(project(":core:ui"))
    //implementation(project(":feature:settings"))
    //implementation(project(":core:data"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)



}