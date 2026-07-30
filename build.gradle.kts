buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath("com.github.recloudstream:gradle:3.0.1")
    }
}

apply(plugin = "com.android.library")
apply(plugin = "kotlin-android")
apply(plugin = "com.lagradost.cloudstream3.gradle")

cloudstream {
    setRepoUrl("https://github.com/eksborgs/uncenx-extension")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.recloudstream:cloudstream:3.0.1")
    implementation("org.jsoup:jsoup:1.15.4")
}
