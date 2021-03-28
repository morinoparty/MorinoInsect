package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import br.com.devsrsouza.kotlinbukkitapi.serialization.architecture.config
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Message
import com.github.morinoparty.morinoinsect.configuration.Standard
import java.nio.file.Paths

class MorinoInsect : KotlinPlugin() {
    lateinit var standard: Standard
    lateinit var insectTypeTable: InsectTypeTable
    lateinit var message: Message

    override fun onPluginEnable() {

        applyConfig(this)

        // Plugin startup logic
        val manager = PaperCommandManager(this)
        val mainCommand = MainCommand(this)
        manager.registerCommand(mainCommand)
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }

    private fun applyConfig(plugin: KotlinPlugin) {
        val localePath = Paths.get("locale")
        plugin.saveDefaultConfig()
        plugin.saveResource(localePath.resolve("insect.yml").toString(), false)
        plugin.saveResource(localePath.resolve("message.yml").toString(), false)
        standard = plugin.config("config.yml", Standard(), Standard.serializer()).config
        insectTypeTable = plugin.config(localePath.resolve("insect.yml").toString(), InsectTypeTable(), InsectTypeTable.serializer(), alwaysRestoreDefaults = false).config
        message = plugin.config(localePath.resolve("message.yml").toString(), Message(), Message.serializer()).config
    }
}
