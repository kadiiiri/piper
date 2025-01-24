plugins {
    kotlin("jvm")
}

group = "com.github.piper"
version = "unspecified"

val kotlinVersion: String by rootProject.extra

dependencies {
    implementation(project(":piper-definition"))

    implementation("org.jetbrains.kotlin:kotlin-scripting-common:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:${kotlinVersion}")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}