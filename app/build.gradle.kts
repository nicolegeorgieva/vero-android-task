plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.devtools.ksp)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.dagger.hilt)
  alias(libs.plugins.room)
  alias(libs.plugins.kotlinx.serialization)
}

android {
  namespace = "com.example.app"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.example.app"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  kotlin {
    compilerOptions {
      optIn.add("kotlin.time.ExperimentalTime")
      optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
      optIn.add("com.google.accompanist.permissions.ExperimentalPermissionsApi")
    }
  }
  room {
    schemaDirectory("$projectDir/schemas")
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.compose.runtime)
  implementation(libs.compose.viewmodel)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.kotlinx.collections.immutable)
  implementation(libs.arrow.core)
  implementation(libs.coil)
  implementation(libs.coil.http)
  implementation(libs.dataStore)
  implementation(libs.barcode.scanning)
  implementation(libs.accompanist.permissions)
  implementation(libs.camera.core)
  implementation(libs.camera.camera2)
  implementation(libs.camera.lifecycle)
  implementation(libs.camera.view)

  // region Navigation
  implementation(libs.androidx.navigation3.ui)
  implementation(libs.androidx.navigation3.runtime)
  implementation(libs.androidx.lifecycle.viewmodel.navigation3)
  // endregion

  // region Hilt
  implementation(libs.hilt)
  implementation(libs.hilt.worker)
  ksp(libs.hilt.compiler)
  implementation(libs.hilt.navigation.compose)
  // endregion

  // region Worker
  implementation(libs.android.workmanager)
  // endregion

  // region Room
  implementation(libs.room.runtime)
  ksp(libs.room.compiler)
  implementation(libs.room.ktx)
  // endregion

  // region KotlinX Serialization
  implementation(libs.kotlinx.serialization.json)
  // endregion

  // region Ktor
  implementation(libs.ktor.client.okhttp)
  implementation(libs.ktor.content.negotiation)
  implementation(libs.ktor.serialization)
  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.serialization)
  implementation(libs.ktor.logging)
  implementation(libs.ktor.auth.plugin)
  // endregion

  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.ui.test.junit4)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  debugImplementation(libs.androidx.ui.test.manifest)
  debugImplementation(libs.androidx.ui.tooling)

  // region tests
  testImplementation(libs.kotlin.test)
  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.mockk)
  testImplementation(libs.strikt.arrow)
  testImplementation(libs.strikt.core)
  testImplementation(libs.turbine)
  // endregion
}