import com.matthewprenger.cursegradle.CurseArtifact
import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.Options

plugins {
    id("net.neoforged.moddev") version "0.1.126"
    id("com.matthewprenger.cursegradle") version "1.4.0"
    id("com.modrinth.minotaur") version "2.+"
}

group = "com.mikn.lava_walker"

val minecraft_version: String by extra
val archives_base_name: String by extra
val neo_version: String by extra
val mod_version: String by extra
val mod_id: String by extra
val curseforge_project_id: String by extra
val modrinth_project_id: String by extra

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

neoForge {
    version = neo_version
    validateAccessTransformers = true

    parchment {
        minecraftVersion = "1.21"
        mappingsVersion = "2024.07.28"
    }

    runs {
        register("client") {
            client()
        }
        register("data") {
            data()
            programArguments.addAll(
                "--mod", "lava_walker",
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }
        register("server") {
            server()
        }
    }

    mods {
        register("lava_walker") {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main {
    resources {
        srcDir("src/generated/resources")
    }
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = mutableMapOf(
        "minecraft_version"       to project.property("minecraft_version"),
        "minecraft_version_range" to project.property("minecraft_version_range"),
        "neo_version"             to project.property("neo_version"),
        "neo_version_range"       to project.property("neo_version_range"),
        "loader_version_range"    to project.property("loader_version_range"),
        "mod_version"             to project.property("mod_version"),
        "mod_id"                  to project.property("mod_id"),
        "mod_name"                to project.property("archives_base_name"),
        "mod_authors"             to project.property("mod_authors"),
    )

    inputs.properties(replaceProperties)

    filesMatching(listOf("META-INF/neoforge.mods.toml")) {
        expand(replaceProperties)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

curseforge {
    apiKey = System.getenv("curse_api_key") ?: ""
    project(closureOf<CurseProject> {
        id = curseforge_project_id
        releaseType = "release"
        addGameVersion("1.21")
        addGameVersion("NeoForge")
        mainArtifact(tasks.jar.get().archiveFile, closureOf<CurseArtifact>{
            displayName = "${archives_base_name} ${mod_version}"
        })
    })
    options(closureOf<Options> {
        forgeGradleIntegration = false
        javaVersionAutoDetect = true
    })
}

tasks.curseforge {
    dependsOn("build")
}

modrinth {
    token.set(System.getenv("modrinth_token"))
    projectId.set(modrinth_project_id)
    versionName.set("${archives_base_name} ${mod_version}")
    versionNumber.set(mod_version)
    versionType.set("release")
    loaders.add("neoforge")
    gameVersions.addAll("1.21")
    uploadFile.set(tasks.jar.get())
}

tasks.modrinth.get().dependsOn(tasks.jar)