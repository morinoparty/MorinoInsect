package com.github.morinoparty.morinoinsect.configuration

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import br.com.devsrsouza.kotlinbukkitapi.serialization.SerializationConfig
import br.com.devsrsouza.kotlinbukkitapi.serialization.architecture.config
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.nio.file.Path
import java.nio.file.Paths

object Config {
    lateinit var standard: Standard
    lateinit var insectTypeTable: InsectTypeTable
    lateinit var message: Message

    fun load(plugin: KotlinPlugin) {
        val paths = listOf("config.yml", "insect.yml", "message.yml")
        val localePath = Paths.get("locale")
        for (path in paths) canSaveDefault(plugin, localePath.resolve(path))

        standard = plugin.config("config.yml", Standard(), Standard.serializer()).config
        insectTypeTable = plugin.config(localePath.resolve("insect.yml").toString(), InsectTypeTable(), InsectTypeTable.serializer(), alwaysRestoreDefaults = false).config
        message = plugin.config(localePath.resolve("message.yml").toString(), Message(), Message.serializer()).config
    }

    private fun canSaveDefault(plugin: Plugin, resourcePath: Path) {
        val dataPath = plugin.dataFolder.toPath()
        val configurationFile = dataPath.resolve(resourcePath).toFile()

        if (!configurationFile.exists()) {
            plugin.saveResource(resourcePath.toString(), false)
        }
    }
}