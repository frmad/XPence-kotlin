plugins {
    id("com.quittle.setup-android-sdk") version "3.1.0"
    id("com.google.gms.google-services") version "4.4.0" apply false
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.libres).apply(false)
    alias(libs.plugins.buildConfig).apply(false)
}