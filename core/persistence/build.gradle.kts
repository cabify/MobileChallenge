import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")

dependencies {
    testImplementation(TestingDependencies.JUnit)
    kapt(CoreDependencies.roomCompiler)
    implementation(CoreDependencies.roomRuntime)
    implementation(CoreDependencies.roomRxJava3)

    implementation(CoreDependencies.koinAndroid)
    implementation(CoreDependencies.rxJava3)
    implementation(CoreDependencies.converterGson)
}