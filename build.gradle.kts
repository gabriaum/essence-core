plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.gabriaum.afterjournal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

val spigotVersion = "1.21.5"
val lombokVersion = "1.18.36"
dependencies {
    compileOnly("org.spigotmc:spigot-api:$spigotVersion-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:$spigotVersion-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

bukkit {
    main = "com.gabriaum.essencecore.EssenceCore"
    name = "EssenceCore"
    version = project.version.toString()
    website = "www.gabriaum.com"
    author = "gabriaum"
}

tasks.shadowJar {
    archiveFileName.set("EssenceCore.jar")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}