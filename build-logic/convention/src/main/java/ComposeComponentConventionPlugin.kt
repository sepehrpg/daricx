import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** Add Plugin Compose Library And Then All Compose Dependencies */
class ComposeComponentConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            /** Add Compose Dependencies */
            dependencies {
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

                add("implementation", libs.findLibrary("androidx.activity.compose").get())
                add("implementation", libs.findLibrary("androidx.compose.foundation").get())
                add("implementation", libs.findLibrary("androidx.compose.foundation.layout").get())
                add("implementation", libs.findLibrary("androidx.compose.material.iconsExtended").get())
                add("implementation", libs.findLibrary("androidx.compose.material3").get())
                add("implementation", libs.findLibrary("androidx.compose.material3.windowSizeClass").get())
                add("implementation", libs.findLibrary("androidx.compose.runtime").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.tooling").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.util").get())
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("coil.kt.compose").get())

                add("implementation", libs.findLibrary("androidx.compose.ui.ui.graphics").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.ui").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.util").get())

                //Test
                add("implementation", libs.findLibrary("androidx.compose.ui.test").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.testManifest").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.ui.test.junit4").get())

                /*implementation(libs.androidx.adaptive)
                implementation (libs.androidx.adaptive.layout)
                implementation (libs.androidx.adaptive.navigation)*/
            }
        }
    }
}
