import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "${rootProject.projectDir}/shared-android-properties.gradle")

dependencies {
    testImplementation(TestingDependencies.JUnit)

    implementation("androidx.room:room-runtime:2.5.0") //TODO change this
    annotationProcessor("androidx.room:room-compiler:2.5.0")

    implementation(CoreDependencies.koinAndroid)
    implementation(CoreDependencies.rxJava3)
    implementation(CoreDependencies.converterGson)
}