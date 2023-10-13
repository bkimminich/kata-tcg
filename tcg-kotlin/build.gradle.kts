plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "de.kimminich.kata.tcg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
}


kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}


tasks.test  {
    useJUnitPlatform()
}