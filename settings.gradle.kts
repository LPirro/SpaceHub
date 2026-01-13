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

rootProject.name = "Spacehub"
include(":app")
include(":features:launches")
include(":features:news")
include(":features:launchdetail")
include(":features:launcheslist")
include(":common")
include(":common:models")
include(":test-util")

// Core submodules
include(":core:design")
include(":core:common")
include(":core:network")
include(":core:navigation")
