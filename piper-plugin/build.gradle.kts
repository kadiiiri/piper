plugins {
  id("java")
  kotlin("jvm")
  id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.github.piper"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  intellijPlatform {
    defaultRepositories()
  }
}

val kotlinVersion: String by rootProject.extra

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
  implementation(project(":piper-definition"))
  implementation(project(":piper-lib"))

  implementation("org.jetbrains.kotlin:kotlin-scripting-common:${kotlinVersion}")
  implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:${kotlinVersion}")
  implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:${kotlinVersion}")
  implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:${kotlinVersion}")
  implementation("org.jetbrains.kotlin:kotlin-scripting-ide-services:${kotlinVersion}")

  intellijPlatform {
    create("IU", "2025.1.1.1")
    bundledPlugin("com.intellij.java")
    bundledPlugin("org.jetbrains.kotlin")
    testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
  }
}

intellijPlatform {
  pluginConfiguration {
    ideaVersion {
      sinceBuild = "251"
    }


    changeNotes = """
      Initial version
    """.trimIndent()
  }
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "21"
    targetCompatibility = "21"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "21"
      languageVersion = "2.1"
      apiVersion = "2.1"
    }
  }
}
