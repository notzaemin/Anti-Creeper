import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:${project.properties["paperVersion"]}-R0.1-SNAPSHOT")
    compileOnly("io.github.monun:kommand-core:${project.properties["kommandVersion"]}")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
        filteringCharset = "UTF-8"
    }
    register<Jar>("output") {
        archiveBaseName.set(project.name)
        archiveVersion.set("")
        archiveClassifier.set("")
        from(sourceSets["main"].output)
        doLast {
            copy {
                from(archiveFile)
                into(File(rootDir, ".output"))
            }
        }
    }
    register<Jar>("paperTest") {
        archiveBaseName.set(project.name)
        archiveVersion.set("")
        archiveClassifier.set("")
        from(sourceSets["main"].output)
        doLast {
            copy {
                from(archiveFile)
                val dir = File(rootDir, ".server/plugins")
                into(if (File(dir, archiveFileName.get()).exists()) File(dir, "update") else dir)
            }
        }
    }
}