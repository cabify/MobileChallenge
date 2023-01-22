plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = ProjectConfig.namespace
  compileSdk = ProjectConfig.compileSdkVersion

  defaultConfig {
    applicationId = ProjectConfig.applicationId
    minSdk = ProjectConfig.minSdkVersion
    targetSdk = ProjectConfig.targetSdkVersion
    versionCode = ProjectConfig.versionCode
    versionName = ProjectConfig.versionName

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    viewBinding = true
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
