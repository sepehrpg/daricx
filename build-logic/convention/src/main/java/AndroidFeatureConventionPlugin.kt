
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

/** ??????? */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {

                //REAL
                //Library Feature Module
                //apply("project.android.library")
                //apply("project.android.library.compose")
                apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

                //JUST FOR TEST
                //Application Feature Module
                apply("project.android.application")
                apply("project.android.application.compose")


                apply("project.compose.component")
                apply("project.android.hilt")
            }

            /*extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
                }
            }*/

            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:data"))
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                //add("implementation", project(":core:ui"))

                //add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                //add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                //add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
            }
        }
    }
}
