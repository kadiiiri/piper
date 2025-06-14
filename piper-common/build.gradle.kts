plugins {
    kotlin("jvm")
}

group = "com.github.piper"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.cronutils:cron-utils:9.2.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
