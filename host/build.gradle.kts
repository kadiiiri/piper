plugins {
    kotlin("jvm")
}

group = "com.github"
version = "unspecified"

val kotlinVersion: String by rootProject.extra

dependencies {
    implementation(project(":def"))

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