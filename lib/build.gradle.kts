plugins {
    kotlin("jvm")
}

group = "com.github"
version = "unspecified"

val kubernetesVersion: String by rootProject.extra
val kotlinLoggingVersion: String by rootProject.extra
val slf4jSimpleVersion: String by rootProject.extra

dependencies {
    implementation("io.kubernetes:client-java:${kubernetesVersion}")
    implementation("io.github.oshai:kotlin-logging:${kotlinLoggingVersion}")
    implementation("org.slf4j:slf4j-simple:${slf4jSimpleVersion}")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}