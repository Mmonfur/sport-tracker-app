plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //Kotlin Symbol Processing
    alias(libs.plugins.google.devtools.ksp)
    //Jetpack Navigation with Safe Arguments
    alias(libs.plugins.androidx.navigation.safe.args)
}

android {
    namespace = "hu.bme.aut.kliensalkalmazasok.sporttracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "hu.bme.aut.kliensalkalmazasok.sporttracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Room ORM Library
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //Jetpack Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    //MPAndroidCharts
    implementation (libs.mpandroidchart)
}