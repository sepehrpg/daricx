import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin to add Koin dependencies to Android modules.
 * Simply apply this plugin inside any module's build.gradle.kts.
 */
class AndroidKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            // Apply KSP if annotation processing is needed later
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {

                // --- Koin BOM ---
                // This ensures all Koin artifacts use the same version
                "implementation"(platform(libs.findLibrary("koin.bom").get()))

                // --- Core / Android ---
                "implementation"(libs.findLibrary("koin.core").get())
                "implementation"(libs.findLibrary("koin.android").get())

                // --- Jetpack Compose support ---
                "implementation"(libs.findLibrary("koin.androidx.compose").get())

                // Optional older compose artifacts (only if needed)
                // "implementation"(libs.findLibrary("koin.compose").get())
                // "implementation"(libs.findLibrary("koin.compose.viewmodel").get())

                // --- Test dependencies ---
                "testImplementation"(libs.findLibrary("koin.test").get())

                // If you add JUnit4 integration later:
                // "androidTestImplementation"(libs.findLibrary("koin.test.junit4").get())
            }
        }
    }
}
