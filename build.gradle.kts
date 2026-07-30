plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.recloudstream:cloudstream:master-SNAPSHOT")
}
