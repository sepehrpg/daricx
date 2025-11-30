import com.example.convention.libs
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin to add Koin + Koin Annotations + KSP config to Android modules.
 * Simply apply this plugin inside any module's build.gradle.kts.
 */
class AndroidKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            // 1) Apply KSP plugin so the "ksp" extension exists
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            // 2) Configure KSP extension for Koin config check
            extensions.configure<KspExtension>("ksp") {
                arg("KOIN_CONFIG_CHECK", "true")
            }

            // 3) Add Koin dependencies
            dependencies {

                // --- Koin BOM ---
                "implementation"(platform(libs.findLibrary("koin.bom").get()))

                // --- Core / Android ---
                "implementation"(libs.findLibrary("koin.core").get())
                "implementation"(libs.findLibrary("koin.android").get())

                // --- Jetpack Compose support ---
                "implementation"(libs.findLibrary("koin.androidx.compose").get())

                // --- Koin Annotations + KSP compiler ---
                "implementation"(libs.findLibrary("koin.annotations").get())
                "ksp"(libs.findLibrary("koin.ksp.compiler").get())

                // --- Test dependencies ---
                "testImplementation"(libs.findLibrary("koin.test").get())
            }
        }
    }
}
