// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.21" apply false
    id(CoreDependencies.benManes) version Versions.benManes
    id(CoreDependencies.graphfity) version Versions.graphfity
}


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.github.ivancarras:graphfity-plugin:" + Versions.graphfity)
    }
}

apply(plugin = "com.github.ivancarras.graphfity")

configure<com.github.ivancarras.graphfity.plugin.main.GraphfityPluginExtension> {
    nodeTypesPath.set("nodesTypes.json")
    graphImagePath.set("graphfity/")
}

