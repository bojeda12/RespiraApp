plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //Agregamos aqui para la navegacion
    alias(libs.plugins.jetbrainsKotlinSerialization)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.examplemvvm"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.examplemvvm"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true


    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        dataBinding = true

    }
}

dependencies {
    val room_version = "2.8.3"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit)
    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    //livedata
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.9.3")
    //fragment
    implementation("androidx.fragment:fragment-ktx:1.8.9")
    //Activity
    implementation("androidx.activity:activity-ktx:1.11.0")

    //Agregamos aqui para la navegacion
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)


    //Graficos vico
    implementation("com.patrykandpatrick.vico:core:1.14.0")
    implementation("com.patrykandpatrick.vico:compose:1.14.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.14.0")
// Room components
    implementation("androidx.room:room-runtime:${room_version}")
    ksp("androidx.room:room-compiler:$room_version")
// Kotlin Extensions and Coroutines support
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.databinding:databinding-ktx:8.1.0")
// Type converters (si usas LocalDate u otros tipos personalizados)
    implementation("androidx.room:room-paging:2.6.1") // opcional si usas Paging
    testImplementation("androidx.room:room-testing:2.6.1")
    implementation( "androidx.hilt:hilt-navigation-compose:1.1.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")

    //BOM animacion
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.animation:animation-core")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

}