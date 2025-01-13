plugins {
    kotlin("jvm")
}

group = "com.github"
version = "unspecified"

val kotlinVersion: String by rootProject.extra
val kotlinCoroutinesVersion: String by rootProject.extra

dependencies {
    implementation(project(":lib"))

    implementation("org.jetbrains.kotlin:kotlin-scripting-common:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}