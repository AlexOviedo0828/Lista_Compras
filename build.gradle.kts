// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

// Deshabilita la aplicación del plugin 'com.google.devtools.ksp' y especifica la versión
        id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

