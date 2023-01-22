plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}
apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")

android {
  namespace = ProjectConfig.namespace
  compileSdk = ProjectConfig.compileSdkVersion

  defaultConfig {
    applicationId = ProjectConfig.applicationId
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementation(CoreDependencies.appCompat)
  implementation(CoreDependencies.coreKtx)
  implementation(CoreDependencies.fragmentKtx)
  implementation(CoreDependencies.liveDataKtx)
  implementation(CoreDependencies.navigationKtx)
  implementation(CoreDependencies.viewModelKtx)
  implementation(project(":features:cart"))
  implementation(project(":features:home"))

  implementation(UIDependencies.constraintLayout)
  implementation(UIDependencies.material)

  testImplementation(TestingDependencies.JUnit)

  androidTestImplementation(TestingDependencies.espresso)
  androidTestImplementation(TestingDependencies.JUnitExt)
}
