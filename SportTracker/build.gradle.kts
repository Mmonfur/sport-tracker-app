// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //Kotlin Symbol Processing
    alias(libs.plugins.google.devtools.ksp) apply false
    //Jetpack Navigation with Safe Arguments
    alias(libs.plugins.androidx.navigation.safe.args) apply false
}