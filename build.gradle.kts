plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.oakay.contracts"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.0"
    }

    namespace = "com.oakay.contracts"
}

dependencies {
    val compose_ui = "androidx.compose.ui:ui:1.5.3"
    val compose_material = "androidx.compose.material:material:1.5.3"
    val activity_compose = "androidx.activity:activity-compose:1.8.0"
    val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    val room = "androidx.room:room-runtime:2.6.1"
    val room_ktx = "androidx.room:room-ktx:2.6.1"
    val work = "androidx.work:work-runtime-ktx:2.8.1"
    val coil = "io.coil-kt:coil-compose:2.4.0"
    val exoplayer = "com.google.android.exoplayer:exoplayer:2.20.0"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:1.9.0"

    implementation(compose_ui)
    implementation(compose_material)
    implementation(activity_compose)
    implementation(lifecycle)
    implementation(room)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation(room_ktx)
    implementation(work)
    implementation(coil)
    implementation(exoplayer)
    implementation(kotlin_stdlib)

    // Google Sign-In and Drive (placeholders - developer must add correct versions and Google services)
    implementation("com.google.android.gms:play-services-auth:20.5.0")
    implementation("com.google.api-client:google-api-client-android:1.35.0")
}
