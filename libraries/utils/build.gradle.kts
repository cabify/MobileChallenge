plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")

dependencies {
    implementation(CoreDependencies.appCompat)
    implementation(UIDependencies.material)
    testImplementation(TestingDependencies.JUnit)
}