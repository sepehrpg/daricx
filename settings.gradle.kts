pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Daricx"
include(":app")
include(":core:designsystem")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:data")
include(":core:common")
include(":core:model")
include(":feature:markets")
include(":core:ui")
