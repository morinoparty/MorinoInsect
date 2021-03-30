package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.configuration.Message
import com.github.morinoparty.morinoinsect.configuration.Standard

class MorinoInsect : KotlinPlugin() {
    lateinit var standard: Standard
    lateinit var insectTypeTable: InsectTypeTable
    lateinit var message: Message

    override fun onPluginEnable() {

        applyConfig(this)

        val manager = PaperCommandManager(this)
        val mainCommand = MainCommand(this)
        manager.registerCommand(mainCommand)
        manager.commandCompletions.registerAsyncCompletion("insect") { insectTypeTable.insectMap.values.map { it.name } }
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }

    private fun applyConfig(plugin: KotlinPlugin) {
        Config.load(this)
        standard = Config.standard
        insectTypeTable = Config.insectTypeTable
        message = Config.message
    }
}
