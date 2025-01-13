plugins {
    kotlin("jvm")
}

val kubernetesVersion: String by extra("22.0.0")
val kotlinLoggingVersion: String by extra("7.0.0")
val slf4jSimpleVersion: String by extra("2.0.13")
val kotlinVersion: String by extra("2.1.0")
val kotlinCoroutinesVersion: String by extra("1.10.1")

allprojects {
    repositories {
        mavenCentral()
    }
}