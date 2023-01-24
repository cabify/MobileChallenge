plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")


android {
    defaultConfig {
        applicationId = ProjectConfig.applicationId
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", ProjectConfig.baseUrl)
    }
}

dependencies {
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.coreKtx)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.liveDataKtx)
    implementation(CoreDependencies.navigationKtx)
    implementation(CoreDependencies.koinAndroid)
    implementation(CoreDependencies.viewModelKtx)
    implementation(CoreDependencies.rxJava3)
    implementation(CoreDependencies.rxAndroid3)
    implementation(project(":features:cart"))
    implementation(project(":features:home"))
    implementation(project(":shared:common-ui"))
    implementation(project(":core:base"))
    implementation(project(":core:network"))
    implementation(project(":core:product"))

    implementation(UIDependencies.constraintLayout)
    implementation(UIDependencies.material)

    testImplementation(TestingDependencies.JUnit)

    androidTestImplementation(TestingDependencies.espresso)
    androidTestImplementation(TestingDependencies.JUnitExt)
}
