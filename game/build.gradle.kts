import org.gradle.internal.os.OperatingSystem

val jomlVersion = "1.9.14"

plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "hamburg.remme.lwjgl.ParticleGameKt"
    if (OperatingSystem.current() == OperatingSystem.MAC_OS) {
        applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
    }
}

dependencies {
    compile(project(":engine"))
    compile(kotlin("stdlib-jdk8"))
    implementation("org.joml", "joml", jomlVersion)
}
