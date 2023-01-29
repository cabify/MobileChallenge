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
    ":shared:product",
    ":shared:cart",
    ":features:cart",
    ":features:home",
    ":common-ui",
    ":utils"
)
