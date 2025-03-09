import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

fun getLocalProperty(key: String): String? {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
        return properties.getProperty(key)
    }
    return null
}

val apiKey: String? = getLocalProperty("API_KEY")
val baseUrl: String? = getLocalProperty("BASE_URL")
val basePosterPath: String? = getLocalProperty("BASE_POSTER_PATH")
val baseBackdropPath: String? = getLocalProperty("BASE_BACKDROP_PATH")

android {
    namespace = "com.sapan.tmdbapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sapan.tmdbapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"${apiKey}\"")
        buildConfigField("String", "BASE_URL", "\"${baseUrl}\"")
        buildConfigField("String", "BASE_POSTER_PATH", "\"${basePosterPath}\"")
        buildConfigField("String", "BASE_BACKDROP_PATH", "\"${baseBackdropPath}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.car.ui.lib)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)


    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.glide)

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.coil)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation (libs.logging.interceptor)

    implementation (libs.androidx.paging.runtime.ktx)

    implementation(libs.moshi.kotlin)
    implementation (libs.converter.moshi)

    implementation(libs.coil)
}