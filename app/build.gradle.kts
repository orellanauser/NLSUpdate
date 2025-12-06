plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.newlandla.nlsupdate"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.newlandla.nlsupdate"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // Note: archivesBaseName is a property of the project, not defaultConfig in KTS.
        project.setProperty("archivesBaseName", "NLSUpdate-v${versionName}-${versionCode}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // No Compose UI is used; keep features minimal

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}