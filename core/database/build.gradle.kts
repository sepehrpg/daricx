@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.room)
    //alias(libs.plugins.project.android.hilt)
    alias(libs.plugins.project.android.koin)

}

android {
    namespace = "com.example.database"
}

dependencies {

    api(project(":core:model"))


    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    //Kotlin Date Time
    implementation(libs.kotlinx.datetime)



}