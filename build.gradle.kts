plugins {
    kotlin("jvm")
}

val kubernetesVersion: String by extra("7.0.1")
val kotlinLoggingVersion: String by extra("7.0.0")
val kotlinVersion: String by extra("2.1.20")
val kotlinCoroutinesVersion: String by extra("1.10.1")

allprojects {
    repositories {
        mavenCentral()
    }
}
