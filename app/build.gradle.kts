plugins {  
    kotlin("android") version "1.9.22"  
    kotlin("ksp") version "1.9.22"  
}  

android {  
    compileSdk = 34  
    defaultConfig {  
        applicationId = "com.example.batterycare"  
        minSdk = 28  
        targetSdk = 34  
        versionCode = 1  
        versionName = "1.0"  
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"  
    }  
    buildFeatures {  
        viewBinding = true  
    }  
    compileOptions {  
        sourceCompatibility = "17"  
        targetCompatibility = "17"  
    }  
}  

dependencies {  
    implementation("androidx.core:core-ktx:1.12.0")  
    implementation("androidx.appcompat:appcompat:1.6.1")  
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")  
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")  
    implementation("androidx.room:room-runtime:2.6.1")  
    implementation("androidx.room:room-ktx:2.6.1")  
    kapt("androidx.room:room-compiler:2.6.1")  
    implementation("com.google.android.material:material:1.11.0")  
}  

ksp {  
    arg("room.schemaLocation", layout.projectDir.resolve("schemas"))  
}  
