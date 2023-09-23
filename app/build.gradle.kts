@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "tech.arnav.monawallpapers"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.arnav.monawallpapers"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
    }
    kotlinOptions {
        jvmTarget = "14"
    }
    android {
        buildFeatures {
            viewBinding = true
        }
    }
}

dependencies {

    implementation(libs.bundles.core)
    implementation(libs.bundles.viewmodel)
    kapt(libs.lifecycle.compiler)
    implementation(libs.constraintlayout)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.coroutines)
    implementation(libs.coil)
    implementation(libs.bundles.layout)


    testImplementation(libs.bundles.testimpl)
    androidTestImplementation(libs.bundles.androidtestimpl)
}