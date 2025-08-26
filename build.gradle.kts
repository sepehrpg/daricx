// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false //compose compiler
    alias(libs.plugins.ksp) apply false //ksp
    alias(libs.plugins.hilt) apply false // hilt
    alias(libs.plugins.room) apply false //room
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false // serialization

    //alias(libs.plugins.dependencyGuard) apply false
}