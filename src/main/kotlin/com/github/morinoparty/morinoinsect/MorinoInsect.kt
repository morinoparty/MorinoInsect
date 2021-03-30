package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import br.com.devsrsouza.kotlinbukkitapi.serialization.architecture.config
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Message
import com.github.morinoparty.morinoinsect.configuration.Standard
import org.bukkit.plugin.Plugin
import java.nio.file.Path
import java.nio.file.Paths

class MorinoInsect : KotlinPlugin() {
    val standard: Standard
    val insectTypeTable: InsectTypeTable
    val message: Message

    override fun onPluginEnable() {

        server.pluginManager.apply {
            // リスナーの登録
        }

        val manager = PaperCommandManager(this)
        val mainCommand = MainCommand(this)
        manager.registerCommand(mainCommand)
        manager.commandCompletions.registerAsyncCompletion("insect") { insectTypeTable.insectMap.values.map { it.name } }
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }

    init {
        val configPath = Paths.get("config.yml")
        val localePath = Paths.get("locale")
        canSaveDefault(this, configPath)
        canSaveDefault(this, localePath.resolve("insect.yml"))
        canSaveDefault(this, localePath.resolve("message.yml"))

        standard = this.config(configPath.toString(), Standard(), Standard.serializer()).config
        insectTypeTable = this.config(localePath.resolve("insect.yml").toString(), InsectTypeTable(), InsectTypeTable.serializer(), alwaysRestoreDefaults = false).config
        message = this.config(localePath.resolve("message.yml").toString(), Message(), Message.serializer()).config
    }

    private fun canSaveDefault(plugin: Plugin, resourcePath: Path) {
        val dataPath = plugin.dataFolder.toPath()
        val configurationFile = dataPath.resolve(resourcePath).toFile()

        if (!configurationFile.exists()) {
            plugin.saveResource(resourcePath.toString(), false)
        }
    }
}
