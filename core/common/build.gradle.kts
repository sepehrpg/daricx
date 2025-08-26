plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.hilt) /** hilt  convention */
    //alias(libs.plugins.kotlinAndroid)
    //alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.common"

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}