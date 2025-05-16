
import io.fabric8.crd.generator.collector.CustomResourceCollector
import io.fabric8.crdv2.generator.CRDGenerationInfo
import io.fabric8.crdv2.generator.CRDGenerator
import java.nio.file.Files
import org.gradle.api.internal.tasks.JvmConstants

plugins {
    kotlin("jvm")
}

group = "com.github.piper"
version = "unspecified"

val kubernetesVersion: String by rootProject.extra
val kotlinLoggingVersion: String by rootProject.extra
val kotlinCoroutinesVersion: String by rootProject.extra

dependencies {
    implementation(project(":piper-common"))
    implementation("io.fabric8:openshift-client:${kubernetesVersion}")
    implementation("io.github.oshai:kotlin-logging:${kotlinLoggingVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}

buildscript {
    dependencies {
        classpath("io.fabric8:crd-generator-api-v2:7.0.0")
        classpath("io.fabric8:crd-generator-collector:7.0.0")
    }
}

tasks.register("generateCrds") {
    description = "Generate CRDs from compiled custom resource classes"
    group = "crd"

    dependsOn("compileKotlin")

    val sourceSet = project.sourceSets["main"]

    val compileClasspathElements = sourceSet.compileClasspath.map { e -> e.absolutePath }

    val outputClassesDirs = sourceSet.output.classesDirs
        .filter { f -> f.absolutePath.contains("kotlin") }

    val outputClasspathElements = outputClassesDirs
        .map { d -> d.absolutePath }
        .filter { p -> p.contains("kotlin") }

    println(outputClasspathElements)

    // Print out the directories to help debug
    outputClassesDirs.forEach { dir ->
        println("Output classes dir: ${dir.absolutePath}")
    }

    val classesDir = project.file("${project.buildDir}/classes/kotlin/main")
    val filesToScan = listOf(outputClassesDirs, listOf(classesDir)).flatten()
    val classpathElements = listOf(outputClasspathElements, compileClasspathElements).flatten()

    val outputDir = sourceSet.output.resourcesDir

    doLast {
        Files.createDirectories(outputDir!!.toPath())

        val collector = CustomResourceCollector()
            .withParentClassLoader(Thread.currentThread().contextClassLoader)
            .withClasspathElements(classpathElements)
            .withFilesToScan(filesToScan)

        val crdGenerator = CRDGenerator()
            .customResourceClasses(collector.findCustomResourceClasses())
            .inOutputDir(outputDir)

        val crdGenerationInfo: CRDGenerationInfo = crdGenerator.detailedGenerate()

        crdGenerationInfo.crdDetailsPerNameAndVersion.forEach { (crdName, versionToInfo) ->
            println("Generated CRD $crdName:")
            versionToInfo.forEach { (version, info) -> println(" $version -> ${info.filePath}") }
        }
    }
}

tasks.named(JvmConstants.CLASSES_TASK_NAME) {
    finalizedBy("generateCrds")
}

tasks.test {
    useJUnitPlatform()
}
