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
    "features:cart",
    "features:home"
)
include(":shared:common-ui")
include(":core:base")
include(":core:network")
