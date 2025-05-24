pluginManagement {
    val kotlinVersion = "2.1.21"

    plugins {
        kotlin("jvm") version kotlinVersion
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "piper"

include(
    "piper-definition",
    "piper-host",
    "piper-lib",
    "piper-web",
    "piper-plugin",
    "piper-common"
)
