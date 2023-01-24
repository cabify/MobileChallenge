plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")

dependencies {
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.coreKtx)
    implementation(CoreDependencies.koinAndroid)
    implementation(CoreDependencies.rxJava3)
    implementation(project(":core:base"))
    implementation(project(":core:persistence"))

    testImplementation(TestingDependencies.JUnit)
}