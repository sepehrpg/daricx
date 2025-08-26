/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.example.convention.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin) //ApplicationExtension
    compileOnly(libs.android.tools.common) //SdkConstants
    compileOnly(libs.firebase.crashlytics.gradlePlugin) //CrashlyticsExtension
    compileOnly(libs.firebase.performance.gradlePlugin) //CrashlyticsExtension
    compileOnly(libs.kotlin.gradlePlugin) //KotlinCompile
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin) //RoomExtension
    implementation(libs.truth) //assertWithMessage
    //
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    
    plugins {

        register("androidApplicationCompose") {
            id = "project.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "project.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("composeComponent") {
            id = "project.compose.component"
            implementationClass = "ComposeComponentConventionPlugin"
        }

        register("androidFeature") {
            id = "project.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidHilt") {
            id = "project.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "project.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "project.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidRoom") {
            id = "project.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("androidLint") {
            id = "project.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
    }
    
}
