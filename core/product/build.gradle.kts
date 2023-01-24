plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")

dependencies{
    testImplementation(TestingDependencies.JUnit)

    implementation(CoreDependencies.koinAndroid)
    implementation(CoreDependencies.okhttp)
    implementation(CoreDependencies.rxJava3)
    implementation(CoreDependencies.retrofit)
    implementation(CoreDependencies.retrofitRx3Adapter)
    implementation(CoreDependencies.converterGson)
    implementation(project(":libraries:utils"))
    implementation(project(":core:network"))
}