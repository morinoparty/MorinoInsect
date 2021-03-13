// More about the setup here: https://github.com/DevSrSouza/KotlinBukkitAPI/wiki/Getting-Started
plugins {
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = "com.github.morinoparty"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    // minecraft
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")

    // kotlinbukkitapi with backup repo
    maven("http://nexus.devsrsouza.com.br/repository/maven-public/")

    // plugins
    maven("https://jitpack.io")
    maven("http://repo.dmulloy2.net/nexus/repository/public/")

    // Annotation Command Framework
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    // minecraft
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    // kotlinbukkitapi
    val changing = Action<ExternalModuleDependency> { isChanging = true }
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:core:0.2.0-SNAPSHOT", changing)
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:serialization:0.2.0-SNAPSHOT", changing)
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:plugins:0.2.0-SNAPSHOT", changing)
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:exposed:0.2.0-SNAPSHOT", changing)

    // plugins
    val transitive = Action<ExternalModuleDependency> { isTransitive = false }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7", transitive)
    compileOnly("net.luckperms:api:5.1", transitive)

    // Annotation Command Framework
    implementation("co.aikar:acf-paper:0.5.0-SNAPSHOT")
}

bukkit {
    main = "com.github.morinoparty.MorinoInsect"
    depend = listOf("KotlinBukkitAPI", "Vault", "LuckPerms")
    description = "Minecraft でゴージャスな虫取りを実装するプラグインです。"
    author = "morinoparty"
    website = "https://github.com/morinoparty/MorinoInsect"
}

tasks {
    compileKotlin {
        dependsOn("ktlintFormat")
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi"
    }
    shadowJar {
        relocate("co.aikar.commands", "com.github.morinoparty.morinoinsect.acf")
        classifier = null
    }
    build {
        dependsOn(shadowJar)
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(120, "seconds")
}f
