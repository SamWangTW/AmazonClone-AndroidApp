//Configure a specific module — usually your Android app, library, or feature module.
//This file tells Gradle (Android’s build system) how to compile, package, and run your Android app.
plugins {
    //It activates features Gradle needs to build your app.
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    // id("kotlin-parcelize") // optional
}

android {
    namespace = "com.sam.amazonclone"
    compileSdk = 36 //This file tells Gradle (Android’s build system) how to compile, package, and run your Android app.

    defaultConfig {
        applicationId = "com.sam.amazonclone"
        minSdk = 24 // lowest Android version your app can run on.
        targetSdk = 35 // version your app is optimized for.
        versionCode = 1
        versionName = "1.0"
        vectorDrawables { useSupportLibrary = true }
    }

    //two versions of your app
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // Useful for inspecting Compose UI trees, etc.
            isMinifyEnabled = false
        }
    }

    buildFeatures { compose = true }

    // Kotlin 17 toolchain (recommended by AGP)
    kotlin {
        jvmToolchain(17)
    }

    packaging {
        resources {
            pickFirsts += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
}

//Everything your app depends on to work.
dependencies {
    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    // ---- Jetpack Compose (BOM) ----
    implementation(platform("androidx.compose:compose-bom:2025.10.01"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.10.01"))

    implementation("androidx.activity:activity-compose:1.11.0") // uses BOM
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material:material-icons-core:1.7.5")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Navigation (Compose)
    implementation("androidx.navigation:navigation-compose:2.9.4")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // ---- Networking stack ----
    // Retrofit 3 + Kotlinx Serialization converter
    implementation(platform("com.squareup.retrofit2:retrofit-bom:3.0.0"))
    implementation("com.squareup.retrofit2:retrofit")
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization")

    // OkHttp 5 (client + logging)
    implementation(platform("com.squareup.okhttp3:okhttp-bom:5.3.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // JSON (Kotlinx Serialization)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    // ---- Images ----
    // Coil 3 for Compose
    implementation("io.coil-kt.coil3:coil-compose:3.3.0")
    // Optional: choose one network layer (OkHttp shown since we already use it)
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")

    // Testing (add more as needed)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

