pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.10"
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}


rootProject.name = "piper"
include("piper-definition")
include("piper-host")
include("piper-lib")
