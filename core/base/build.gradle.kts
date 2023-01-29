plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")

dependencies {
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.coreKtx)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.liveDataKtx)
    implementation(CoreDependencies.navigationKtx)
    implementation(CoreDependencies.koinAndroid)
    implementation(CoreDependencies.viewModelKtx)
    implementation(CoreDependencies.rxAndroid3)
    implementation(CoreDependencies.rxJava3)
    implementation(project(":utils"))
    implementation(project(":common-ui"))

    implementation(UIDependencies.constraintLayout)
    implementation(UIDependencies.material)

    testImplementation(TestingDependencies.JUnit)

    androidTestImplementation(TestingDependencies.espresso)
    androidTestImplementation(TestingDependencies.JUnitExt)
}