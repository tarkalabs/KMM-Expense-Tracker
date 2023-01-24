import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("com.squareup.sqldelight")
}

kotlin {
  android()

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "shared"
    }
  }

  sourceSets {
    all {
      languageSettings.apply {
        optIn("kotlin.RequiresOptIn")
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
      }
    }

    val commonMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("com.squareup.sqldelight:coroutines-extensions:1.5.5")
        implementation("io.insert-koin:koin-core:3.2.2")
        implementation("io.github.aakira:napier:2.6.1")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation("io.insert-koin:koin-test:3.2.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
        implementation("app.cash.turbine:turbine:0.12.1")
      }
    }
    val androidMain by getting {
      dependencies {
        implementation("com.squareup.sqldelight:android-driver:1.5.5")
        implementation("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
      }
    }
    val androidTest by getting {
      dependencies {
        implementation("com.squareup.sqldelight:sqlite-driver:1.5.5")
        implementation("org.robolectric:robolectric:4.9.2")
        implementation("androidx.test:core-ktx:1.5.0")
      }
    }
    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)

      dependencies {
        implementation("com.squareup.sqldelight:native-driver:1.5.5")
      }
    }
    val iosX64Test by getting
    val iosArm64Test by getting
    val iosSimulatorArm64Test by getting
    val iosTest by creating {
      dependsOn(commonTest)
      iosX64Test.dependsOn(this)
      iosArm64Test.dependsOn(this)
      iosSimulatorArm64Test.dependsOn(this)
    }
  }
}

android {
  namespace = "com.tarkalabs.expensetracker"
  compileSdk = 33
  defaultConfig {
    minSdk = 24
    targetSdk = 33
  }
}

sqldelight {
  database("ExpensesDb") {
    packageName = "com.tarkalabs.expensetracker"
    schemaOutputDirectory = file("src/commonMain/sqldelight/com/tarkalabs/expensetracker/db")
  }
}

tasks.getByName<KotlinNativeSimulatorTest>("iosSimulatorArm64Test") {
  deviceId = "iPhone 14"
}