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
    implementation(CoreDependencies.koinAndroid)
    implementation(CoreDependencies.viewModelKtx)
    implementation(CoreDependencies.rxJava3)
    implementation(CoreDependencies.rxAndroid3)
    implementation(project(":core:base"))
    implementation(project(":data:product"))
    implementation(project(":common-ui"))
    implementation(project(":data:cart"))
    implementation(project(":utils"))

    implementation(UIDependencies.coil)
    implementation(UIDependencies.constraintLayout)
    implementation(UIDependencies.material)

    testImplementation(TestingDependencies.JUnit)
    testImplementation(TestingDependencies.mockitoKotlin)
    testImplementation(TestingDependencies.JUnitExt)
    testImplementation(TestingDependencies.androidArchTest)

    androidTestImplementation(TestingDependencies.espresso)
    androidTestImplementation(TestingDependencies.JUnitExt)
}