plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}
apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.airelcamilo.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("String", "HOST_NAME", "\"api.github.com\"")
            buildConfigField("String", "TOKEN", "\"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\"")
            buildConfigField("String", "SHA256_1", "\"sha256/GyhWVHsOXNZc6tGTNd15kXF9YD0kEZaGxYn6MUva5jY=\"")
            buildConfigField("String", "SHA256_2", "\"sha256/6YBE8kK4d5J1qu1wEjyoKqzEIvyRY5HyM/NB2wKdcZo=\"")
            buildConfigField("String", "SHA256_3", "\"sha256/ICGRfpgmOUXIWcQ/HXPLQTkFPEFPoDyjvH7ohhQpjzs=\"")
            buildConfigField("String", "PASSPHRASE", "\"airel273\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("String", "HOST_NAME", "\"api.github.com\"")
            buildConfigField("String", "TOKEN", "\"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\"")
            buildConfigField("String", "SHA256_1", "\"sha256/GyhWVHsOXNZc6tGTNd15kXF9YD0kEZaGxYn6MUva5jY=\"")
            buildConfigField("String", "SHA256_2", "\"sha256/6YBE8kK4d5J1qu1wEjyoKqzEIvyRY5HyM/NB2wKdcZo=\"")
            buildConfigField("String", "SHA256_3", "\"sha256/ICGRfpgmOUXIWcQ/HXPLQTkFPEFPoDyjvH7ohhQpjzs=\"")
            buildConfigField("String", "PASSPHRASE", "\"airel273\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.room:room-ktx:2.6.1")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

    implementation("net.zetetic:android-database-sqlcipher:4.4.0")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")
}