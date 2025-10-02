plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // El plugin Safe Args ya está aquí, ¡Bien!

    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
}
// build.gradle.kts (Proyecto: ComercioESQ)


android {
    namespace = "com.example.comercioesq"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.comercioesq"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ===============================================
    // ¡NUEVO BLOQUE: HABILITAR VIEWBINDING!
    // ===============================================
    buildFeatures {
        viewBinding = true
    }
    // ===============================================

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
    // -------------------------------------------------------------
    // 💡 AÑADIDO: Dependencias de Navigation Component
    // -------------------------------------------------------------
    val nav_version = "2.7.7" // Usa la versión que has estado usando

    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // 💡 AÑADIDO: Procesador de Anotaciones de Safe Args
    //kapt("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

    // Dependencias de MVVM (Si no estaban en el libs.versions.toml)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Dependencias que has usado antes (Ej. Material Design)
    implementation("com.google.android.material:material:1.11.0")
    // ... otras dependencias que tengas
}