pluginManagement {
    repositories {
        google()
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
rootProject.name = "MobileChallenge"
include(
    ":app",
    ":core:base",
    ":core:network",
    ":core:persistence",
    ":core:product",
    ":features:cart",
    ":features:home",
    ":shared:common-ui",
)
include(":libraries:utils")
include(":shared:cart")
