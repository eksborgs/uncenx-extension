import com.android.build.gradle.LibraryExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
    }
}

apply(plugin = "com.android.library")
apply(plugin = "kotlin-android")
apply(plugin = "com.lagradost.cloudstream3.gradle")

// Penapisan refleksif tepat: Wajibkan 1 parameter String sahaja
val cloudstreamExt = extensions.findByName("cloudstream")
if (cloudstreamExt != null) {
    val targetUrl = "https://github.com/eksborgs/uncenx-extension"
    val repoMethod = cloudstreamExt.javaClass.methods.firstOrNull { method ->
        method.parameterTypes.size == 1 && 
        method.parameterTypes[0] == String::class.java && 
        (method.name.startsWith("set") || method.name.contains("repo", ignoreCase = true))
    }
    
    if (repoMethod != null) {
        repoMethod.invoke(cloudstreamExt, targetUrl)
    } else {
        cloudstreamExt.javaClass.declaredFields.firstOrNull { 
            it.name.contains("repo", ignoreCase = true) 
        }?.let { field ->
            field.isAccessible = true
            field.set(cloudstreamExt, targetUrl)
        }
    }
}

configure<LibraryExtension> {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    val implementation by configurations
    implementation("com.github.recloudstream:cloudstream:master-SNAPSHOT")
    implementation("org.jsoup:jsoup:1.15.4")
}
