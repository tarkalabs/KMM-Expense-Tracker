plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.0").apply(false)
    id("com.android.library").version("7.4.0").apply(false)
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
    id("com.squareup.sqldelight").version("1.5.5").apply(false)
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.3" apply false
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
