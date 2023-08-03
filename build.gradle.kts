plugins {
    kotlin("jvm") version "1.8.21"
}

repositories {
    maven("https://nexus.cyanbukkit.cn/repository/maven-public")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

version = "1.0.0"


kotlin {
    jvmToolchain (8)
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }

}